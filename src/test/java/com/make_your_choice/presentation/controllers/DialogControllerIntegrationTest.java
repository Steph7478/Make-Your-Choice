package com.make_your_choice.presentation.controllers;

import static com.make_your_choice.support.TestReflectionUtils.setField;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.make_your_choice.application.usecases.dialog.getalldialog.GetAllDialogsUseCaseImpl;
import com.make_your_choice.application.usecases.dialog.getchoicesbydialogcode.GetChoicesByDialogCodeUseCaseImpl;
import com.make_your_choice.application.usecases.dialog.getdialogbyid.GetDialogByIdUseCaseImpl;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;

@WebMvcTest(DialogController.class)
@ActiveProfiles("test")
public class DialogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GetAllDialogsUseCaseImpl getAllDialogsUseCase;

    @Autowired
    private GetChoicesByDialogCodeUseCaseImpl getChoicesByDialogCodeUseCase;

    @Autowired
    private GetDialogByIdUseCaseImpl getDialogByIdUseCase;

    private DialogEntity dialog1;
    private DialogEntity dialog2;
    private ChoiceEntity choice1;
    private ChoiceEntity choice2;

    @TestConfiguration
    static class MockUseCasesConfig {
        @Bean
        public GetAllDialogsUseCaseImpl getAllDialogsUseCase() {
            return Mockito.mock(GetAllDialogsUseCaseImpl.class);
        }

        @Bean
        public GetChoicesByDialogCodeUseCaseImpl getChoicesByDialogCodeUseCase() {
            return Mockito.mock(GetChoicesByDialogCodeUseCaseImpl.class);
        }

        @Bean
        public GetDialogByIdUseCaseImpl getDialogByIdUseCase() {
            return Mockito.mock(GetDialogByIdUseCaseImpl.class);
        }
    }

    @BeforeEach
    void setup() {
        dialog1 = new DialogEntity();
        setField(dialog1, "id", 42L);
        setField(dialog1, "dialog", "First Dialog");

        dialog2 = new DialogEntity();
        setField(dialog2, "id", 43L);
        setField(dialog2, "dialog", "Second Dialog");

        choice1 = new ChoiceEntity();
        setField(choice1, "dialog", dialog1);
        setField(choice1, "choice", "First Choice");

        choice2 = new ChoiceEntity();
        setField(choice2, "dialog", dialog1);
        setField(choice2, "choice", "Second Choice");

        when(getAllDialogsUseCase.execute()).thenReturn(List.of(dialog1, dialog2));
        when(getChoicesByDialogCodeUseCase.execute("D42")).thenReturn(List.of(choice1, choice2));
        when(getDialogByIdUseCase.execute("D42")).thenReturn(Optional.of(dialog1));
    }

    @Test
    void testGetAllDialogs() throws Exception {
        mockMvc.perform(get("/dialog/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value(dialog1.getCode()))
                .andExpect(jsonPath("$[1].code").value(dialog2.getCode()));
    }

    @Test
    void testGetChoicesByDialogCodePath() throws Exception {
        mockMvc.perform(get("/dialog/choices/D42").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value(choice1.getCode()))
                .andExpect(jsonPath("$[0].choice").value(choice1.getChoice()))
                .andExpect(jsonPath("$[1].code").value(choice2.getCode()))
                .andExpect(jsonPath("$[1].choice").value(choice2.getChoice()));
    }

    @Test
    void testGetDialogById() throws Exception {
        mockMvc.perform(get("/dialog/D42").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(dialog1.getCode()))
                .andExpect(jsonPath("$.choices").value(dialog1.getChoices()))
                .andExpect(jsonPath("$.dialog").value(dialog1.getDialog()));
    }

}
