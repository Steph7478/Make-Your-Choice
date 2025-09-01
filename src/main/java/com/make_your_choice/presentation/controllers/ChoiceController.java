package com.make_your_choice.presentation.controllers;

import com.make_your_choice.application.usecases.choice.getallchoice.GetAllChoiceUseCase;
import com.make_your_choice.application.usecases.choice.getchoicebyid.GetChoiceByIdUseCase;
import com.make_your_choice.application.usecases.choice.getdialogbycode.GetDialogByCodeUseCase;
import com.make_your_choice.application.usecases.choice.getnextdialogbycode.GetNextDialogByCodeUseCase;
import com.make_your_choice.application.usecases.dialog.getchoicesbydialogcode.GetChoicesByDialogCodeUseCase;
import com.make_your_choice.domain.entities.ChoiceEntity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/choices")
public class ChoiceController {

    private final GetAllChoiceUseCase getAllChoiceUseCase;
    private final GetChoiceByIdUseCase getChoiceByIdUseCase;
    private final GetNextDialogByCodeUseCase getNextDialogByCodeUseCase;

    public ChoiceController(
            GetAllChoiceUseCase getAllChoiceUseCase,
            GetChoiceByIdUseCase getChoiceByIdUseCase,
            GetDialogByCodeUseCase getDialogByCodeUseCase,
            GetNextDialogByCodeUseCase getNextDialogByCodeUseCase,
            GetChoicesByDialogCodeUseCase getChoicesByDialogCodeUseCase) {
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
