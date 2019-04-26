package org.innovation.format.field.number.decimal;

import java.text.ParseException;

import org.innovation.format.field.number.NumberFieldFormat;

/**
 * read and write a Decimal field into a {@link Double}
 * 
 * @author nick.bithrey
 *
 */
public class DecimalFieldFormat extends NumberFieldFormat<Double> {

    public DecimalFieldFormat(String pattern) {
        super(pattern);
        format.setParseIntegerOnly(false);
    }

    @Override
    public Double readInner(String value) throws ParseException {
        return parseValue(value).doubleValue();
    }
}
