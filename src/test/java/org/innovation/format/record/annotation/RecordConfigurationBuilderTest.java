package org.innovation.format.record.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.innovation.format.field.date.DateField;
import org.innovation.format.field.number.decimal.DecimalField;
import org.innovation.format.field.number.integer.IntegerField;
import org.innovation.format.field.string.StringField;
import org.innovation.format.record.RecordConfiguration;
import org.innovation.format.record.RecordConfigurationBuilder;
import org.innovation.format.record.delimited.DelimitedRecord;
import org.innovation.format.record.delimited.DelimitedRecordConfiguration;
import org.junit.Test;

public class RecordConfigurationBuilderTest {

    @Test
    public void testBuildsCorrectConfiguration() {
        RecordConfigurationBuilder builder = new RecordConfigurationBuilder();
        RecordConfiguration recordConfiguration = builder.build(TestDelimitedRecord.class);

        assertThat(recordConfiguration).as("build record configuration")
                .isInstanceOf(DelimitedRecordConfiguration.class);
        assertThat(recordConfiguration.getFields()).as("build fields on record configuration").hasSize(4);
        assertThat(((DelimitedRecordConfiguration) recordConfiguration).getDelimiter())
                .as("delimiter on built delimited record configuration").isEqualTo("\t".getBytes());
    }

    @FormatRecord(accessorType = AccessorType.FIELD)
    @DelimitedRecord(delimiter = "\t")
    private static class TestDelimitedRecord {

        @StringField(number = 1, name = "STRFIELD")
        private String strField;

        @IntegerField(number = 2, name = "INTFIELD", format = "0")
        private Integer intField;

        @DecimalField(number = 3, name = "DOUBLEFIELD", format = "0.00")
        private Double doubleField;

        @DateField(number = 4, name = "DATEFIELD", format = "dd/MM/yy")
        private Date dateField;

    }

}
