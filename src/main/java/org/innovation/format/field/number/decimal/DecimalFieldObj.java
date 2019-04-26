package org.innovation.format.field.number.decimal;

import org.innovation.format.field.BaseField;

public class DecimalFieldObj extends BaseField<Double> {

    private final String format;

    public DecimalFieldObj(String name, String format) {
        super(name);
        this.format = format;
    }

    @Override
    public DecimalFieldFormat getFormatter() {
        return new DecimalFieldFormat(format);
    }

}
