package com.make_your_choice.application.usecases.getdialog;

import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.domain.repositories.DialogEntityReadRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetDialogUseCaseImpl implements GetDialogUseCase {

    private final DialogEntityReadRepository dialogRepository;

    public GetDialogUseCaseImpl(DialogEntityReadRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @Override
    public Optional<DialogEntity> execute(String code) {
        return dialogRepository.findByCode(code);
    }
}
