package org.innovation.format.record;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.innovation.format.exception.InvalidFormatAnnotationException;
import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.record.annotation.AccessorType;
import org.innovation.format.record.annotation.AccessorTypeRecordBuilder;
import org.innovation.format.record.annotation.FieldAccessRecordConfigurationBuilder;
import org.innovation.format.record.annotation.FormatRecord;
import org.innovation.format.record.delimited.DelimitedRecordConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

public class RecordConfigurationBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordConfigurationBuilder.class);

    private final Map<AccessorType, AccessorTypeRecordBuilder> fieldBuilders = new EnumMap<>(AccessorType.class);

    private final Set<RecordConfigurationTypeBuilder<?, ?>> recordBuilders = new HashSet<>();

    public RecordConfigurationBuilder() {
        fieldBuilders.put(AccessorType.FIELD, new FieldAccessRecordConfigurationBuilder());
        recordBuilders.add(new DelimitedRecordConfigurationBuilder());
        LOGGER.info("{} field builders and {} record builders", fieldBuilders.size(), recordBuilders.size());
    }

    public RecordConfiguration build(Class<?> clazz) {
        FormatRecord formatRecord = AnnotationUtils.findAnnotation(clazz, FormatRecord.class);
        if (formatRecord == null) {
            throw new InvalidFormatAnnotationException(ElementType.TYPE, clazz.getSimpleName(),
                    Collections.singleton(FormatRecord.class));
        }
        AccessorType accessorType = formatRecord.accessorType();
        Set<FieldConfiguration> fields = fieldBuilders.get(accessorType).build(clazz);
        List<RecordBuilderWithAnnotation<?, ?>> buildersWithAnnotation = recordBuilders.stream()
                .map(builder -> new RecordBuilderWithAnnotation<>(builder,
                        Optional.ofNullable(builder.findBuilderAnnotation(clazz))))
                .filter(RecordBuilderWithAnnotation::hasAnnotation).collect(Collectors.toList());

        if (buildersWithAnnotation.size() != 1) {
            throw new InvalidFormatAnnotationException(ElementType.TYPE, clazz.getSimpleName(), recordBuilders.stream()
                    .map(RecordConfigurationTypeBuilder::getBuilderAnnotationClass).collect(Collectors.toSet()));
        }
        RecordBuilderWithAnnotation<?, ?> builderWithAnnotation = buildersWithAnnotation.get(0);
        Optional<Annotation> annotation = builderWithAnnotation.getAnnotation();
        if (!annotation.isPresent()) {
            throw new InvalidFormatAnnotationException(ElementType.TYPE, clazz.getSimpleName(), recordBuilders.stream()
                    .map(RecordConfigurationTypeBuilder::getBuilderAnnotationClass).collect(Collectors.toSet()));
        }
        return builderWithAnnotation.getBuilder().build(annotation.get(), fields);
    }

    private static class RecordBuilderWithAnnotation<T extends RecordConfiguration, V extends Annotation> {

        private final RecordConfigurationTypeBuilder<T, V> builder;

        private final Optional<Annotation> annotation;

        public RecordBuilderWithAnnotation(RecordConfigurationTypeBuilder<T, V> builder,
                Optional<Annotation> annotation) {
            super();
            this.builder = builder;
            this.annotation = annotation;
        }

        public RecordConfigurationTypeBuilder<T, V> getBuilder() {
            return builder;
        }

        public Optional<Annotation> getAnnotation() {
            return annotation;
        }

        public boolean hasAnnotation() {
            return annotation.isPresent();
        }

    }

}