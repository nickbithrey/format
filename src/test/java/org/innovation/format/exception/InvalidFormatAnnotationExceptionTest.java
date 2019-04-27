package org.innovation.format.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.ElementType;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

public class InvalidFormatAnnotationExceptionTest {

    @Test
    public void test() {
        ElementType type = ElementType.TYPE;
        String name = "className";
        Collection<Class<?>> validClasses = Collections.singleton(this.getClass());
        InvalidFormatAnnotationException exception = new InvalidFormatAnnotationException(type, name, validClasses);
        assertThat(exception).as("invalid format annotation exception")
                .hasMessage(String.format("could not find any valid Format annotations on %s %s. One of %s expected",
                        type, name, validClasses))
                .hasNoCause();
    }

}
