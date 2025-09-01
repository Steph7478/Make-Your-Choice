package com.make_your_choice.application.usecases.dialog.getchoicesbydialogcode;

import java.util.List;
import com.make_your_choice.domain.entities.*;;

public interface GetChoicesByDialogCodeUseCase {
    List<ChoiceEntity> execute(String code);
}
