package com.make_your_choice.application.usecases.getdialog;

import com.make_your_choice.domain.entities.DialogEntity;
import java.util.Optional;

public interface GetDialogUseCase {
    Optional<DialogEntity> execute(String code);
}
