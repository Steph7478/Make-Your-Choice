package com.make_your_choice.support;

import java.lang.reflect.Field;

/**
 * Utility class for manipulating object fields using Java Reflection.
 *
 *
 * What is Reflection?
 * Reflection is a feature in Java that allows programs to inspect and
 * manipulate
 * classes, methods, and fields at runtime, even if they are private or
 * protected.
 * This can be useful for testing, frameworks, or dynamic behavior, but should
 * be used carefully since it breaks encapsulation.
 *
 */

public class TestReflectionUtils {

    /**
     * Sets the value of a field in the given target object using Java Reflection.
     * This method can access private or protected fields by forcing accessibility.
     *
     * @param target    the object whose field should be modified
     * @param fieldName the name of the field to set
     * @param value     the new value for the field
     */
    public static void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Reflection set error", e);
        }
    }

    /**
     * Gets the value of a field in the given target object using Java Reflection.
     * This method can access private or protected fields by forcing accessibility.
     *
     * @param target    the object whose field value should be retrieved
     * @param fieldName the name of the field to get
     * @return the current value of the field
     */
    public static Object getField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException("Reflection get error", e);
        }
    }
}
