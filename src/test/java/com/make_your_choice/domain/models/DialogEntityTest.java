package com.make_your_choice.domain.models;

import static org.junit.jupiter.api.Assertions.*;

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
    void testGetText() throws Exception {
        DialogEntity dialog = new DialogEntity();
        setField(dialog, "text", "Hello im the dialog test!");

        assertEquals("Hello im the dialog test!", dialog.getText());
    }
}
