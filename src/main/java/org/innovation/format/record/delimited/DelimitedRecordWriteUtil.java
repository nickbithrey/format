package org.innovation.format.record.delimited;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.record.Record;

class DelimitedRecordWriteUtil {

    private DelimitedRecordWriteUtil() {
    }

    static byte[] writeRecord(Record record, DelimitedRecordConfiguration configuration) throws IOException {
        int count = 0;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            for (FieldConfiguration fieldConfiguration : configuration.getFields().stream().sorted()
                    .collect(Collectors.toList())) {
                Field field = record.getFields().stream().filter(f -> f.getName().equals(fieldConfiguration.getName()))
                        .findFirst().orElseThrow(() -> new IllegalStateException(
                                String.format("cannot find field with name %s", fieldConfiguration.getName())));
                byte[] recordBytes = field.getValue();
                baos.write(recordBytes);

                if (++count != record.getFields().size()) {
                    baos.write(configuration.getDelimiter());
                }
            }
            return baos.toByteArray();
        }
    }

}
