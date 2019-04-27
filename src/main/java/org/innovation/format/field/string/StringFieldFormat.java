package org.innovation.format.field.string;

import org.innovation.format.field.FieldFormat;

public class StringFieldFormat implements FieldFormat {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T read(byte[] value, Class<T> clazz) {
        return (T) new String(value);
    }

    @Override
    public <T> byte[] write(T value) {
        return ((String) value).getBytes();
    }

}
