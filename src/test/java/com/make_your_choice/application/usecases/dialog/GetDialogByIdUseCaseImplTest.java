package com.make_your_choice.application.usecases.dialog;

import com.make_your_choice.application.usecases.dialog.getdialogbyid.GetDialogByIdUseCaseImpl;
import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.infrastructure.repositories.DialogRepositoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.make_your_choice.support.TestReflectionUtils.setField;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetDialogByIdUseCaseImplTest {

    private DialogRepositoryImpl dialogRepository;
    private GetDialogByIdUseCaseImpl getDialogUseCase;

    @BeforeEach
    void setup() {
        // mocket repository
        dialogRepository = Mockito.mock(DialogRepositoryImpl.class);

        // usecase to test
        getDialogUseCase = new GetDialogByIdUseCaseImpl(dialogRepository);
    }

    @Test
    void testGetDialogById_Found() {
        // mocket data
        String code = "D42";
        DialogEntity dialog = new DialogEntity();
        setField(dialog, "id", 42L);

        // what must return
        when(dialogRepository.findByCode(code)).thenReturn(Optional.of(dialog));

        // execute test
        Optional<DialogEntity> result = getDialogUseCase.execute(code);

        assertTrue(result.isPresent());
        assertEquals(dialog, result.get());

        verify(dialogRepository, times(1)).findByCode(code);
    }

    @Test
    void testGetDialogById_NotFound() {
        // mocket data
        String code = "D999";

        // what must return
        when(dialogRepository.findByCode(code)).thenReturn(Optional.empty());

        // execute test
        Optional<DialogEntity> result = getDialogUseCase.execute(code);

        assertFalse(result.isPresent());

        verify(dialogRepository, times(1)).findByCode(code);
    }
}
