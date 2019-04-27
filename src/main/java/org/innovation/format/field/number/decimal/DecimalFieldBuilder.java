package org.innovation.format.field.number.decimal;

import org.innovation.format.field.BaseField;

public class DecimalFieldBuilder {

    private DecimalFieldBuilder() {
    }

    public static BaseField buildField(String name, String format) {
        return new BaseField(name, new DecimalFieldFormat(format));
    }

}
