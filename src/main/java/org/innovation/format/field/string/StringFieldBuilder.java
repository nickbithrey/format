package org.innovation.format.field.string;

public class StringFieldBuilder {

    private StringFieldBuilder() {
    }

    public static StringFieldObj buildField(String name) {
        return new StringFieldObj(name);
    }

}
