package ru.mipt.messenger.types.chattypes;

/**
 * Helper type for Chat class. Values are not capitalised to make it easier to map from JSON to database.
 * @see <a href="https://stackoverflow.com/questions/33637427/spring-requestbody-and-enum-value">Stack Overflow</a>
 */
public enum Type {
    Private,
    Group
}
