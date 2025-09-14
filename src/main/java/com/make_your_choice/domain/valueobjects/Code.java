package com.make_your_choice.domain.valueobjects;

import java.util.Optional;

public final class Code {

    private final Long id;
    private final String prefix;

    private Code(String prefix, Long id) {
        this.prefix = prefix;
        this.id = id;
    }

    public static Optional<Code> fromString(String code, String expectedPrefix) {
        if (code == null || !code.startsWith(expectedPrefix))
            return Optional.empty();
        try {
            Long id = Long.parseLong(code.substring(expectedPrefix.length()));
            return Optional.of(new Code(expectedPrefix, id));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Long getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }
}
