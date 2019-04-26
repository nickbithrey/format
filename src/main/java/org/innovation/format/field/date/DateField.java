package org.innovation.format.field.date;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.innovation.format.field.FormatField;

@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@FormatField
public @interface DateField {

    int number();

    String name();

    String format() default "yyyy-MM-dd";

}
