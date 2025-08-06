package com.make_your_choice.application.usecases.dialog.getalldialog;

import com.make_your_choice.domain.entities.DialogEntity;
import java.util.List;

public interface GetAllDialogsUseCase {
    List<DialogEntity> execute();
}
