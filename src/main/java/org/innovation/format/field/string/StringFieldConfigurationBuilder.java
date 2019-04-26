package org.innovation.format.field.string;

import java.lang.annotation.Annotation;

import org.innovation.format.field.FieldConfigurationBuilder;
import org.springframework.core.annotation.AnnotationUtils;

public class StringFieldConfigurationBuilder implements FieldConfigurationBuilder<StringFieldConfiguration> {

    private static StringFieldConfiguration buildField(StringField stringField) {
        return new StringFieldConfiguration(stringField.number(), stringField.name());
    }

    @Override
    public StringFieldConfiguration build(Annotation annotation) {
        if (annotation instanceof StringField) {
            return buildField((StringField) annotation);
        }
        throw new IllegalStateException("Cannot build string field as configuration is for " + annotation.getClass());
    }

    @Override
    public Annotation findValidAnnotation(java.lang.reflect.Field f) {
        return AnnotationUtils.findAnnotation(f, StringField.class);
    }
}
