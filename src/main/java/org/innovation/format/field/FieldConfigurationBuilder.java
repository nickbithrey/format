package org.innovation.format.field;

import java.lang.annotation.Annotation;

/**
 * builds the configuration for a field based on the custom Format Annotations
 *
 * @author nick.bithrey
 *
 * @param <T>
 */
public interface FieldConfigurationBuilder<T extends FieldConfiguration<? extends Field<?>>> {

    /**
     * builds the field configuration from the supplied annotation
     *
     * @param annotation
     * @return the {@link FieldConfiguration}
     */
    T build(Annotation annotation);

    /**
     * resolve the applicable annotation that is attributed to the supplied field
     *
     * @param f
     * @return resolved custom Format annotation on field or null if none found
     */
    Annotation findValidAnnotation(java.lang.reflect.Field f);

}
