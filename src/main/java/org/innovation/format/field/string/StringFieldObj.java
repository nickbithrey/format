package org.innovation.format.field.string;

import org.innovation.format.field.BaseField;

public class StringFieldObj extends BaseField<String> {

    private static final StringFieldFormat FORMAT = new StringFieldFormat();

    public StringFieldObj(String name) {
        super(name);
    }

    @Override
    public StringFieldFormat getFormatter() {
        return FORMAT;
    }

}
