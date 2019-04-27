package org.innovation.format.field;

public abstract class BaseFieldConfiguration implements FieldConfiguration {

    private final long number;

    private final String name;

    private final Class<?> type;

    public BaseFieldConfiguration(long number, String name, Class<?> type) {
        super();
        this.number = number;
        this.name = name;
        this.type = type;
    }

    @Override
    public long getNumber() {
        return number;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

}
