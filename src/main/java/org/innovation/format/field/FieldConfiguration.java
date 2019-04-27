package org.innovation.format.field;

/**
 * Configuration object for a Field
 *
 * @author nick.bithrey
 *
 * @param <T>
 */
public interface FieldConfiguration extends Comparable<FieldConfiguration> {

    /**
     * @return field number in record
     */
    long getNumber();

    /**
     * @return field name
     */
    String getName();

    Class<?> getType();

    /**
     * builds the {@link Field} object from the configuration
     *
     * @param name
     * @return the {@link Field}
     */
    Field buildField(String name);

    @Override
    default int compareTo(FieldConfiguration o) {
        return Long.valueOf(getNumber()).compareTo(Long.valueOf(o.getNumber()));
    }

}
