package com.make_your_choice.application.usecases.choice.getdialogbycode;

import java.util.Optional;

import com.make_your_choice.domain.entities.ChoiceEntity;

public interface GetDialogByCodeUseCase {
    Optional<ChoiceEntity> execute(String code);
}
