package com.make_your_choice.presentation.controllers;

import com.make_your_choice.application.usecases.choice.getallchoice.GetAllChoiceUseCase;
import com.make_your_choice.application.usecases.choice.getchoicebyid.GetChoiceByIdUseCase;
import com.make_your_choice.application.usecases.choice.getdialogbycode.GetDialogByCodeUseCase;
import com.make_your_choice.application.usecases.choice.getnextdialogbycode.GetNextDialogByCodeUseCase;
import com.make_your_choice.domain.entities.ChoiceEntity;
import com.make_your_choice.domain.entities.DialogEntity;
import com.make_your_choice.support.TestReflectionUtils;
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

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChoiceController.class)
@ActiveProfiles("test")
public class ChoiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GetAllChoiceUseCase getAllChoiceUseCase;

    @Autowired
    private GetChoiceByIdUseCase getChoiceByIdUseCase;

    @Autowired
    private GetDialogByCodeUseCase getDialogByCodeUseCase;

    @Autowired
    private GetNextDialogByCodeUseCase getNextDialogByCodeUseCase;

    private ChoiceEntity choice;
    private DialogEntity dialog;
    private DialogEntity nextDialog;

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

    @BeforeEach
    void setup() {
        dialog = new DialogEntity();
        TestReflectionUtils.setField(dialog, "id", 12L);
        TestReflectionUtils.setField(dialog, "dialog", "Dialog 1");

        nextDialog = new DialogEntity();
        TestReflectionUtils.setField(nextDialog, "id", 13L);
        TestReflectionUtils.setField(nextDialog, "dialog", "Dialog 2");

        choice = new ChoiceEntity();
        TestReflectionUtils.setField(choice, "id", 20L);
        TestReflectionUtils.setField(choice, "choice", "Choice 1");
        TestReflectionUtils.setField(choice, "dialog", dialog);
        TestReflectionUtils.setField(choice, "nextDialog", nextDialog);

        Mockito.when(getAllChoiceUseCase.execute()).thenReturn(List.of(choice));
        Mockito.when(getChoiceByIdUseCase.execute("C20")).thenReturn(Optional.of(choice));
        Mockito.when(getDialogByCodeUseCase.execute("D12")).thenReturn(Optional.of(choice));
        Mockito.when(getNextDialogByCodeUseCase.execute("D13")).thenReturn(Optional.of(choice));
    }

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
    void testGetChoiceByDialogCode() throws Exception {
        mockMvc.perform(get("/choices/dialog/D12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dialogCode").value(dialog.getCode()));

    }

    @Test
    void testGetNextChoiceByDialogCode() throws Exception {
        mockMvc.perform(get("/choices/next/D13").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nextDialogCode").value(nextDialog.getCode()));

    }
}
