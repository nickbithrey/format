package org.innovation.format.field.date;

public class DateFieldBuilder {

    private DateFieldBuilder() {
    }

    public static DateFieldObj buildField(String name, String format) {
        return new DateFieldObj(name, format);
    }

}
