package org.innovation.format.field.number;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import org.innovation.format.field.PreStringFormatFieldFormat;
import org.springframework.util.Assert;

/**
 * reading and writing a number type field
 *
 * @author nick.bithrey
 *
 * @param <T>
 *            must extend a {@link Number}
 */
public abstract class NumberFieldFormat<T extends Number> extends PreStringFormatFieldFormat<T> {

    protected final NumberFormat format;

    public NumberFieldFormat(String pattern) {
        super();
        Assert.hasLength(pattern, "pattern cannot be blank");
        this.format = new DecimalFormat(pattern);
    }

    protected Number parseValue(String value) throws ParseException {
        return format.parse(value);
    }

    @Override
    public String writeInner(T value) {
        return format.format(value);
    }

}
