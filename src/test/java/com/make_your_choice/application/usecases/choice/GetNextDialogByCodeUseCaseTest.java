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

import com.make_your_choice.infrastructure.repositories.ChoiceRepositoryImpl;
import com.make_your_choice.application.usecases.choice.getnextdialogbycode.GetNextDialogByCodeUseCaseImpl;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;

public class GetNextDialogByCodeUseCaseTest {
    private ChoiceRepositoryImpl choiceRepository;
    private GetNextDialogByCodeUseCaseImpl getNextDialogByCodeUseCase;

    @BeforeEach
    void setup() {
        choiceRepository = Mockito.mock(ChoiceRepositoryImpl.class);
        getNextDialogByCodeUseCase = new GetNextDialogByCodeUseCaseImpl(choiceRepository);
    }

    @Test
    void testGetNextDialogByCodeUseCase() {
        String code = "D12";
        ChoiceEntity choice = new ChoiceEntity();
        DialogEntity dialog = new DialogEntity();

        setField(choice, "nextDialog", dialog);
        setField(dialog, "id", 12L);

        when(choiceRepository.findByNextDialogCode(code)).thenReturn(Optional.of(choice));

        Optional<ChoiceEntity> result = getNextDialogByCodeUseCase.execute(code);

        assertTrue(result.isPresent());
        assertEquals(choice, result.get());

        verify(choiceRepository, times(1)).findByNextDialogCode(code);

    }

}
