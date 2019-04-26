package org.innovation.format.record.delimited;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.record.Record;

class DelimitedRecordReadUtil {

    private DelimitedRecordReadUtil() {
    }

    static Record readRecord(byte[] recordBytes, DelimitedRecordConfiguration configuration) throws IOException {
        Record record = new Record();

        byte[] delimiter = configuration.getDelimiter();
        byte[] prev = new byte[delimiter.length];
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            for (byte b : recordBytes) {
                baos.write(b);

                byte[] curr = Arrays.copyOfRange(prev, 1, prev.length + 1);
                curr[curr.length - 1] = b;
                prev = curr;

                if (!Arrays.equals(curr, delimiter)) {
                    continue;
                }

                record.addField(getField(baos, configuration, record.getFields().size() + 1, curr.length));
                baos.reset();
            }
            record.addField(getField(baos, configuration, record.getFields().size() + 1, 0));
        }

        return record;
    }

    private static Field<?> getField(ByteArrayOutputStream baos, DelimitedRecordConfiguration configuration,
            int currentParseNumber, int bytesToRemove) {
        FieldConfiguration<? extends Field<?>> fieldConfiguration = configuration.getFields().stream()
                .filter(f -> isFieldNumber(currentParseNumber, f)).findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("too many fields in record (%s) compared to configuration (%s)",
                                currentParseNumber, configuration.getFields().size())));
        return readCurrentBytes(baos, bytesToRemove, fieldConfiguration);
    }

    private static boolean isFieldNumber(int currentParseNumber, FieldConfiguration<? extends Field<?>> f) {
        return f.getNumber() == currentParseNumber;
    }

    private static Field<?> readCurrentBytes(ByteArrayOutputStream baos, int delimiter,
            FieldConfiguration<? extends Field<?>> fieldConfiguration) {
        byte[] bytes = baos.toByteArray();
        byte[] result = Arrays.copyOfRange(bytes, 0, bytes.length - delimiter);

        Field<?> field = fieldConfiguration.buildField(fieldConfiguration.getName());
        field.setRawValue(result);

        return field;
    }
}
