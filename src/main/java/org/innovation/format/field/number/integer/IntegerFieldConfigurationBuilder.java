package org.innovation.format.field.number.integer;

import java.lang.annotation.Annotation;

import org.innovation.format.field.FieldConfigurationBuilder;
import org.springframework.core.annotation.AnnotationUtils;

public class IntegerFieldConfigurationBuilder implements FieldConfigurationBuilder<IntegerFieldConfiguration> {

    private static IntegerFieldConfiguration buildField(IntegerField integerField) {
        IntegerFieldConfiguration configuration = new IntegerFieldConfiguration(integerField.number(),
                integerField.name());
        configuration.setFormat(integerField.format());
        return configuration;
    }

    @Override
    public IntegerFieldConfiguration build(Annotation annotation) {
        if (annotation instanceof IntegerField) {
            return buildField((IntegerField) annotation);
        }
        throw new IllegalStateException("Cannot build integer field as configuration is for " + annotation.getClass());
    }

    @Override
    public Annotation findValidAnnotation(java.lang.reflect.Field f) {
        return AnnotationUtils.findAnnotation(f, IntegerField.class);
    }
}
