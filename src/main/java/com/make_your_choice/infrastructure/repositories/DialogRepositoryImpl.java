package com.make_your_choice.infrastructure.repositories;

import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.domain.repositories.DialogEntityReadRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// How to Use Entity Methods

@Repository
public class DialogRepositoryImpl extends AbstractCodeRepository<DialogEntity> implements DialogEntityReadRepository {

    @Autowired
    public DialogRepositoryImpl(EntityManager entityManager) {
        super(entityManager, DialogEntity.class, "D");
    }

    @Override
    public List<ChoiceEntity> findChoicesByDialogCode(String dialogCode) {

        if (dialogCode == null || !dialogCode.startsWith("D")) {
            return List.of();
        }

        try {
            // here im removing the prefix "D"
            Long id = Long.parseLong(dialogCode.substring(1));
            DialogEntity dialog = entityManager.find(DialogEntity.class, id);
            if (dialog == null) {
                return List.of();
            }

            // here i use the Entity Method
            return dialog.getChoices();
        } catch (NumberFormatException e) {
            return List.of();
        }
    }

}
