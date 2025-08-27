package com.make_your_choice.domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import static com.make_your_choice.support.TestReflectionUtils.setField;

public class DialogEntityTest {
    @Test
    void testGetCodeReturnsNullWhenIdIsNull() throws Exception {
        DialogEntity dialog = new DialogEntity();
        setField(dialog, "id", null);

        assertNull(dialog.getCode());
    }

    @Test
    void testGetCodeReturnsCorrectCode() throws Exception {
        DialogEntity dialog = new DialogEntity();
        setField(dialog, "id", 42L);

        assertEquals("D42", dialog.getCode());
    }

    @Test
    void testGetDialog() throws Exception {
        DialogEntity dialog = new DialogEntity();
        setField(dialog, "dialog", "Hello im the dialog test!");

        assertEquals("Hello im the dialog test!", dialog.getDialog());
    }

    @Test
    void testGetChoices() throws Exception {
        ChoiceEntity choice1 = new ChoiceEntity();
        setField(choice1, "choice", "Choice 1");

        ChoiceEntity choice2 = new ChoiceEntity();
        setField(choice2, "choice", "Choice 2");

        DialogEntity dialog = new DialogEntity();
        setField(dialog, "choices", List.of(choice1, choice2));

        List<ChoiceEntity> results = dialog.getChoices();

        assertEquals(2, results.size());
        assertEquals("Choice 1", results.get(0).getChoice());
        assertEquals("Choice 2", results.get(1).getChoice());
    }
}
