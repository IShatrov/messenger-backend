package ru.mipt.messenger.dto;

public record SettingsDto(boolean darkTheme, boolean showDateOfBirth, 
                         boolean chatYourselfDefault, boolean contactAutoAccept) {}