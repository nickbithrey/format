package org.innovation.format.field.date;

import java.util.Date;

import org.innovation.format.field.BaseFieldConfiguration;
import org.innovation.format.field.Field;

public class DateFieldConfiguration extends BaseFieldConfiguration {

    private String format;

    public DateFieldConfiguration(long number, String name) {
        super(number, name, Date.class);
    }

    @Override
    public Field buildField(String name) {
        return DateFieldBuilder.buildField(name, format);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
