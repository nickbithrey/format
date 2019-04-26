package org.innovation.format.field.string;

import org.innovation.format.field.FieldFormat;

public class StringFieldFormat implements FieldFormat<String> {

    @Override
    public String read(byte[] value) {
        return new String(value);
    }

    @Override
    public byte[] write(String value) {
        return value.getBytes();
    }
}
