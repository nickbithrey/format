package org.innovation.format.field;

public abstract class BaseField<T> implements Field<T> {

    private final String name;

    private T value;

    private byte[] rawValue;

    private boolean read;

    public BaseField(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
        this.read = true;
    }

    @Override
    public boolean isRead() {
        return read;
    }

    @Override
    public byte[] getRawValue() {
        return rawValue;
    }

    @Override
    public void setRawValue(byte[] rawValue) {
        this.rawValue = rawValue;
    }

}
