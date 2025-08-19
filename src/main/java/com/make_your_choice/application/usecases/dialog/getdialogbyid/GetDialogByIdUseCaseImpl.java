package com.make_your_choice.application.usecases.dialog.getdialogbyid;

import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.infrastructure.repositories.DialogRepositoryImpl;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetDialogByIdUseCaseImpl implements GetDialogByIdUseCase {

    private final DialogRepositoryImpl dialogRepository;

    public GetDialogByIdUseCaseImpl(DialogRepositoryImpl dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @Override
    public Optional<DialogEntity> execute(String code) {
        return dialogRepository.findByCode(code);
    }
}
