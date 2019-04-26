package org.innovation.format.field.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.innovation.format.field.PreStringFormatFieldFormat;

/**
 * reading and writing a {@link Date} object
 * 
 * @author nick.bithrey
 *
 */
public class DateFieldFormat extends PreStringFormatFieldFormat<Date> {

    private final ThreadLocal<DateFormat> format;

    public DateFieldFormat(String pattern) {
        super();
        this.format = ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern));
    }

    private DateFormat getFormat() {
        return format.get();
    }

    @Override
    public Date readInner(String value) throws ParseException {
        return getFormat().parse(value);
    }

    @Override
    public String writeInner(Date value) {
        return getFormat().format(value);
    }

}
