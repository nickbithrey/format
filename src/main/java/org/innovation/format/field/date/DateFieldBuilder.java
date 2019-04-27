package org.innovation.format.field.date;

import org.innovation.format.field.BaseField;

public class DateFieldBuilder {

    private DateFieldBuilder() {
    }

    public static BaseField buildField(String name, String format) {
        return new BaseField(name, new DateFieldFormat(format));
    }

}
