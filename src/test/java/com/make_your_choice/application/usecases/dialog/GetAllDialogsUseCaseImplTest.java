package com.make_your_choice.application.usecases.dialog;

import com.make_your_choice.application.usecases.dialog.getalldialog.GetAllDialogsUseCaseImpl;
import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.infrastructure.repositories.DialogRepositoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllDialogsUseCaseImplTest {

    private DialogRepositoryImpl dialogRepository;
    private GetAllDialogsUseCaseImpl getAllDialogsUseCase;

    @BeforeEach
    void setup() {
        // mocket repository
        dialogRepository = Mockito.mock(DialogRepositoryImpl.class);

        // usecase to test
        getAllDialogsUseCase = new GetAllDialogsUseCaseImpl(dialogRepository);
    }

    @Test
    void testExecute_ReturnsAllDialogs() {
        // mocket data
        DialogEntity dialog1 = new DialogEntity();
        DialogEntity dialog2 = new DialogEntity();

        List<DialogEntity> dialogs = List.of(dialog1, dialog2);

        // what must return
        when(dialogRepository.findAll()).thenReturn(dialogs);

        // execute test
        List<DialogEntity> result = getAllDialogsUseCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dialog1));
        assertTrue(result.contains(dialog2));

        verify(dialogRepository, times(1)).findAll();
    }

    @Test
    void testExecute_ReturnsEmptyList() {
        // what must return
        when(dialogRepository.findAll()).thenReturn(List.of());

        // execute test
        List<DialogEntity> result = getAllDialogsUseCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(dialogRepository, times(1)).findAll();
    }
}
