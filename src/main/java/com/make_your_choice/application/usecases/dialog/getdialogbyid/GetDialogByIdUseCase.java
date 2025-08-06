package com.make_your_choice.application.usecases.dialog.getdialogbyid;

import com.make_your_choice.domain.entities.DialogEntity;
import java.util.Optional;

public interface GetDialogByIdUseCase {
    Optional<DialogEntity> execute(String code);
}
