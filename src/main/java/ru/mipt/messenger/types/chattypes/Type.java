package ru.mipt.messenger.types.chattypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {
    Private("private"),
    Group("group");

    private final String text;

    Type(String text) {
        this.text = text;
    }

    @Override
    @JsonValue
    public String toString() {
        return text;
    }

    @JsonCreator
    public static Type fromText(String text) {
        for (Type t : Type.values()) {
            if (t.toString().equals(text)) {
                return t;
            }
        }

        throw new IllegalArgumentException();
    }
}
