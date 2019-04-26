package org.innovation.format.examples;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Files;
import org.innovation.format.Format;
import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.field.string.StringFieldConfiguration;
import org.innovation.format.field.string.StringFieldObj;
import org.innovation.format.record.Record;
import org.innovation.format.record.delimited.DelimitedRecordConfiguration;
import org.innovation.format.record.delimited.DelimitedRecordFormatUtil;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class DelimitedFileExampleTest {

    private static final String TSV_FILE = "/delimited/tabseparated.tsv";

    private static final String CSV_FILE = "delimited/commaseparated.csv";

    @Test
    public void testReadTabSeparatedFile() throws IOException {
        DelimitedRecordConfiguration config = buildRecordConfiguration("\t");

        List<Record> records = buildFormat().readResource(new ClassPathResource(TSV_FILE), config);

        assertThat(records).as("read records").hasSize(3);
    }

    private Format buildFormat() {
        Format format = new Format();
        format.addBuilder(new DelimitedRecordFormatUtil());
        return format;
    }

    @Test
    public void testWriteTabSeparatedFile() throws IOException {
        DelimitedRecordConfiguration config = buildRecordConfiguration("\t");

        List<Record> records = new ArrayList<>();
        records.add(createRecord("tab", "separated", "file"));
        records.add(createRecord("written", "with", "extra"));
        records.add(createRecord("lines", "in", "file"));

        File file = Files.newTemporaryFile();
        buildFormat().writeToFile(file, records, config);

        List<String> lines = new ArrayList<>();
        try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader);) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        assertThat(lines).as("written records").containsExactly("tab\tseparated\tfile", "written\twith\textra",
                "lines\tin\tfile");
    }

    @Test
    public void testReadCommaSeparatedFile() throws IOException {
        DelimitedRecordConfiguration config = buildRecordConfiguration(",");

        List<Record> records = buildFormat().readResource(new ClassPathResource(CSV_FILE), config);

        assertThat(records).as("read records").hasSize(3);
    }

    @Test
    public void testWriteCommaSeparatedFile() throws IOException {
        DelimitedRecordConfiguration config = buildRecordConfiguration(",");

        List<Record> records = new ArrayList<>();
        records.add(createRecord("tab", "separated", "file"));
        records.add(createRecord("written", "with", "extra"));
        records.add(createRecord("lines", "in", "file"));

        File file = Files.newTemporaryFile();
        buildFormat().writeToFile(file, records, config);

        List<String> lines = new ArrayList<>();
        try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader);) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        assertThat(lines).as("written records").containsExactly("tab,separated,file", "written,with,extra",
                "lines,in,file");
    }

    private Record createRecord(String field1Val, String field2Val, String field3Val) {
        Record record = new Record();
        StringFieldObj field1 = new StringFieldObj("FIELD1");
        field1.setRawValue(field1Val.getBytes());
        record.addField(field1);
        StringFieldObj field2 = new StringFieldObj("FIELD2");
        field2.setRawValue(field2Val.getBytes());
        record.addField(field2);
        StringFieldObj field3 = new StringFieldObj("FIELD3");
        field3.setRawValue(field3Val.getBytes());
        record.addField(field3);
        return record;
    }

    private DelimitedRecordConfiguration buildRecordConfiguration(String delimiter) {
        DelimitedRecordConfiguration config = new DelimitedRecordConfiguration(delimiter.getBytes());
        FieldConfiguration<StringFieldObj> field1 = new StringFieldConfiguration(1, "FIELD1");
        FieldConfiguration<StringFieldObj> field2 = new StringFieldConfiguration(2, "FIELD2");
        FieldConfiguration<StringFieldObj> field3 = new StringFieldConfiguration(3, "FIELD3");
        Set<FieldConfiguration<? extends Field<?>>> fields = new HashSet<>();
        fields.add(field1);
        fields.add(field2);
        fields.add(field3);
        config.setFields(fields);
        return config;
    }

}
