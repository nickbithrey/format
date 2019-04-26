package org.innovation.format.field.date;

import java.util.Date;

import org.innovation.format.field.BaseField;

public class DateFieldObj extends BaseField<Date> {

    private final String pattern;

    public DateFieldObj(String name, String pattern) {
        super(name);
        this.pattern = pattern;
    }

    @Override
    public DateFieldFormat getFormatter() {
        return new DateFieldFormat(pattern);
    }

}
