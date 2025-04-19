package ru.mipt.messenger.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.mipt.messenger.types.chattypes.Type;

@Converter(autoApply = true)
public class ChatTypeConverter implements AttributeConverter<Type, String> {
    @Override
    public String convertToDatabaseColumn(Type attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public Type convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Type.fromText(dbData);
    }
}
