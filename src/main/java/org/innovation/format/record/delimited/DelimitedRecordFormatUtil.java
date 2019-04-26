package org.innovation.format.record.delimited;

import java.io.IOException;

import org.innovation.format.record.Record;
import org.innovation.format.record.RecordFormatBuilder;

public class DelimitedRecordFormatUtil implements RecordFormatBuilder<DelimitedRecordConfiguration> {

    private static final Class<DelimitedRecordConfiguration> BUILDER = DelimitedRecordConfiguration.class;

    @Override
    public Record readRecord(byte[] bytes, DelimitedRecordConfiguration config) throws IOException {
        return DelimitedRecordReadUtil.readRecord(bytes, config);
    }

    @Override
    public byte[] writeRecord(Record record, DelimitedRecordConfiguration config) throws IOException {
        return DelimitedRecordWriteUtil.writeRecord(record, config);
    }

    @Override
    public Class<DelimitedRecordConfiguration> validRecordConfigurationClass() {
        return BUILDER;
    }

}
