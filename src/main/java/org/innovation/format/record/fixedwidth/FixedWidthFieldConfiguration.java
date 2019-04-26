package org.innovation.format.record.fixedwidth;

import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;

public class FixedWidthFieldConfiguration<T extends Field<?>> implements FieldConfiguration<T> {

    private final long length;

    private final FieldConfiguration<? extends T> field;

    public FixedWidthFieldConfiguration(long length, FieldConfiguration<? extends T> field) {
        super();
        this.length = length;
        this.field = field;
    }

    public long getLength() {
        return length;
    }

    @Override
    public long getNumber() {
        return field.getNumber();
    }

    @Override
    public String getName() {
        return field.getName();
    }

    @Override
    public T buildField(String name) {
        return field.buildField(name);
    }

}
