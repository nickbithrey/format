package org.innovation.format.field;

import java.text.ParseException;

import org.innovation.format.field.string.StringFieldFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * formatter that requires a format into string before can be formatted into the true value
 *
 * @author nick.bithrey
 *
 * @param <T>
 */
public abstract class PreStringFormatFieldFormat<T> implements FieldFormat<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreStringFormatFieldFormat.class);

    private static final StringFieldFormat STRING_FORMAT = new StringFieldFormat();

    @Override
    public T read(byte[] value) throws ParseException {
        String strValue = STRING_FORMAT.read(value);
        LOGGER.trace("Read bytes {} into {} first, then reading using {}", value, strValue, getClass().getSimpleName());
        return readInner(strValue);
    }

    protected abstract T readInner(String strRead) throws ParseException;

    @Override
    public byte[] write(T value) {
        String strValue = writeInner(value);
        LOGGER.trace("Written {} into {} first, then writing to bytes using {}", value, strValue,
                getClass().getSimpleName());
        return STRING_FORMAT.write(strValue);
    }

    protected abstract String writeInner(T strRead);
}
