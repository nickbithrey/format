package org.innovation.format.record.fixedwidth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.innovation.format.field.BaseField;
import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.field.date.DateFieldConfiguration;
import org.innovation.format.field.date.DateFieldFormat;
import org.innovation.format.field.number.decimal.DecimalFieldConfiguration;
import org.innovation.format.field.number.decimal.DecimalFieldFormat;
import org.innovation.format.field.number.integer.IntegerFieldConfiguration;
import org.innovation.format.field.number.integer.IntegerFieldFormat;
import org.innovation.format.field.string.StringFieldConfiguration;
import org.innovation.format.field.string.StringFieldFormat;
import org.innovation.format.record.Record;
import org.junit.Test;

public class FixedWidthRecordFormatUtilTest {

    private final FixedWidthRecordFormatUtil formatUtil = new FixedWidthRecordFormatUtil();

    @Test
    public void testParseRecord() throws IOException {
        FixedWidthRecordConfiguration config = new FixedWidthRecordConfiguration();

        Set<FieldConfiguration> fields = new HashSet<>();
        fields.add(buildField(5, new StringFieldConfiguration(1, "field1")));
        fields.add(buildField(2, new StringFieldConfiguration(2, "field2")));
        fields.add(buildField(3, new StringFieldConfiguration(3, "field3")));
        config.setFields(fields);

        String contents = "hellomydear";
        Record record = formatUtil.readRecord(contents.getBytes(), config);

        assertThat(record.getFields()).as("parsed fields from %s using configuration %s", contents, config)
                .hasSize(fields.size());
        List<String> formattedFields = record.getFields().stream().map(Field::getValue).map(String::new)
                .collect(Collectors.toList());
        assertThat(formattedFields).as("formatted fields").containsExactlyInAnyOrder("hello", "my", "dear");
    }

    @Test
    public void testWriteRecord() throws IOException {
        FixedWidthRecordConfiguration config = new FixedWidthRecordConfiguration();

        Set<FieldConfiguration> fields = new HashSet<>();
        fields.add(buildField(5, new StringFieldConfiguration(1, "field1")));
        fields.add(buildField(2, new StringFieldConfiguration(2, "field2")));
        fields.add(buildField(3, new StringFieldConfiguration(3, "field3")));
        config.setFields(fields);

        Record record = new Record();
        record.addField(buildStringField("field1", "hello".getBytes()));
        record.addField(buildStringField("field2", "my".getBytes()));
        record.addField(buildStringField("field3", "dear".getBytes()));

        String contents = "hellomydear";
        byte[] recordBytes = formatUtil.writeRecord(record, config);

        assertThat(recordBytes).as("written fields from %s using configuration %s", record.getFields(), config)
                .isEqualTo(contents.getBytes());
    }

    @Test
    public void testParseWithMultipleFieldTypes() throws IOException {
        FixedWidthRecordConfiguration config = new FixedWidthRecordConfiguration();

        Set<FieldConfiguration> fields = new HashSet<>();
        fields.add(buildField(5, new StringFieldConfiguration(1, "field1")));
        IntegerFieldConfiguration integerFieldConfiguration = new IntegerFieldConfiguration(2, "field2");
        integerFieldConfiguration.setFormat("#");
        fields.add(buildField(3, integerFieldConfiguration));
        DecimalFieldConfiguration decimalFieldConfiguration = new DecimalFieldConfiguration(3, "field3");
        decimalFieldConfiguration.setFormat("#.##");
        fields.add(buildField(6, decimalFieldConfiguration));
        DateFieldConfiguration dateFieldConfiguration = new DateFieldConfiguration(4, "field4");
        dateFieldConfiguration.setFormat("dd-MM-yy");
        fields.add(buildField(8, dateFieldConfiguration));
        config.setFields(fields);

        String contents = "hello312443.2312-01-17";
        Record record = formatUtil.readRecord(contents.getBytes(), config);

        assertThat(record.getFields()).as("parsed fields from %s using configuration %s", contents, config)
                .hasSize(fields.size());
        assertThat(record.getFields().stream().map(f -> FieldAndType.of(f.getValue(), f.getFormatter().getClass()))
                .collect(Collectors.toList())).extracting(FieldAndType::getField, FieldAndType::getType)
                        .as("formatted fields").containsExactlyInAnyOrder(tuple("hello", StringFieldFormat.class),
                                tuple("312", IntegerFieldFormat.class), tuple("443.23", DecimalFieldFormat.class),
                                tuple("12-01-17", DateFieldFormat.class));
    }

    @Test
    public void testReadAndWriteGivesSameResult() throws IOException {
        FixedWidthRecordConfiguration config = new FixedWidthRecordConfiguration();

        Set<FieldConfiguration> fields = new HashSet<>();
        fields.add(buildField(5, new StringFieldConfiguration(1, "field1")));
        fields.add(buildField(2, new StringFieldConfiguration(2, "field2")));
        fields.add(buildField(3, new StringFieldConfiguration(3, "field3")));
        config.setFields(fields);

        String contents = "hellomydear";
        Record record = FixedWidthRecordReadUtil.readRecord(contents.getBytes(), config);
        byte[] recordBytes = FixedWidthRecordWriteUtil.writeRecord(record, config);

        assertThat(recordBytes).as("written fields from %s using configuration %s", record.getFields(), config)
                .isEqualTo(contents.getBytes());
    }

    private static class FieldAndType {

        private final byte[] field;

        private final Class<?> type;

        public static FieldAndType of(byte[] field, Class<?> type) {
            return new FieldAndType(field, type);
        }

        private FieldAndType(byte[] field, Class<?> type) {
            super();
            this.field = field;
            this.type = type;
        }

        public String getField() {
            return new String(field);
        }

        public Class<?> getType() {
            return type;
        }

    }

    private BaseField buildStringField(String name, byte[] rawValue) {
        BaseField field = new BaseField(name, new StringFieldFormat());
        field.setValue(rawValue);
        return field;
    }

    private FixedWidthFieldConfiguration buildField(long length, FieldConfiguration config) {
        return new FixedWidthFieldConfiguration(length, config);
    }

}
