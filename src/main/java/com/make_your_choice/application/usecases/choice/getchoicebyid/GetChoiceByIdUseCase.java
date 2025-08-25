package com.make_your_choice.application.usecases.choice.getchoicebyid;

import java.util.Optional;

import com.make_your_choice.domain.entities.ChoiceEntity;

public interface GetChoiceByIdUseCase {
    Optional<ChoiceEntity> execute(String code);

}
