package com.make_your_choice.infrastructure.repositories;

import com.make_your_choice.domain.models.DialogEntity;
import com.make_your_choice.domain.repositories.DialogEntityReadRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class DialogRepositoryImpl implements DialogEntityReadRepository {

    private final EntityManager entityManager;

    @Autowired
    public DialogRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<DialogEntity> findByCode(String code) {
        if (code == null || !code.startsWith("D")) {
            return Optional.empty();
        }
        try {
            Long id = Long.parseLong(code.substring(1));
            DialogEntity entity = entityManager.find(DialogEntity.class, id);
            return Optional.ofNullable(entity);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<DialogEntity> findAllByCodes(List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return List.of();
        }

        List<Long> ids = codes.stream()
                .filter(c -> c != null && c.startsWith("D"))
                .map(c -> {
                    try {
                        return Long.parseLong(c.substring(1));
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(id -> id != null)
                .toList();

        if (ids.isEmpty()) {
            return List.of();
        }

        return entityManager.createQuery("SELECT d FROM DialogEntity d WHERE d.id IN :ids", DialogEntity.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
