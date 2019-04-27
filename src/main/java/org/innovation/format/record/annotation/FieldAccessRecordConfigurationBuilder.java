package org.innovation.format.record.annotation;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.field.FieldConfigurationBuilder;
import org.innovation.format.field.date.DateFieldConfigurationBuilder;
import org.innovation.format.field.number.decimal.DecimalFieldConfigurationBuilder;
import org.innovation.format.field.number.integer.IntegerFieldConfigurationBuilder;
import org.innovation.format.field.string.StringFieldConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

public class FieldAccessRecordConfigurationBuilder implements AccessorTypeRecordBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldAccessRecordConfigurationBuilder.class);

    private final Set<FieldConfigurationBuilder<? extends FieldConfiguration>> fieldBuilders = new HashSet<>();

    public FieldAccessRecordConfigurationBuilder() {
        fieldBuilders.add(new DateFieldConfigurationBuilder());
        fieldBuilders.add(new IntegerFieldConfigurationBuilder());
        fieldBuilders.add(new DecimalFieldConfigurationBuilder());
        fieldBuilders.add(new StringFieldConfigurationBuilder());
        LOGGER.info("Built field builders. Number = {}", fieldBuilders.size());
    }

    @Override
    public Set<FieldConfiguration> build(Class<?> clazz) {
        Set<FieldConfiguration> fields = new HashSet<>();
        FormatFieldCallback fieldCallback = new FormatFieldCallback(fields);
        ReflectionUtils.doWithFields(clazz, fieldCallback, new FormatFieldFilter());
        return fields;
    }

    private class FormatFieldCallback implements FieldCallback {

        private final Set<FieldConfiguration> fieldConfiguration;

        public FormatFieldCallback(Set<FieldConfiguration> fieldConfiguration) {
            super();
            this.fieldConfiguration = fieldConfiguration;
        }

        @Override
        public void doWith(Field field) throws IllegalAccessException {

            fieldConfiguration.add(fieldBuilders.stream()
                    .map(builder -> new AbstractMap.SimpleEntry<>(builder, builder.findValidAnnotation(field)))
                    .filter(entry -> entry.getValue() != null).findFirst()
                    .map(entry -> entry.getKey().build(entry.getValue()))
                    .orElseThrow(() -> new IllegalStateException("no valid annotation found for field")));
        }

    }

    private class FormatFieldFilter implements FieldFilter {

        @Override
        public boolean matches(Field field) {
            return fieldBuilders.stream().filter(builder -> builder.findValidAnnotation(field) != null).count() > 0;
        }

    }

}
