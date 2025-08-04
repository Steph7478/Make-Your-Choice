package com.make_your_choice.domain.repositories;

import com.make_your_choice.domain.models.ChoiceEntity;

import java.util.List;
import java.util.Optional;

public interface ChoiceEntityReadRepository {
    List<ChoiceEntity> findAll();

    Optional<ChoiceEntity> findByDialogId(Long dialogId);

    Optional<ChoiceEntity> findByNextDialogId(Long dialogId);

    Optional<ChoiceEntity> findById(Long id);

}
