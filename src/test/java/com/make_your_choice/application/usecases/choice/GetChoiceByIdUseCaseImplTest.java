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

import com.make_your_choice.application.usecases.choice.getchoicebyid.GetChoiceByIdUseCaseImpl;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.infrastructure.repositories.ChoiceRepositoryImpl;

public class GetChoiceByIdUseCaseImplTest {
    private ChoiceRepositoryImpl choiceRepository;
    private GetChoiceByIdUseCaseImpl getChoiceByIdUseCase;

    @BeforeEach
    void setup() {
        // choice repository
        choiceRepository = Mockito.mock(ChoiceRepositoryImpl.class);

        // usecase to test
        getChoiceByIdUseCase = new GetChoiceByIdUseCaseImpl(choiceRepository);
    }

    @Test
    void testGetChoiceById() {
        // mocket data
        String code = "C12";
        ChoiceEntity choice = new ChoiceEntity();
        setField(choice, "id", 12L);

        // what must return
        when(choiceRepository.findByCode(code)).thenReturn(Optional.of(choice));

        // execute test
        Optional<ChoiceEntity> result = getChoiceByIdUseCase.execute(code);

        assertTrue(result.isPresent());
        assertEquals(choice, result.get());

        verify(choiceRepository, times(1)).findByCode(code);
    }
}
