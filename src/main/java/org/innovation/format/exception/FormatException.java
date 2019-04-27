package org.innovation.format.exception;

import org.innovation.format.record.RecordConfiguration;

/**
 * exception thrown for when formatting a record fails
 *
 * @author nick.bithrey
 *
 */
public class FormatException extends RuntimeException {

    private static final long serialVersionUID = -4214192601927683044L;

    public FormatException(long recordNumber, RecordConfiguration recordConfiguration, String message) {
        super(formatMessage(recordNumber, recordConfiguration, message));
    }

    public FormatException(long recordNumber, RecordConfiguration recordConfiguration, Throwable throwable) {
        super(formatMessage(recordNumber, recordConfiguration, throwable.getMessage()), throwable);
    }

    private static String formatMessage(long recordNumber, RecordConfiguration recordConfiguration, String message) {
        return String.format("Error formatting record %d with configuration %s due to %s", recordNumber,
                recordConfiguration, message);
    }

}
