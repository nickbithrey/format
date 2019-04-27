package org.innovation.format.record;

import java.util.HashSet;
import java.util.Set;

import org.innovation.format.field.Field;

public class Record {

    private final Set<Field> fields = new HashSet<>();

    public Set<Field> getFields() {
        return fields;
    }

    public boolean addField(Field field) {
        return fields.add(field);
    }

}
