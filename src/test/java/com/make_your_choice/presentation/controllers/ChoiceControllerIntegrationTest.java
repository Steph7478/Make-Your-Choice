package com.make_your_choice.presentation.controllers;

import com.make_your_choice.application.usecases.choice.getallchoice.GetAllChoiceUseCase;
import com.make_your_choice.application.usecases.choice.getchoicebyid.GetChoiceByIdUseCase;
import com.make_your_choice.application.usecases.choice.getdialogbycode.GetDialogByCodeUseCase;
import com.make_your_choice.application.usecases.choice.getnextdialogbycode.GetNextDialogByCodeUseCase;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import static com.make_your_choice.support.TestReflectionUtils.setField;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChoiceController.class)
@ActiveProfiles("test")
public class ChoiceControllerIntegrationTest {

    // This gonna be used to execute the test. It performs full Spring MVC request
    // handling but via mock request and response objects instead of a running
    // server
    @Autowired
    private MockMvc mockMvc;

    // all the dependencies for the test
    @Autowired
    private GetAllChoiceUseCase getAllChoiceUseCase;

    @Autowired
    private GetChoiceByIdUseCase getChoiceByIdUseCase;

    @Autowired
    private GetNextDialogByCodeUseCase getNextDialogByCodeUseCase;

    // to create the entities, since im gonna use it in many tests, i decided to set
    // it here at the top of the module, instead of setting it inside of the @Test,
    // for less repetition
    private ChoiceEntity choice;
    private DialogEntity dialog;
    private DialogEntity nextDialog;

    // here i must configurate beans of my dependencies, since im gonna use then
    // indirectly
    @TestConfiguration
    static class MockUseCasesConfig {
        @Bean
        public GetAllChoiceUseCase getAllChoiceUseCase() {
            return Mockito.mock(GetAllChoiceUseCase.class);
        }

        @Bean
        public GetChoiceByIdUseCase getChoiceByIdUseCase() {
            return Mockito.mock(GetChoiceByIdUseCase.class);
        }

        @Bean
        public GetDialogByCodeUseCase getDialogByCodeUseCase() {
            return Mockito.mock(GetDialogByCodeUseCase.class);
        }

        @Bean
        public GetNextDialogByCodeUseCase getNextDialogByCodeUseCase() {
            return Mockito.mock(GetNextDialogByCodeUseCase.class);
        }
    }

    // here i start to create the mocked tables
    @BeforeEach
    void setup() {
        dialog = new DialogEntity();
        setField(dialog, "id", 12L);
        setField(dialog, "dialog", "Dialog 1");

        nextDialog = new DialogEntity();
        setField(nextDialog, "id", 13L);
        setField(nextDialog, "dialog", "Dialog 2");

        choice = new ChoiceEntity();
        setField(choice, "id", 20L);
        setField(choice, "choice", "Choice 1");
        setField(choice, "dialog", dialog);
        setField(choice, "nextDialog", nextDialog);

        // im saying to mockito what they must return
        Mockito.when(getAllChoiceUseCase.execute()).thenReturn(List.of(choice));
        Mockito.when(getChoiceByIdUseCase.execute("C20")).thenReturn(Optional.of(choice));
        Mockito.when(getNextDialogByCodeUseCase.execute("D13")).thenReturn(Optional.of(choice));
    }

    // here i use mockMvc to execute and verify my integration tests
    @Test
    void testGetAllChoices() throws Exception {
        mockMvc.perform(get("/choices/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].code").value(choice.getCode()));
    }

    @Test
    void testGetChoiceById() throws Exception {
        mockMvc.perform(get("/choices/C20").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(choice.getCode()));
    }

    @Test
    void testGetNextChoiceByDialogCode() throws Exception {
        mockMvc.perform(get("/choices/next/D13").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nextDialogCode").value(nextDialog.getCode()));

    }
}
