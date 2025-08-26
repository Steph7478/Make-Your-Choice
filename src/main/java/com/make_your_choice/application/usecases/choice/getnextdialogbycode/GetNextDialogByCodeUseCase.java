package com.make_your_choice.application.usecases.choice.getnextdialogbycode;

import java.util.Optional;

import com.make_your_choice.domain.entities.ChoiceEntity;

public interface GetNextDialogByCodeUseCase {
    Optional<ChoiceEntity> execute(String code);
}
