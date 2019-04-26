package org.innovation.format.record;

import java.util.HashSet;
import java.util.Set;

import org.innovation.format.field.Field;

public class Record {

    private final Set<Field<? extends Object>> fields = new HashSet<>();

    public Set<Field<? extends Object>> getFields() {
        return fields;
    }

    public <T extends Object> boolean addField(Field<T> field) {
        return fields.add(field);
    }

}
