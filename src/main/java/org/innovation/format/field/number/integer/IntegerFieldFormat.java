package org.innovation.format.field.number.integer;

import java.text.ParseException;

import org.innovation.format.field.number.NumberFieldFormat;

/**
 * read and write an Integer field into an {@link Integer}
 *
 * @author nick.bithrey
 *
 */
public class IntegerFieldFormat extends NumberFieldFormat {

    public IntegerFieldFormat(String pattern) {
        super(pattern);
        format.setParseIntegerOnly(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Object> T readInner(String value) throws ParseException {
        return (T) Long.valueOf(parseValue(value).longValue());
    }
}
