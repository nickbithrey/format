package org.innovation.format.field;

import java.text.ParseException;

/**
 * read and write a supplied value T using its formatting details
 *
 * @author nick.bithrey
 *
 * @param <T>
 */
public interface FieldFormat {

    /**
     * reads the byte array into the desired java object of parameterised type
     *
     * @param value
     * @return the parameterised type java object
     * @throws ParseException
     *             if the formatter cannot parse the byte array into the object
     */
    <T extends Object> T read(byte[] value, Class<T> clazz) throws ParseException;

    /**
     * writes the parameterised type java object into a byte array to be written to an output stream
     *
     * @param value
     * @return the byte array for the supplied java object
     */
    <T extends Object> byte[] write(T value);

}
