package org.innovation.format.field.string;

import org.innovation.format.field.BaseFieldConfiguration;
import org.innovation.format.field.Field;

public class StringFieldConfiguration extends BaseFieldConfiguration {

    public StringFieldConfiguration(long number, String name) {
        super(number, name, String.class);
    }

    @Override
    public Field buildField(String name) {
        return StringFieldBuilder.buildField(name);
    }

}
