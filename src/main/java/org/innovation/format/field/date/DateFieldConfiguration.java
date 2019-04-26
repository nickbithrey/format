package org.innovation.format.field.date;

import org.innovation.format.field.BaseFieldConfiguration;

public class DateFieldConfiguration extends BaseFieldConfiguration<DateFieldObj> {

    private String format;

    public DateFieldConfiguration(long number, String name) {
        super(number, name);
    }

    @Override
    public DateFieldObj buildField(String name) {
        return DateFieldBuilder.buildField(name, format);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
