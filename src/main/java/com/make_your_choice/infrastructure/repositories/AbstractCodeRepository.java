package com.make_your_choice.infrastructure.repositories;

import com.make_your_choice.domain.valueobjects.Code;
import com.make_your_choice.infrastructure.cache.Cache;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractCodeRepository<T> {

    protected final EntityManager entityManager;
    private final Class<T> entityClass;
    private final String codePrefix;

    private final Cache<String, T> cache = new Cache<>(100, 5000, 16);

    protected AbstractCodeRepository(EntityManager entityManager, Class<T> entityClass, String codePrefix) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.codePrefix = codePrefix;
    }

    public Optional<T> findByCode(String codeStr) {
        Optional<Code> codeOpt = Code.fromString(codeStr, codePrefix);
        if (codeOpt.isEmpty())
            return Optional.empty();

        T cached = cache.get(codeStr);
        if (cached != null)
            return Optional.of(cached);

        T entity = entityManager.find(entityClass, codeOpt.get().getId());
        if (entity != null)
            cache.put(codeStr, entity);

        return Optional.ofNullable(entity);
    }

    public List<T> findAll() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return entityManager.createQuery(jpql, entityClass).getResultList();
    }

    public List<T> findAllByCodes(List<String> codesStr) {
        if (codesStr == null || codesStr.isEmpty())
            return List.of();

        List<Long> ids = codesStr.stream()
                .map(code -> Code.fromString(code, codePrefix))
                .filter(Optional::isPresent)
                .map(opt -> opt.get().getId())
                .collect(Collectors.toList());

        if (ids.isEmpty())
            return List.of();

        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id IN :ids";
        return entityManager.createQuery(jpql, entityClass)
                .setParameter("ids", ids)
                .getResultList();
    }
}
