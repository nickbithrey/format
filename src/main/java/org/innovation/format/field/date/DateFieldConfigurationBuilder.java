package org.innovation.format.field.date;

import java.lang.annotation.Annotation;

import org.innovation.format.field.FieldConfigurationBuilder;
import org.springframework.core.annotation.AnnotationUtils;

public class DateFieldConfigurationBuilder implements FieldConfigurationBuilder<DateFieldConfiguration> {

    public static DateFieldConfiguration buildField(DateField dateField) {
        DateFieldConfiguration configuration = new DateFieldConfiguration(dateField.number(), dateField.name());
        configuration.setFormat(dateField.format());
        return configuration;
    }

    @Override
    public DateFieldConfiguration build(Annotation annotation) {
        if (annotation instanceof DateField) {
            return buildField((DateField) annotation);
        }
        throw new IllegalStateException("Cannot build date field as configuration is for " + annotation.getClass());
    }

    @Override
    public Annotation findValidAnnotation(java.lang.reflect.Field f) {
        return AnnotationUtils.findAnnotation(f, DateField.class);
    }
}
