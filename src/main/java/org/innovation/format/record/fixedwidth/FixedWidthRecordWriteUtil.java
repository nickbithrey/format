package org.innovation.format.record.fixedwidth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.field.FieldFormatUtil;
import org.innovation.format.record.Record;

class FixedWidthRecordWriteUtil {

    private FixedWidthRecordWriteUtil() {
    }

    static byte[] writeRecord(Record record, FixedWidthRecordConfiguration configuration) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            for (FieldConfiguration<?> fieldConfiguration : configuration.getFields().stream().sorted()
                    .collect(Collectors.toList())) {
                Field<?> field = record.getFields().stream()
                        .filter(f -> f.getName().equals(fieldConfiguration.getName())).findFirst()
                        .orElseThrow(() -> new IllegalStateException(
                                String.format("cannot find field with name %s", fieldConfiguration.getName())));
                byte[] recordBytes = FieldFormatUtil.writeField(field);
                baos.write(recordBytes);
            }
            return baos.toByteArray();
        }
    }

}
