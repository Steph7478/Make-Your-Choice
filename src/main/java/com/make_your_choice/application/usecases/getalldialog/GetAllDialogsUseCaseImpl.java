package com.make_your_choice.application.usecases.getalldialog;

import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.domain.repositories.DialogEntityReadRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllDialogsUseCaseImpl implements GetAllDialogsUseCase {

    private final DialogEntityReadRepository dialogRepository;

    public GetAllDialogsUseCaseImpl(DialogEntityReadRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @Override
    public List<DialogEntity> execute() {
        return dialogRepository.findAll();
    }
}
