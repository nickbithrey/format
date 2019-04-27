package org.innovation.format.record.fixedwidth;

import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;

public class FixedWidthFieldConfiguration implements FieldConfiguration {

    private final long length;

    private final FieldConfiguration fieldConfiguration;

    public FixedWidthFieldConfiguration(long length, FieldConfiguration fieldConfiguration) {
        super();
        this.length = length;
        this.fieldConfiguration = fieldConfiguration;
    }

    public long getLength() {
        return length;
    }

    @Override
    public long getNumber() {
        return fieldConfiguration.getNumber();
    }

    @Override
    public String getName() {
        return fieldConfiguration.getName();
    }

    @Override
    public Class<?> getType() {
        return fieldConfiguration.getType();
    }

    @Override
    public Field buildField(String name) {
        return fieldConfiguration.buildField(name);
    }

}
