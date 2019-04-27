package org.innovation.format.field.number.integer;

import org.innovation.format.field.BaseFieldConfiguration;
import org.innovation.format.field.Field;

public class IntegerFieldConfiguration extends BaseFieldConfiguration {

    private String format;

    public IntegerFieldConfiguration(long number, String name) {
        super(number, name, Long.class);
    }

    @Override
    public Field buildField(String name) {
        return IntegerFieldBuilder.buildField(name, format);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
