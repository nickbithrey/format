package org.innovation.format.field;

/**
 * the field object that stores the current field value in its raw form along with the formatter for
 * this field and its name
 *
 * @author nick.bithrey
 *
 * @param <T>
 */
public interface Field<T extends Object> {

    String getName();

    /**
     * should not be used to retrieve the value from the field. should use
     * {@link FieldFormatUtil#readField(Field)} instead
     *
     * @return
     */
    T getValue();

    void setValue(T value);

    /**
     * @return whether the field has been read and processed. If so then {@link #getValue()} is used
     *         to retrieve the value
     */
    boolean isRead();

    /**
     * @return the raw byte array for the field
     */
    byte[] getRawValue();

    void setRawValue(byte[] value);

    /**
     * @return the formatter for the given field
     */
    FieldFormat<T> getFormatter();

}
