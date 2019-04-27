package org.innovation.format.record.annotation;

import java.util.Set;

import org.innovation.format.field.FieldConfiguration;

public interface AccessorTypeRecordBuilder {

    Set<FieldConfiguration> build(Class<?> clazz);

}
