package org.innovation.format.field;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FieldFormatUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldFormatUtil.class);

    private FieldFormatUtil() {

    }

    public static <T> T readField(Field field, Class<T> clazz) throws ParseException {
        FieldFormat fieldFormat = field.getFormatter();
        try {
            T value = fieldFormat.read(field.getValue(), clazz);
            LOGGER.trace("Read field {} as {}", field, value);
            return value;
        } catch (ParseException e) {
            LOGGER.error("Error in formatting field {} with formatter {}", field, fieldFormat);
            throw e;
        }
    }

    public static <T> void writeField(Field field, T value) {
        field.setValue(field.getFormatter().write(value));
    }
}
