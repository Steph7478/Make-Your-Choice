package com.make_your_choice.presentation.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.make_your_choice.application.usecases.dialog.getalldialog.GetAllDialogsUseCaseImpl;

import com.make_your_choice.application.usecases.dialog.getchoicesbydialogcode.GetChoicesByDialogCodeUseCaseImpl;
import com.make_your_choice.application.usecases.dialog.getdialogbyid.GetDialogByIdUseCaseImpl;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;

@RestController
@RequestMapping("/dialog")
public class DialogController {
        private final GetAllDialogsUseCaseImpl getAllDialogsUseCase;
        private final GetDialogByIdUseCaseImpl getDialogByIdUseCase;
        private final GetChoicesByDialogCodeUseCaseImpl getChoicesByDialogCodeUseCase;

        public DialogController(GetDialogByIdUseCaseImpl getDialogByCodeUseCase,
                        GetChoicesByDialogCodeUseCaseImpl getChoicesByDialogCodeUseCase,
                        GetAllDialogsUseCaseImpl getAllDialogsUseCase) {
                this.getChoicesByDialogCodeUseCase = getChoicesByDialogCodeUseCase;
                this.getDialogByIdUseCase = getDialogByCodeUseCase;
                this.getAllDialogsUseCase = getAllDialogsUseCase;
        }

        @GetMapping("/")
        public List<DialogEntity> getAllDialogs() {
                return getAllDialogsUseCase.execute();
        }

        @GetMapping("/{dialogCode}")
        public DialogEntity getDialogByCodeUseCase(@PathVariable String dialogCode) {
                return getDialogByIdUseCase.execute(dialogCode)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Choice not found for dialog"));
        }

        @GetMapping("/choices")
        public List<ChoiceEntity> getChoicesByDialogCodeParam(@RequestParam String code) {
                return getChoicesByDialogCode(code);
        }

        @GetMapping("/choices/{code}")
        public List<ChoiceEntity> getChoicesByDialogCodePath(@PathVariable String code) {
                return getChoicesByDialogCode(code);
        }

        private List<ChoiceEntity> getChoicesByDialogCode(String code) {
                List<ChoiceEntity> choices = getChoicesByDialogCodeUseCase.execute(code);

                return Optional.of(choices)
                                .filter(c -> !c.isEmpty())
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "No choices found for dialog " + code));
        }

}
