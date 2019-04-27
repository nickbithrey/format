package org.innovation.format.record.delimited;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.innovation.format.field.FieldConfiguration;
import org.innovation.format.record.RecordConfigurationTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class DelimitedRecordConfigurationBuilder
        implements RecordConfigurationTypeBuilder<DelimitedRecordConfiguration, DelimitedRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DelimitedRecordConfigurationBuilder.class);

    private static final Class<DelimitedRecord> BUILDER_ANNOTATION = DelimitedRecord.class;

    @Override
    public DelimitedRecordConfiguration build(Annotation annotation, Set<FieldConfiguration> fields) {
        if (annotation instanceof DelimitedRecord) {
            return buildDelimitedRecord((DelimitedRecord) annotation, fields);
        }
        throw new IllegalStateException("record not a delimited record");
    }

    private DelimitedRecordConfiguration buildDelimitedRecord(DelimitedRecord annotation,
            Set<FieldConfiguration> fields) {
        byte[] delimiterBytes = annotation.delimiterBytes();
        if (delimiterBytes.length == 0) {
            delimiterBytes = annotation.delimiter().getBytes();
        }
        Assert.isTrue(delimiterBytes.length != 0, "No Delimiter defined for delimited record");
        String delimiterString = new String(delimiterBytes);
        LOGGER.info("building delimited record with delimiter {} and {} fields", delimiterString, fields.size());
        DelimitedRecordConfiguration delimitedRecordConfiguration = new DelimitedRecordConfiguration(delimiterBytes);
        delimitedRecordConfiguration.setFields(fields);
        return delimitedRecordConfiguration;
    }

    @Override
    public Class<DelimitedRecord> getBuilderAnnotationClass() {
        return BUILDER_ANNOTATION;
    }
}
