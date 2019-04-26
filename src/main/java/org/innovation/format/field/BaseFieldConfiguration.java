package org.innovation.format.field;

public abstract class BaseFieldConfiguration<T> implements FieldConfiguration<T> {

    private final long number;

    private final String name;

    public BaseFieldConfiguration(long number, String name) {
        super();
        this.number = number;
        this.name = name;
    }

    @Override
    public long getNumber() {
        return number;
    }

    @Override
    public String getName() {
        return name;
    }

}
