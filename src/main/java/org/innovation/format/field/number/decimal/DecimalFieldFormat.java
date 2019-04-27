package org.innovation.format.field.number.decimal;

import java.text.ParseException;

import org.innovation.format.field.number.NumberFieldFormat;

/**
 * read and write a Decimal field into a {@link Double}
 *
 * @author nick.bithrey
 *
 */
public class DecimalFieldFormat extends NumberFieldFormat {

    public DecimalFieldFormat(String pattern) {
        super(pattern);
        format.setParseIntegerOnly(false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Object> T readInner(String value) throws ParseException {
        return (T) Double.valueOf(parseValue(value).doubleValue());
    }
}
