package org.innovation.format.field.number.decimal;

import org.innovation.format.field.BaseFieldConfiguration;

public class DecimalFieldConfiguration extends BaseFieldConfiguration<DecimalFieldObj> {

    private String format;

    public DecimalFieldConfiguration(long number, String name) {
        super(number, name);
    }

    @Override
    public DecimalFieldObj buildField(String name) {
        return DecimalFieldBuilder.buildField(name, format);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
