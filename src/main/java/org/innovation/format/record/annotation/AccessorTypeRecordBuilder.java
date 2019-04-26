package org.innovation.format.record.annotation;

import java.util.Set;

import org.innovation.format.field.FieldConfiguration;

public interface AccessorTypeRecordBuilder {

    Set<FieldConfiguration<? extends org.innovation.format.field.Field<?>>> build(Class<?> clazz);

}
