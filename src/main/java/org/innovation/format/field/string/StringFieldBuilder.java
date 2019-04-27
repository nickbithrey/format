package org.innovation.format.field.string;

import org.innovation.format.field.BaseField;

public class StringFieldBuilder {

    private StringFieldBuilder() {
    }

    public static BaseField buildField(String name) {
        return new BaseField(name, new StringFieldFormat());
    }

}
