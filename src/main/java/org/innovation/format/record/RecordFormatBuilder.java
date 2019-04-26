package org.innovation.format.record;

import java.io.IOException;

/**
 * processes the records based on their formats for this specific format builder
 *
 * @author nick.bithrey
 *
 * @param <T>
 *            extends {@link RecordConfiguration}
 */
public interface RecordFormatBuilder<T extends RecordConfiguration> {

    Record readRecord(byte[] bytes, T config) throws IOException;

    byte[] writeRecord(Record record, T config) throws IOException;

    Class<T> validRecordConfigurationClass();

    default boolean isValidRecordConfiguration(RecordConfiguration configuration) {
        return configuration != null && configuration.getClass().isAssignableFrom(validRecordConfigurationClass());
    }

}
