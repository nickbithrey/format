package org.innovation.format.record;

import java.util.HashSet;
import java.util.Set;

import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;

public class RecordConfiguration {

    private Set<FieldConfiguration<? extends Field<?>>> fields = new HashSet<>();

    public Set<FieldConfiguration<? extends Field<?>>> getFields() {
        return fields;
    }

    public void setFields(Set<FieldConfiguration<? extends Field<?>>> fields) {
        this.fields = fields;
    }

}
