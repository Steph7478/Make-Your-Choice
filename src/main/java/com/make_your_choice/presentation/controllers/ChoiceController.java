package com.make_your_choice.presentation.controllers;

import com.make_your_choice.application.usecases.choice.getallchoice.GetAllChoiceUseCaseImpl;

import com.make_your_choice.application.usecases.choice.getchoicebyid.GetChoiceByIdUseCaseImpl;

import com.make_your_choice.application.usecases.choice.getnextdialogbycode.GetNextDialogByCodeUseCaseImpl;
import com.make_your_choice.domain.entities.ChoiceEntity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/choices")
public class ChoiceController {

    private final GetAllChoiceUseCaseImpl getAllChoiceUseCase;
    private final GetChoiceByIdUseCaseImpl getChoiceByIdUseCase;
    private final GetNextDialogByCodeUseCaseImpl getNextDialogByCodeUseCase;

    public ChoiceController(
            GetAllChoiceUseCaseImpl getAllChoiceUseCase,
            GetChoiceByIdUseCaseImpl getChoiceByIdUseCase,
            GetNextDialogByCodeUseCaseImpl getNextDialogByCodeUseCase) {
        this.getAllChoiceUseCase = getAllChoiceUseCase;
        this.getChoiceByIdUseCase = getChoiceByIdUseCase;
        this.getNextDialogByCodeUseCase = getNextDialogByCodeUseCase;
    }

    @GetMapping("/")
    public List<ChoiceEntity> getAllChoices() {
        return getAllChoiceUseCase.execute();
    }

    @GetMapping("/{code}")
    public ChoiceEntity getChoiceById(@PathVariable String code) {
        return getChoiceByIdUseCase.execute(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Choice not found"));
    }

    @GetMapping("/next/{nextDialogCode}")
    public ChoiceEntity getNextChoiceByDialogCode(@PathVariable String nextDialogCode) {
        return getNextDialogByCodeUseCase.execute(nextDialogCode)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Next choice not found for dialog"));
    }

}
