package com.make_your_choice.application.usecases.choice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.make_your_choice.application.usecases.choice.getallchoice.GetAllChoiceUseCaseImpl;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.infrastructure.repositories.ChoiceRepositoryImpl;

public class GetAllChoiceUseCaseImplTest {
    private ChoiceRepositoryImpl choiceRepository;
    private GetAllChoiceUseCaseImpl getAllChoicesUseCase;

    @BeforeEach
    void setup() {
        choiceRepository = Mockito.mock(ChoiceRepositoryImpl.class);
        getAllChoicesUseCase = new GetAllChoiceUseCaseImpl(choiceRepository);
    }

    @Test
    void testExecute_ReturnsAllChoices() {
        ChoiceEntity choice1 = new ChoiceEntity();
        ChoiceEntity choice2 = new ChoiceEntity();

        List<ChoiceEntity> choices = List.of(choice1, choice2);

        when(choiceRepository.findAll()).thenReturn(choices);

        // execute test
        List<ChoiceEntity> result = getAllChoicesUseCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(choice1));
        assertTrue(result.contains(choice2));

        verify(choiceRepository, times(1)).findAll();

    }

    @Test
    void testExecute_ReturnsEmptyList() {
        when(choiceRepository.findAll()).thenReturn(List.of());

        // execute test
        List<ChoiceEntity> result = getAllChoicesUseCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(choiceRepository, times(1)).findAll();
    }
}
