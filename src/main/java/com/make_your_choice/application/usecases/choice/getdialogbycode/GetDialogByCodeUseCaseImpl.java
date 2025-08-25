package com.make_your_choice.application.usecases.choice.getdialogbycode;

import org.springframework.stereotype.Component;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.infrastructure.repositories.ChoiceRepositoryImpl;
import java.util.Optional;

@Component
public class GetDialogByCodeUseCaseImpl implements GetDialogByCodeUseCase {
    private ChoiceRepositoryImpl choiceRepository;

    public GetDialogByCodeUseCaseImpl(ChoiceRepositoryImpl choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    @Override
    public Optional<ChoiceEntity> execute(String code) {
        return choiceRepository.findByDialogCode(code);
    }
}
