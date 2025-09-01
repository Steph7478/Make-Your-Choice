package com.make_your_choice.application.usecases.dialog.getchoicesbydialogcode;

import java.util.List;

import org.springframework.stereotype.Component;

import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.infrastructure.repositories.DialogRepositoryImpl;

@Component
public class GetChoicesByDialogCodeUseCaseImpl implements GetChoicesByDialogCodeUseCase {

    private final DialogRepositoryImpl repository;

    public GetChoicesByDialogCodeUseCaseImpl(DialogRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public List<ChoiceEntity> execute(String code) {
        return repository.findChoicesByDialogCode(code);
    }
}
