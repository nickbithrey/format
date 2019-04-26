package org.innovation.format.field.string;

import org.innovation.format.field.BaseFieldConfiguration;

public class StringFieldConfiguration extends BaseFieldConfiguration<StringFieldObj> {

    public StringFieldConfiguration(long number, String name) {
        super(number, name);
    }

    @Override
    public StringFieldObj buildField(String name) {
        return StringFieldBuilder.buildField(name);
    }

}
