package com.make_your_choice.application.usecases.choice.getallchoice;

import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.infrastructure.repositories.ChoiceRepositoryImpl;

import java.util.List;

public class GetAllChoiceUseCaseImpl implements GetAllChoiceUseCase {
    private final ChoiceRepositoryImpl choiceRepository;

    public GetAllChoiceUseCaseImpl(ChoiceRepositoryImpl choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    @Override
    public List<ChoiceEntity> execute() {
        return choiceRepository.findAll();
    }
}
