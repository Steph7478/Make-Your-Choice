package com.make_your_choice.infrastructure.repositories;

import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.domain.repositories.ChoiceEntityReadRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChoiceRepositoryImpl extends AbstractCodeRepository<ChoiceEntity> implements ChoiceEntityReadRepository {

    @Autowired
    public ChoiceRepositoryImpl(EntityManager entityManager) {
        super(entityManager, ChoiceEntity.class, "C");
    }

    private Optional<DialogEntity> findDialogByCode(String dialogCode) {
        if (dialogCode == null || !dialogCode.startsWith("D")) {
            return Optional.empty();
        }
        try {
            Long id = Long.parseLong(dialogCode.substring(1));
            DialogEntity dialog = entityManager.find(DialogEntity.class, id);
            return Optional.ofNullable(dialog);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChoiceEntity> findByDialogCode(String dialogCode) {
        Optional<DialogEntity> dialogOpt = findDialogByCode(dialogCode);
        if (dialogOpt.isEmpty()) {
            return Optional.empty();
        }
        DialogEntity dialog = dialogOpt.get();

        List<ChoiceEntity> result = entityManager.createQuery(
                "SELECT c FROM ChoiceEntity c WHERE c.dialog = :dialog", ChoiceEntity.class)
                .setParameter("dialog", dialog)
                .setMaxResults(1)
                .getResultList();

        return result.stream().findFirst();
    }

    @Override
    public Optional<ChoiceEntity> findByNextDialogCode(String nextDialogCode) {
        Optional<DialogEntity> dialogOpt = findDialogByCode(nextDialogCode);
        if (dialogOpt.isEmpty()) {
            return Optional.empty();
        }
        DialogEntity dialog = dialogOpt.get();

        List<ChoiceEntity> result = entityManager.createQuery(
                "SELECT c FROM ChoiceEntity c WHERE c.nextDialog = :nextDialog", ChoiceEntity.class)
                .setParameter("nextDialog", dialog)
                .setMaxResults(1)
                .getResultList();

        return result.stream().findFirst();
    }
}
