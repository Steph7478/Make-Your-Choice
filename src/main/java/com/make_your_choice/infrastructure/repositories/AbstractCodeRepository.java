package com.make_your_choice.infrastructure.repositories;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractCodeRepository<T> {

    protected final EntityManager entityManager;
    private final Class<T> entityClass;
    private final String codePrefix;

    protected AbstractCodeRepository(EntityManager entityManager, Class<T> entityClass, String codePrefix) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.codePrefix = codePrefix;
    }

    public List<T> findAll() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return entityManager.createQuery(jpql, entityClass)
                .getResultList();
    }

    public Optional<T> findByCode(String code) {
        if (code == null || !code.startsWith(codePrefix)) {
            return Optional.empty();
        }
        try {
            Long id = Long.parseLong(code.substring(codePrefix.length()));
            T entity = entityManager.find(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public List<T> findAllByCodes(List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return List.of();
        }

        List<Long> ids = codes.stream()
                .filter(c -> c != null && c.startsWith(codePrefix))
                .map(c -> {
                    try {
                        return Long.parseLong(c.substring(codePrefix.length()));
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(id -> id != null)
                .collect(Collectors.toList());

        if (ids.isEmpty()) {
            return List.of();
        }

        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id IN :ids";
        return entityManager.createQuery(jpql, entityClass)
                .setParameter("ids", ids)
                .getResultList();
    }
}
