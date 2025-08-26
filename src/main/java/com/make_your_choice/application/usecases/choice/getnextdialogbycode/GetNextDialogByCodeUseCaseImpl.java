package com.make_your_choice.application.usecases.choice.getnextdialogbycode;

import java.util.Optional;

import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.infrastructure.repositories.ChoiceRepositoryImpl;

public class GetNextDialogByCodeUseCaseImpl implements GetNextDialogByCodeUseCase {
    private ChoiceRepositoryImpl choiceRepository;

    public GetNextDialogByCodeUseCaseImpl(ChoiceRepositoryImpl choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    @Override
    public Optional<ChoiceEntity> execute(String code) {
        return choiceRepository.findByNextDialogCode(code);
    }
}
