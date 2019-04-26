package org.innovation.format.field.number.integer;

import org.innovation.format.field.BaseFieldConfiguration;

public class IntegerFieldConfiguration extends BaseFieldConfiguration<IntegerFieldObj> {

    private String format;

    public IntegerFieldConfiguration(long number, String name) {
        super(number, name);
    }

    @Override
    public IntegerFieldObj buildField(String name) {
        return IntegerFieldBuilder.buildField(name, format);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
