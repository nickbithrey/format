package org.innovation.format.record.delimited;

import org.innovation.format.record.RecordConfiguration;

public class DelimitedRecordConfiguration extends RecordConfiguration {

    private final byte[] delimiter;

    public DelimitedRecordConfiguration(byte[] delimiter) {
        super();
        this.delimiter = delimiter;
    }

    public byte[] getDelimiter() {
        return delimiter;
    }

}
