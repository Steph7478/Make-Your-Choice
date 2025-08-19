package com.make_your_choice.application.usecases.choice.getallchoice;

import com.make_your_choice.domain.entities.ChoiceEntity;
import java.util.List;

public interface GetAllChoiceUseCase {
    List<ChoiceEntity> execute();
}
