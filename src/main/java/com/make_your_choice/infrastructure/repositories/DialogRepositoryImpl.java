package com.make_your_choice.infrastructure.repositories;

import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.domain.repositories.DialogEntityReadRepository;
import com.make_your_choice.domain.valueobjects.Code;
import com.make_your_choice.infrastructure.cache.Cache;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// How to Use Entity Methods

@Repository
public class DialogRepositoryImpl extends AbstractCodeRepository<DialogEntity> implements DialogEntityReadRepository {

    private final Cache<String, List<ChoiceEntity>> listCache = new Cache<>(100, 5000, 16);

    @Autowired
    public DialogRepositoryImpl(EntityManager entityManager) {
        super(entityManager, DialogEntity.class, "D");
    }

    @Override
    public List<ChoiceEntity> findChoicesByDialogCode(String dialogCode) {

        Optional<Code> codeOpt = Code.fromString(dialogCode, "D");
        if (codeOpt.isEmpty())
            return List.of();

        List<ChoiceEntity> cached = listCache.get(dialogCode);
        if (cached != null)
            return cached;

        DialogEntity dialog = entityManager.find(DialogEntity.class, codeOpt.get().getId());
        if (dialog == null)
            return List.of();

        List<ChoiceEntity> choices = dialog.getChoices();

        listCache.put(dialogCode, choices);

        return choices;
    }

}
