package org.innovation.format.exception;

import java.lang.annotation.ElementType;
import java.util.Collection;

public class InvalidFormatAnnotationException extends RuntimeException {

    private static final long serialVersionUID = -6907727975801052381L;

    public InvalidFormatAnnotationException(ElementType annotatedElementType, String elementName,
            Collection<Class<?>> validAnnotationClasses) {
        super(formatMessage(annotatedElementType, elementName, validAnnotationClasses));
    }

    public InvalidFormatAnnotationException(ElementType annotatedElementType, String elementName,
            Collection<Class<?>> validAnnotationClasses, Throwable throwable) {
        super(formatMessage(annotatedElementType, elementName, validAnnotationClasses), throwable);
    }

    private static String formatMessage(ElementType annotatedElementType, String elementName,
            Collection<Class<?>> validAnnotationClasses) {
        return String.format("could not find any valid Format annotations on %s %s. One of %s expected",
                annotatedElementType, elementName, validAnnotationClasses);
    }

}
