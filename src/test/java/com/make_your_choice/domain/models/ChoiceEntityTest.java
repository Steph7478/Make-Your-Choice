package com.make_your_choice.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import static com.make_your_choice.support.TestReflectionUtils.setField;

public class ChoiceEntityTest {
    @Test
    void testGetCodeReturnsNullWhenIdIsNull()
            throws Exception {
        ChoiceEntity choice = new ChoiceEntity();
        setField(choice, "id", null);

        assertNull(choice.getCode());
    }

    @Test
    void testGetCodeReturnsCorrectCode()
            throws Exception {
        ChoiceEntity choice = new ChoiceEntity();
        setField(choice, "id", 42L);

        assertEquals("C42", choice.getCode());
    }

    @Test
    void testGetChoice()
            throws Exception {
        ChoiceEntity choice = new ChoiceEntity();
        setField(choice, "choice", "Hello im the choice text!");

        assertEquals("Hello im the choice text!", choice.getChoice());
    }

    @Test
    void testGetDialog()
            throws Exception {
        ChoiceEntity choice = new ChoiceEntity();
        DialogEntity dialog = new DialogEntity();
        setField(choice, "dialog", dialog);

        assertEquals(dialog, choice.getDialog());
    }

    @Test
    void testGetNextDialog()
            throws Exception {
        ChoiceEntity choice = new ChoiceEntity();
        DialogEntity dialog = new DialogEntity();

        setField(choice, "nextDialog", dialog);

        assertEquals(dialog, choice.getNextDialog());
    }
}
