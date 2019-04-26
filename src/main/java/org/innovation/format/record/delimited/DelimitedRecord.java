package org.innovation.format.record.delimited;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface DelimitedRecord {

    String delimiter();

    byte[] delimiterBytes() default {};

}
