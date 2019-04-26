package org.innovation.format.record;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * builds the record configuration from the customer Format annotations
 *
 * @author nick.bithrey
 *
 * @param <T>
 *            extending {@link RecordConfiguration}
 */
public interface RecordConfigurationTypeBuilder<T extends RecordConfiguration, V extends Annotation> {

    /**
     * @param annotation
     * @param fields
     * @return the built {@link RecordConfiguration} using the supplied {@link FieldConfiguration}s
     */
    T build(Annotation annotation, Set<FieldConfiguration<? extends Field<?>>> fields);

    /**
     * @param clazz
     * @return the valid Format annotation against the class. This will throw an @Invalid
     */
    default Annotation findBuilderAnnotation(Class<?> clazz) {
        return AnnotationUtils.findAnnotation(clazz, getBuilderAnnotationClass());
    }

    Class<V> getBuilderAnnotationClass();

}
