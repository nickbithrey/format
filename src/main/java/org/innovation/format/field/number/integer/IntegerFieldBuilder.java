package org.innovation.format.field.number.integer;

public class IntegerFieldBuilder {

    private IntegerFieldBuilder() {
    }

    public static IntegerFieldObj buildField(String name, String format) {
        return new IntegerFieldObj(name, format);
    }

}
