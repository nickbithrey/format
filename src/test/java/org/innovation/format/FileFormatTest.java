package org.innovation.format;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.innovation.format.field.Field;
import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.field.date.DateFieldConfiguration;
import org.innovation.format.field.number.decimal.DecimalFieldConfiguration;
import org.innovation.format.field.string.StringFieldConfiguration;
import org.innovation.format.record.Record;
import org.innovation.format.record.delimited.DelimitedRecordConfiguration;
import org.innovation.format.record.delimited.DelimitedRecordFormatUtil;
import org.junit.Test;

public class FileFormatTest {

    @Test
    public void testDelimitedFile() throws IOException {
        StringBuilder contents = new StringBuilder();
        contents.append("test,file,12.32,14-JAN-2019\n");
        contents.append("test2,file2,12.32,14-JAN-2019\n");
        contents.append("test3,file3,13.32,15-JAN-2019\n");
        contents.append("test4,file4,14.32,16-JAN-2019\n");
        contents.append("test5,file5,15.32,17-JAN-2019\n");

        String fullContents = contents.toString();
        String[] recordsStr = StringUtils.split(fullContents, "\n");

        DelimitedRecordConfiguration config = new DelimitedRecordConfiguration(",".getBytes());
        Set<FieldConfiguration> fields = new HashSet<>();
        fields.add(new StringFieldConfiguration(1, "field1"));
        fields.add(new StringFieldConfiguration(2, "field2"));
        DecimalFieldConfiguration dfConfig = new DecimalFieldConfiguration(3, "field3");
        dfConfig.setFormat("#.##");
        fields.add(dfConfig);
        DateFieldConfiguration dateConfig = new DateFieldConfiguration(4, "field4");
        dateConfig.setFormat("dd-MMM-yyyy");
        fields.add(dateConfig);
        config.setFields(fields);

        List<Record> records = new ArrayList<>();

        for (String recordStr : recordsStr) {
            records.add(new DelimitedRecordFormatUtil().readRecord(recordStr.getBytes(), config));
        }

        List<Set<Field>> recordsFields = records.stream().map(Record::getFields).collect(Collectors.toList());
        recordsFields.stream().map(fs -> fs.stream().collect(Collectors.toMap(Field::getName, Field::getValue)))
                .collect(Collectors.toList());
        assertThat(recordsFields).as("read records").hasSize(5);
    }

}
