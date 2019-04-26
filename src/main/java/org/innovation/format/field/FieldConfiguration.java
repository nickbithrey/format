package org.innovation.format.field;

/**
 * Configuration object for a Field
 *
 * @author nick.bithrey
 *
 * @param <T>
 */
public interface FieldConfiguration<T> extends Comparable<FieldConfiguration<T>> {

    /**
     * @return field number in record
     */
    long getNumber();

    /**
     * @return field name
     */
    String getName();

    /**
     * builds the {@link Field} object from the configuration
     *
     * @param name
     * @return the {@link Field}
     */
    T buildField(String name);

    @Override
    default int compareTo(FieldConfiguration<T> o) {
        return Long.valueOf(getNumber()).compareTo(Long.valueOf(o.getNumber()));
    }

}
