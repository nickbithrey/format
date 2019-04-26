package org.innovation.format.record.fixedwidth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.record.Record;

class FixedWidthRecordReadUtil {

    private FixedWidthRecordReadUtil() {
    }

    static Record readRecord(byte[] recordBytes, FixedWidthRecordConfiguration configuration) throws IOException {
        if (recordBytes.length == 0) {
            return new Record();
        }
        Record record = new Record();

        FixedWidthFieldConfiguration<? extends Field<?>> fixedWidthField = getNextField(configuration, 1);
        int count = 0;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            for (byte b : recordBytes) {
                baos.write(b);
                if (++count != fixedWidthField.getLength()
                        || configuration.getFields().size() == record.getFields().size() + 1) {
                    continue;
                }

                record.addField(getField(baos, fixedWidthField));
                baos.reset();
                count = 0;
                fixedWidthField = getNextField(configuration, record.getFields().size() + 1);
            }
            record.addField(getField(baos, fixedWidthField));
        }

        return record;
    }

    private static FixedWidthFieldConfiguration<? extends Field<?>> getNextField(
            FixedWidthRecordConfiguration configuration, int fieldNumber) {
        FieldConfiguration<? extends Field<?>> field = configuration.getFields().stream()
                .filter(f -> f.getNumber() == fieldNumber).findFirst()
                .orElseThrow(() -> new IllegalStateException("no configuration fields set up"));
        if (!FixedWidthFieldConfiguration.class.isAssignableFrom(field.getClass())) {
            throw new IllegalStateException(String.format(
                    "field %s not set up as fixed width field even though its a fixed width file", field.getName()));
        }
        return (FixedWidthFieldConfiguration<? extends Field<?>>) field;
    }

    private static Field<?> getField(ByteArrayOutputStream baos, FixedWidthFieldConfiguration<?> fieldConfiguration) {
        return readCurrentBytes(baos, fieldConfiguration);
    }

    private static Field<?> readCurrentBytes(ByteArrayOutputStream baos,
            FixedWidthFieldConfiguration<?> fieldConfiguration) {
        byte[] result = baos.toByteArray();

        Field<?> field = fieldConfiguration.buildField(fieldConfiguration.getName());
        field.setRawValue(result);

        return field;
    }

}
