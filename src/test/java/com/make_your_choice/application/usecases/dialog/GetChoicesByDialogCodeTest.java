package com.make_your_choice.application.usecases.dialog;

import static com.make_your_choice.support.TestReflectionUtils.setField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.make_your_choice.application.usecases.dialog.getchoicesbydialogcode.GetChoicesByDialogCodeUseCaseImpl;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.infrastructure.repositories.DialogRepositoryImpl;

public class GetChoicesByDialogCodeTest {
    private DialogRepositoryImpl dialogRepository;
    private GetChoicesByDialogCodeUseCaseImpl getChoicesByDialogCode;

    @BeforeEach
    void setup() {
        dialogRepository = Mockito.mock(DialogRepositoryImpl.class);
        getChoicesByDialogCode = new GetChoicesByDialogCodeUseCaseImpl(dialogRepository);
    }

    @Test
    void getChoicesByDialogCodeTest() {
        DialogEntity dialog = new DialogEntity();
        ChoiceEntity choice1 = new ChoiceEntity();
        ChoiceEntity choice2 = new ChoiceEntity();

        setField(choice1, "dialog", dialog);
        setField(choice2, "dialog", dialog);

        setField(dialog, "choices", List.of(choice1, choice2));
        setField(dialog, "id", 42L);

        List<ChoiceEntity> choices = List.of(choice1, choice2);

        when(dialogRepository.findChoicesByDialogCode("D42")).thenReturn(choices);

        List<ChoiceEntity> result = getChoicesByDialogCode.execute("D42");

        assertEquals(2, result.size());
        assertEquals(choices, result);
    }

}
