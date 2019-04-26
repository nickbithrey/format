package org.innovation.format.field.number.integer;

import org.innovation.format.field.BaseField;

public class IntegerFieldObj extends BaseField<Long> {

    private final String format;

    public IntegerFieldObj(String name, String format) {
        super(name);
        this.format = format;
    }

    @Override
    public IntegerFieldFormat getFormatter() {
        return new IntegerFieldFormat(format);
    }

}
