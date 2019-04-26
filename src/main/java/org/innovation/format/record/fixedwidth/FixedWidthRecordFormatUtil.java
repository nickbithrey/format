package org.innovation.format.record.fixedwidth;

import java.io.IOException;

import org.innovation.format.record.Record;
import org.innovation.format.record.RecordFormatBuilder;

public class FixedWidthRecordFormatUtil implements RecordFormatBuilder<FixedWidthRecordConfiguration> {

    private static final Class<FixedWidthRecordConfiguration> BUILDER = FixedWidthRecordConfiguration.class;

    @Override
    public Record readRecord(byte[] byteRecord, FixedWidthRecordConfiguration config) throws IOException {
        return FixedWidthRecordReadUtil.readRecord(byteRecord, config);
    }

    @Override
    public byte[] writeRecord(Record record, FixedWidthRecordConfiguration config) throws IOException {
        return FixedWidthRecordWriteUtil.writeRecord(record, config);
    }

    @Override
    public Class<FixedWidthRecordConfiguration> validRecordConfigurationClass() {
        return BUILDER;
    }
}
