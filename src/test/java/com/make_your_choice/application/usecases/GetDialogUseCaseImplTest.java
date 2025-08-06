package com.make_your_choice.application.usecases;

import com.make_your_choice.application.usecases.getdialogbyid.GetDialogByIdUseCaseImpl;
import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.domain.repositories.DialogEntityReadRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetDialogUseCaseImplTest {

    private DialogEntityReadRepository dialogRepository;
    private GetDialogByIdUseCaseImpl getDialogUseCase;

    @BeforeEach
    void setup() {
        dialogRepository = Mockito.mock(DialogEntityReadRepository.class);
        getDialogUseCase = new GetDialogByIdUseCaseImpl(dialogRepository);
    }

    @Test
    void testGetDialogById_Found() {
        String code = "D42";
        DialogEntity dialog = new DialogEntity();
        com.make_your_choice.support.TestReflectionUtils.setField(dialog, "id", 42L);

        when(dialogRepository.findByCode(code)).thenReturn(Optional.of(dialog));

        Optional<DialogEntity> result = getDialogUseCase.execute(code);

        assertTrue(result.isPresent());
        assertEquals(dialog, result.get());

        verify(dialogRepository, times(1)).findByCode(code);
    }

    @Test
    void testGetDialogById_NotFound() {
        String code = "D999";
        when(dialogRepository.findByCode(code)).thenReturn(Optional.empty());

        Optional<DialogEntity> result = getDialogUseCase.execute(code);

        assertFalse(result.isPresent());

        verify(dialogRepository, times(1)).findByCode(code);
    }
}
