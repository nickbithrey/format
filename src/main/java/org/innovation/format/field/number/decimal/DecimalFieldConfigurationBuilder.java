package org.innovation.format.field.number.decimal;

import java.lang.annotation.Annotation;

import org.innovation.format.field.FieldConfigurationBuilder;
import org.springframework.core.annotation.AnnotationUtils;

public class DecimalFieldConfigurationBuilder implements FieldConfigurationBuilder<DecimalFieldConfiguration> {

    private static DecimalFieldConfiguration buildField(DecimalField decimalField) {
        DecimalFieldConfiguration configuration = new DecimalFieldConfiguration(decimalField.number(),
                decimalField.name());
        configuration.setFormat(decimalField.format());
        return configuration;
    }

    @Override
    public DecimalFieldConfiguration build(Annotation annotation) {
        if (annotation instanceof DecimalField) {
            return buildField((DecimalField) annotation);
        }
        throw new IllegalStateException("Cannot build decimal field as configuration is for " + annotation.getClass());
    }

    @Override
    public Annotation findValidAnnotation(java.lang.reflect.Field f) {
        return AnnotationUtils.findAnnotation(f, DecimalField.class);
    }
}
