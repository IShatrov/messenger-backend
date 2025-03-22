package ru.mipt.messenger.types.usertypes;

/**
 * Helper type for User class. Values are not capitalised to make it easier to map from JSON to database.
 * @see <a href="https://stackoverflow.com/questions/33637427/spring-requestbody-and-enum-value">Stack Overflow</a>
 */
public enum Role {
    Admin,
    User
}
