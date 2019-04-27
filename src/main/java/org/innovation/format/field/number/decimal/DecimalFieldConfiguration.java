package org.innovation.format.field.number.decimal;

import org.innovation.format.field.BaseFieldConfiguration;
import org.innovation.format.field.Field;

public class DecimalFieldConfiguration extends BaseFieldConfiguration {

    private String format;

    public DecimalFieldConfiguration(long number, String name) {
        super(number, name, Double.class);
    }

    @Override
    public Field buildField(String name) {
        return DecimalFieldBuilder.buildField(name, format);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
