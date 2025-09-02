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

import com.make_your_choice.application.usecases.choice.getdialogbycode.GetDialogByCodeUseCase;
import com.make_your_choice.application.usecases.dialog.getchoicesbydialogcode.GetChoicesByDialogCodeUseCase;
import com.make_your_choice.domain.entities.ChoiceEntity;

@RestController
@RequestMapping("/dialog")
public class DialogController {
        private final GetDialogByCodeUseCase getDialogByCodeUseCase;
        private final GetChoicesByDialogCodeUseCase getChoicesByDialogCodeUseCase;

        public DialogController(GetDialogByCodeUseCase getDialogByCodeUseCase,
                        GetChoicesByDialogCodeUseCase getChoicesByDialogCodeUseCase) {
                this.getChoicesByDialogCodeUseCase = getChoicesByDialogCodeUseCase;
                this.getDialogByCodeUseCase = getDialogByCodeUseCase;
        }

        @GetMapping("/{dialogCode}")
        public ChoiceEntity getChoiceByDialogCode(@PathVariable String dialogCode) {
                return getDialogByCodeUseCase.execute(dialogCode)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Choice not found for dialog"));
        }

        @GetMapping({ "/choices", "/choices/{code}" })
        public List<ChoiceEntity> getChoicesByDialogCode(
                        @RequestParam(required = false) String codeParam, @PathVariable(required = false) String code) {

                String finalCode = Optional.ofNullable(code)
                                .orElse(codeParam);

                return Optional.ofNullable(finalCode)
                                .filter(c -> !c.isEmpty())
                                .map(getChoicesByDialogCodeUseCase::execute)
                                .filter(list -> !list.isEmpty())
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "No choices found for this dialog code"));
        }

}
