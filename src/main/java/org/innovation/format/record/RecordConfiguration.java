package org.innovation.format.record;

import java.util.HashSet;
import java.util.Set;

import org.innovation.format.field.FieldConfiguration;

public class RecordConfiguration {

    private Set<FieldConfiguration> fields = new HashSet<>();

    public Set<FieldConfiguration> getFields() {
        return fields;
    }

    public void setFields(Set<FieldConfiguration> fields) {
        this.fields = fields;
    }

}
