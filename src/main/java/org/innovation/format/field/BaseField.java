package org.innovation.format.field;

import java.util.Arrays;

public class BaseField implements Field {

    private final String name;

    private final FieldFormat formatter;

    private byte[] value;

    public BaseField(String name, FieldFormat formatter) {
        super();
        this.name = name;
        this.formatter = formatter;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FieldFormat getFormatter() {
        return formatter;
    }

    @Override
    public byte[] getValue() {
        return value;
    }

    @Override
    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("BaseField [name=%s, formatter=%s, value=%s]", name, formatter, Arrays.toString(value));
    }

}
