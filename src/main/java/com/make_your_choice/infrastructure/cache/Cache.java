package com.make_your_choice.infrastructure.cache;

import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

public class Cache<K, V> {

    private final Shard<K, V>[] shards;
    private final int shardCount;

    // needed cuz generic arrays cannot be directly created in Java
    @SuppressWarnings("unchecked")

    // cache setup
    public Cache(int maxSize, long ttlMillis, int shardCount) {
        // setting atributes
        this.shardCount = shardCount;
        this.shards = new Shard[shardCount];

        // this is the formula to calculate the size of each shard, that means max of
        // keys / how many items can contain inside of 1 shard
        int shardSize = (maxSize + shardCount - 1) / shardCount;

        for (int i = 0; i < shardCount; i++) {
            shards[i] = new Shard<>(shardSize, ttlMillis);
        }
    }

    // what i want to save in cache
    private static class CacheItem<V> {
        final V value;
        volatile long expireAt;

        CacheItem(V value, long expireAt) {
            this.value = value;
            this.expireAt = expireAt;
        }
    }

    // detect which item must be removed of the cache
    private static class DelayedCacheItem<K> implements Delayed {
        final K key;
        final long expireAt;

        DelayedCacheItem(K key, long expireAt) {
            this.key = key;
            this.expireAt = expireAt;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expireAt - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.expireAt, ((DelayedCacheItem<?>) o).expireAt);
        }
    }

    private Shard<K, V> shard(K key) {
        // here im setting the index of the shard in base of hashcode -> making it
        // positive -> getting index by %
        int idx = (key.hashCode() & 0x7fffffff) % shardCount;
        return shards[idx];
    }

    // shard setup
    // here comes the logic of each shard
    private static class Shard<K, V> {

        // finals for queues

        // save the keys and values with the expiration information
        final ConcurrentHashMap<K, CacheItem<V>> map;

        // keep the order of insert, used to remove items after passing the size limit
        final ConcurrentLinkedQueue<K> fifoQueue = new ConcurrentLinkedQueue<>();

        // special queue that keep the items organized by the expiration time, and
        // allows to remove automatically items when expired
        final DelayQueue<DelayedCacheItem<K>> delayQueue = new DelayQueue<>();

        // valors needed for shard
        final int maxSize;
        final long ttlMillis;

        // metrics
        final LongAdder hits = new LongAdder();
        final LongAdder misses = new LongAdder();
        final LongAdder expired = new LongAdder();
        final LongAdder evicted = new LongAdder();

        // here im configurating the thread to run the cleanUp(), thread is like a mini
        // process inside of the program, they are subtasks inside of a process
        private static final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor(r -> {

            // thread setup
            Thread t = new Thread(r);

            // I set the thread as a daemon so it doesn’t block the application from
            // shutting down
            t.setDaemon(true);
            // just a name for better debug
            t.setName("UltraCache-Cleaner");
            return t;
        });

        // setting atributes
        Shard(int maxSize, long ttlMillis) {
            this.maxSize = maxSize;
            this.ttlMillis = ttlMillis;
            this.map = new ConcurrentHashMap<>(maxSize);

            cleaner.scheduleWithFixedDelay(this::cleanUp, ttlMillis, ttlMillis, TimeUnit.MILLISECONDS);
        }

        void put(K key, V value) {

            // add the shard to the cache
            long expireAt = System.currentTimeMillis() + ttlMillis;
            map.put(key, new CacheItem<>(value, expireAt));
            fifoQueue.add(key);
            delayQueue.offer(new DelayedCacheItem<>(key, expireAt));

            // enable fifo
            while (map.size() > maxSize) {
                K oldest = fifoQueue.poll();
                if (oldest != null && map.remove(oldest) != null) {
                    evicted.increment();
                }
            }
        }

        // get the item in the cache and refresh TTL
        V get(K key, boolean refreshTTL) {
            CacheItem<V> item = map.get(key);

            // metrics
            if (item == null) {
                misses.increment();
                return null;
            }

            long now = System.currentTimeMillis();

            // if expired, then remove
            if (item.expireAt <= now) {
                map.remove(key);
                expired.increment();
                misses.increment();
                return null;
            }

            // refresh if not expired
            if (refreshTTL) {
                long newExpire = now + ttlMillis;
                item.expireAt = newExpire;
                delayQueue.offer(new DelayedCacheItem<>(key, newExpire));
            }

            hits.increment();
            return item.value;
        }

        void remove(K key) {
            map.remove(key);
        }

        void clear() {
            map.clear();
            fifoQueue.clear();
            delayQueue.clear();
        }

        // cleanup of cache with thread
        private void cleanUp() {
            DelayedCacheItem<K> delayedItem;
            while ((delayedItem = delayQueue.poll()) != null) {
                K key = delayedItem.key;
                CacheItem<V> item = map.get(key);
                if (item != null && item.expireAt <= System.currentTimeMillis()) {
                    map.remove(key);
                    expired.increment();
                }
            }
        }
    }

    // public functions to be used

    // size
    public int size() {
        int sum = 0;
        for (Shard<K, V> s : shards)
            sum += s.map.size();
        return sum;
    }

    // operations of shard
    public void put(K key, V value) {
        shard(key).put(key, value);
    }

    public V get(K key, boolean refreshTTL) {
        return shard(key).get(key, refreshTTL);
    }

    public V get(K key) {
        return get(key, false);
    }

    public void remove(K key) {
        shard(key).remove(key);
    }

    public void clear() {
        for (Shard<K, V> shard : shards)
            shard.clear();
    }

    // metrics
    public long getHits() {
        long sum = 0;
        for (Shard<K, V> s : shards)
            sum += s.hits.sum();
        return sum;
    }

    public long getMisses() {
        long sum = 0;
        for (Shard<K, V> s : shards)
            sum += s.misses.sum();
        return sum;
    }

    public long getExpiredCount() {
        long sum = 0;
        for (Shard<K, V> s : shards)
            sum += s.expired.sum();
        return sum;
    }

    public long getEvictedCount() {
        long sum = 0;
        for (Shard<K, V> s : shards)
            sum += s.evicted.sum();
        return sum;
    }

}
