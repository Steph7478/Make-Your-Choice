package com.make_your_choice.support;

import java.lang.reflect.Field;

public class TestReflectionUtils {

    public static void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Reflection set error", e);
        }
    }
}
