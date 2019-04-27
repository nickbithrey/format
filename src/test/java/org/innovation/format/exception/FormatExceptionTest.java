package org.innovation.format.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.innovation.format.record.RecordConfiguration;
import org.junit.Test;

public class FormatExceptionTest {

    @Test
    public void testNoCause() {
        int recordNumber = 1;
        RecordConfiguration recordConfiguration = new RecordConfiguration();
        String message = "message";
        FormatException exception = new FormatException(recordNumber, recordConfiguration, message);
        assertThat(exception).as("format exception")
                .hasMessage(String.format("Error formatting record %d with configuration %s due to %s", recordNumber,
                        recordConfiguration, message))
                .hasNoCause();
    }

    @Test
    public void testWithCause() {
        int recordNumber = 1;
        RecordConfiguration recordConfiguration = new RecordConfiguration();
        String message = "message";
        Throwable throwable = new Throwable(message);
        FormatException exception = new FormatException(recordNumber, recordConfiguration, throwable);
        assertThat(exception).as("format exception")
                .hasMessage(String.format("Error formatting record %d with configuration %s due to %s", recordNumber,
                        recordConfiguration, message))
                .hasCause(throwable);
    }

}
