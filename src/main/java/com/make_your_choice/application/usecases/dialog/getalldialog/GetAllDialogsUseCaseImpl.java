package com.make_your_choice.application.usecases.dialog.getalldialog;

import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.infrastructure.repositories.DialogRepositoryImpl;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllDialogsUseCaseImpl implements GetAllDialogsUseCase {

    private final DialogRepositoryImpl dialogRepository;

    public GetAllDialogsUseCaseImpl(DialogRepositoryImpl dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @Override
    public List<DialogEntity> execute() {
        return dialogRepository.findAll();
    }
}
