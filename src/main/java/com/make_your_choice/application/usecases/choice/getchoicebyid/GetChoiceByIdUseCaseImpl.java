package com.make_your_choice.application.usecases.choice.getchoicebyid;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.infrastructure.repositories.ChoiceRepositoryImpl;

@Component
public class GetChoiceByIdUseCaseImpl implements GetChoiceByIdUseCase {
    private final ChoiceRepositoryImpl choiceRepository;

    public GetChoiceByIdUseCaseImpl(ChoiceRepositoryImpl choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    @Override
    public Optional<ChoiceEntity> execute(String code) {
        return choiceRepository.findByCode(code);
    }
}
