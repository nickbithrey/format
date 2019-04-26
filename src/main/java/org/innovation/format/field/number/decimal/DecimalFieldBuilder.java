package org.innovation.format.field.number.decimal;

public class DecimalFieldBuilder {

    private DecimalFieldBuilder() {
    }

    public static DecimalFieldObj buildField(String name, String format) {
        return new DecimalFieldObj(name, format);
    }

}
