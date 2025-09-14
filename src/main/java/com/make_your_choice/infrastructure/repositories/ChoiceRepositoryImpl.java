package com.make_your_choice.infrastructure.repositories;

import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.domain.repositories.ChoiceEntityReadRepository;
import com.make_your_choice.domain.valueobjects.Code;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// How to Search Using JPQL

@Repository
public class ChoiceRepositoryImpl extends AbstractCodeRepository<ChoiceEntity> implements ChoiceEntityReadRepository {

    @Autowired
    public ChoiceRepositoryImpl(EntityManager entityManager) {
        super(entityManager, ChoiceEntity.class, "C");
    }

    // * From all the rows in the table representing `ChoiceEntity`, select only
    // those whose `dialog` attribute is equal to the dialogue I passed as a
    // parameter (`:dialog`). */

    @Override
    public Optional<ChoiceEntity> findByDialogCode(String dialogCode) {
        Optional<Code> dialogOpt = Code.fromString(dialogCode, "D");
        if (dialogOpt.isEmpty()) {
            return Optional.empty();
        }
        DialogEntity dialog = entityManager.find(DialogEntity.class, dialogOpt.get().getId());

        List<ChoiceEntity> result = entityManager.createQuery(
                "SELECT c FROM ChoiceEntity c WHERE c.dialog = :dialog", ChoiceEntity.class)
                .setParameter("dialog", dialog)
                .setMaxResults(1)
                .getResultList();

        return result.stream().findFirst();
    }

    // * From all the rows in the table representing `ChoiceEntity`, select only
    // those whose `nextDialog` attribute is equal to the dialogue I passed as a
    // parameter (`:nextDialog`). */
    @Override
    public Optional<ChoiceEntity> findByNextDialogCode(String nextDialogCode) {
        Optional<Code> dialogOpt = Code.fromString(nextDialogCode, "D");

        if (dialogOpt.isEmpty()) {
            return Optional.empty();
        }

        DialogEntity dialog = entityManager.find(DialogEntity.class, dialogOpt.get().getId());

        List<ChoiceEntity> result = entityManager.createQuery(
                "SELECT c FROM ChoiceEntity c WHERE c.nextDialog = :nextDialog", ChoiceEntity.class)
                .setParameter("nextDialog", dialog)
                .setMaxResults(1)
                .getResultList();

        return result.stream().findFirst();
    }
}
