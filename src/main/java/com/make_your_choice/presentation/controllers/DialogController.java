package com.make_your_choice.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.make_your_choice.application.usecases.choice.getdialogbycode.GetDialogByCodeUseCaseImpl;
import com.make_your_choice.application.usecases.dialog.getalldialog.GetAllDialogsUseCaseImpl;

import com.make_your_choice.application.usecases.dialog.getchoicesbydialogcode.GetChoicesByDialogCodeUseCaseImpl;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;

@RestController
@RequestMapping("/dialog")
public class DialogController {
        private final GetAllDialogsUseCaseImpl getAllDialogsUseCase;
        private final GetDialogByCodeUseCaseImpl getDialogByCodeUseCase;
        private final GetChoicesByDialogCodeUseCaseImpl getChoicesByDialogCodeUseCase;

        public DialogController(GetDialogByCodeUseCaseImpl getDialogByCodeUseCase,
                        GetChoicesByDialogCodeUseCaseImpl getChoicesByDialogCodeUseCase,
                        GetAllDialogsUseCaseImpl getAllDialogsUseCase) {
                this.getChoicesByDialogCodeUseCase = getChoicesByDialogCodeUseCase;
                this.getDialogByCodeUseCase = getDialogByCodeUseCase;
                this.getAllDialogsUseCase = getAllDialogsUseCase;
        }

        @GetMapping("/")
        public List<DialogEntity> getAllDialogs() {
                return getAllDialogsUseCase.execute();
        }

        @GetMapping("/{dialogCode}")
        public ChoiceEntity getChoiceByDialogCode(@PathVariable String dialogCode) {
                return getDialogByCodeUseCase.execute(dialogCode)
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
                if (code == null || code.isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code is required");
                }

                List<ChoiceEntity> choices = getChoicesByDialogCodeUseCase.execute(code);

                if (choices.isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "No choices found for dialog code: " + code);
                }

                return choices;
        }

}
