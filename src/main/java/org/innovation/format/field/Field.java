package org.innovation.format.field;

/**
 * the field object that stores the current field value in its raw form along with the formatter for
 * this field and its name
 *
 * @author nick.bithrey
 *
 * @param <T>
 */
public interface Field {

    String getName();

    /**
     * @return the raw byte array for the field
     */
    byte[] getValue();

    /**
     * @return the formatter for the given field
     */
    FieldFormat getFormatter();

    /**
     * set the value on the field
     * 
     * @param value
     */
    void setValue(byte[] value);

}
