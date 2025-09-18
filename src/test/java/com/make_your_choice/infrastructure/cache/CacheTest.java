package com.make_your_choice.infrastructure.cache;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CacheTest {

    @Test
    void testHitsAndMisses() {
        Cache<String, String> cache = new Cache<>(2, 1000, 1);

        cache.put("a", "alpha");

        System.out.println(">>> GET a = " + cache.get("a"));
        System.out.println(">>> GET b = " + cache.get("b"));
        System.out.println(">>> Hits   = " + cache.getHits());
        System.out.println(">>> Misses = " + cache.getMisses());

        assertEquals(1, cache.getHits());
        assertEquals(1, cache.getMisses());
        assertEquals("alpha", cache.get("a"));
        assertNull(cache.get("b"));
    }

    @Test
    void testExpiration() throws InterruptedException {
        Cache<String, String> cache = new Cache<>(2, 100, 1);
        cache.put("x", "xxx");

        Thread.sleep(200);

        System.out.println(">>> After TTL, GET x = " + cache.get("x"));
        System.out.println(">>> Expired count = " + cache.getExpiredCount());

        assertNull(cache.get("x"));
        assertEquals(1, cache.getExpiredCount());
    }

    @Test
    void testEviction() {
        Cache<String, String> cache = new Cache<>(2, 1000, 1);

        cache.put("a", "alpha");
        cache.put("b", "bravo");
        cache.put("c", "charlie");

        System.out.println(">>> After eviction, GET a = " + cache.get("a"));
        System.out.println(">>> GET b = " + cache.get("b"));
        System.out.println(">>> GET c = " + cache.get("c"));
        System.out.println(">>> Evicted count = " + cache.getEvictedCount());

        assertNull(cache.get("a"));
        assertNotNull(cache.get("b"));
        assertNotNull(cache.get("c"));
        assertEquals(1, cache.getEvictedCount());
    }
}
