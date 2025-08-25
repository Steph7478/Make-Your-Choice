package com.make_your_choice.application.usecases.choice;

import static com.make_your_choice.support.TestReflectionUtils.setField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.make_your_choice.application.usecases.choice.getdialogbycode.GetDialogByCodeUseCaseImpl;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.infrastructure.repositories.ChoiceRepositoryImpl;

public class GetDialogByCodeUseCaseImplTest {
    private ChoiceRepositoryImpl choiceRepository;
    private GetDialogByCodeUseCaseImpl getDialogByCodeUseCase;

    @BeforeEach
    void setup() {
        choiceRepository = Mockito.mock(ChoiceRepositoryImpl.class);
        getDialogByCodeUseCase = new GetDialogByCodeUseCaseImpl(choiceRepository);
    }

    @Test
    void testGetDialogByCodeUseCase() {
        String code = "D12";

        ChoiceEntity choice = new ChoiceEntity();
        DialogEntity dialog = new DialogEntity();

        setField(dialog, "id", 12L);
        setField(choice, "dialog", dialog);

        when(choiceRepository.findByDialogCode(code)).thenReturn(Optional.of(choice));

        Optional<ChoiceEntity> result = getDialogByCodeUseCase.execute(code);

        assertTrue(result.isPresent());
        assertEquals(choice, result.get());

        verify(choiceRepository, times(1)).findByDialogCode(code);
    }

}
