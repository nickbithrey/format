package org.innovation.format.record.delimited;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.field.FieldFormatUtil;
import org.innovation.format.field.date.DateFieldConfiguration;
import org.innovation.format.field.number.decimal.DecimalFieldConfiguration;
import org.innovation.format.field.number.integer.IntegerFieldConfiguration;
import org.innovation.format.field.string.StringFieldConfiguration;
import org.innovation.format.field.string.StringFieldObj;
import org.innovation.format.record.Record;
import org.junit.Test;
import org.springframework.util.StringUtils;

public class DelimitedRecordFormatUtilTest {

    private final DelimitedRecordFormatUtil formatUtil = new DelimitedRecordFormatUtil();

    @Test
    public void testParseRecord() throws IOException {
        DelimitedRecordConfiguration config = new DelimitedRecordConfiguration(",".getBytes());

        Set<FieldConfiguration<? extends Field<?>>> fields = new HashSet<>();
        fields.add(new StringFieldConfiguration(1, "field1"));
        fields.add(new StringFieldConfiguration(2, "field2"));
        fields.add(new StringFieldConfiguration(3, "field3"));
        config.setFields(fields);

        String contents = "hello,my,dear";
        Record record = formatUtil.readRecord(contents.getBytes(), config);

        assertThat(record.getFields()).as("parsed fields from %s using configuration %s", contents, config)
                .hasSize(fields.size());
        String[] splitContents = StringUtils.commaDelimitedListToStringArray(contents);
        List<String> formattedFields = record.getFields().stream().map(Field::getRawValue).map(String::new)
                .collect(Collectors.toList());
        assertThat(formattedFields).as("formatted fields").containsExactlyInAnyOrder(splitContents);
    }

    @Test
    public void testParseWithLargeDelimiter() throws IOException {
        DelimitedRecordConfiguration config = new DelimitedRecordConfiguration(",65".getBytes());

        Set<FieldConfiguration<? extends Field<?>>> fields = new HashSet<>();
        fields.add(new StringFieldConfiguration(1, "field1"));
        fields.add(new StringFieldConfiguration(2, "field2"));
        fields.add(new StringFieldConfiguration(3, "field3"));
        config.setFields(fields);

        String contents = "hello,65my,65dear";
        Record record = formatUtil.readRecord(contents.getBytes(), config);

        assertThat(record.getFields()).as("parsed fields from %s using configuration %s", contents, config)
                .hasSize(fields.size());
        List<String> formattedFields = record.getFields().stream().map(Field::getRawValue).map(String::new)
                .collect(Collectors.toList());
        assertThat(formattedFields).as("formatted fields").containsExactlyInAnyOrder("hello", "my", "dear");
    }

    @Test
    public void testParseWithMultipleFieldTypes() throws IOException {
        DelimitedRecordConfiguration config = new DelimitedRecordConfiguration(",".getBytes());

        Set<FieldConfiguration<? extends Field<?>>> fields = new HashSet<>();
        fields.add(new StringFieldConfiguration(1, "field1"));
        IntegerFieldConfiguration integerFieldConfiguration = new IntegerFieldConfiguration(2, "field2");
        integerFieldConfiguration.setFormat("#");
        fields.add(integerFieldConfiguration);
        DecimalFieldConfiguration decimalFieldConfiguration = new DecimalFieldConfiguration(3, "field3");
        decimalFieldConfiguration.setFormat("#.##");
        fields.add(decimalFieldConfiguration);
        DateFieldConfiguration dateFieldConfiguration = new DateFieldConfiguration(4, "field4");
        dateFieldConfiguration.setFormat("dd-MM-yy");
        fields.add(dateFieldConfiguration);
        config.setFields(fields);

        String contents = "hello,312,443.23,12-01-17";
        Record record = formatUtil.readRecord(contents.getBytes(), config);

        assertThat(record.getFields()).as("parsed fields from %s using configuration %s", contents, config)
                .hasSize(fields.size());
        List<Object> formattedFields = record.getFields().stream().map(t -> {
            try {
                return FieldFormatUtil.readField(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        assertThat(formattedFields).as("formatted fields").containsExactlyInAnyOrder("hello", 312l, 443.23,
                Date.from(LocalDate.of(2017, 1, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Test
    public void testWriteRecord() throws IOException {
        DelimitedRecordConfiguration config = new DelimitedRecordConfiguration(",".getBytes());

        Set<FieldConfiguration<? extends Field<?>>> fields = new HashSet<>();
        fields.add(new StringFieldConfiguration(1, "field1"));
        fields.add(new StringFieldConfiguration(2, "field2"));
        fields.add(new StringFieldConfiguration(3, "field3"));
        config.setFields(fields);

        Record record = new Record();
        record.addField(buildStringField("field1", "hello".getBytes()));
        record.addField(buildStringField("field2", "my".getBytes()));
        record.addField(buildStringField("field3", "dear".getBytes()));

        String contents = "hello,my,dear";
        byte[] recordBytes = formatUtil.writeRecord(record, config);

        assertThat(recordBytes).as("written fields from %s using configuration %s", record.getFields(), config)
                .isEqualTo(contents.getBytes());
    }

    @Test
    public void testReadAndWriteGetsSameResult() throws IOException {
        DelimitedRecordConfiguration config = new DelimitedRecordConfiguration(",".getBytes());

        Set<FieldConfiguration<? extends Field<?>>> fields = new HashSet<>();
        fields.add(new StringFieldConfiguration(1, "field1"));
        fields.add(new StringFieldConfiguration(2, "field2"));
        fields.add(new StringFieldConfiguration(3, "field3"));
        config.setFields(fields);

        String contents = "hello,my,dear";
        Record record = DelimitedRecordReadUtil.readRecord(contents.getBytes(), config);
        byte[] writeRecord = DelimitedRecordWriteUtil.writeRecord(record, config);

        assertThat(writeRecord).as("resulting written record").isEqualTo(contents.getBytes());
    }

    private StringFieldObj buildStringField(String name, byte[] rawValue) {
        StringFieldObj field = new StringFieldObj(name);
        field.setRawValue(rawValue);
        return field;
    }

}
