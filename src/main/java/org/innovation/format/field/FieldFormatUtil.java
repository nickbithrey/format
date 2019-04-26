package org.innovation.format.field;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FieldFormatUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldFormatUtil.class);

    public static <T> T readField(Field<T> field) throws ParseException {
        if (field.isRead()) {
            T fieldValue = field.getValue();
            LOGGER.trace("Reading previously read field value {} for field {}", fieldValue, field);
            return fieldValue;
        }
        FieldFormat<T> fieldFormat = field.getFormatter();
        try {
            T value = fieldFormat.read(field.getRawValue());
            LOGGER.trace("Read field {} as {}", field, value);
            return value;
        } catch (ParseException e) {
            LOGGER.error("Error in formatting field {} with formatter {}", field, fieldFormat);
            throw e;
        }
    }

    public static <T> byte[] writeField(Field<T> field) {
        if (!field.isRead()) {
            return field.getRawValue();
        }
        FieldFormat<T> fieldFormat = field.getFormatter();
        byte[] value = fieldFormat.write(field.getValue());
        LOGGER.trace("Formatted {} into {} using formatter {}", field.getValue(), value, fieldFormat);
        return value;
    }
}
