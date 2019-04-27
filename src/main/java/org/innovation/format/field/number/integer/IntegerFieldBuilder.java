package org.innovation.format.field.number.integer;

import org.innovation.format.field.BaseField;

public class IntegerFieldBuilder {

    private IntegerFieldBuilder() {
    }

    public static BaseField buildField(String name, String format) {
        return new BaseField(name, new IntegerFieldFormat(format));
    }

}
