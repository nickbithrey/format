package org.innovation.format.field.string;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringFieldFormattingTest {

    @Test
    public void testParsingString() {
        StringFieldFormat format = new StringFieldFormat();
        String value = "strValue";
        String parsedValue = format.read(value.getBytes(), String.class);
        assertThat(parsedValue).as("value parsed from %s for value %s", format.getClass().getSimpleName(), value)
                .isEqualTo(value);
    }

    @Test
    public void testWritingString() {
        StringFieldFormat format = new StringFieldFormat();
        String value = "strValue";
        byte[] writtenValue = format.write(value);
        assertThat(writtenValue).as("value written from %s for value %s", format.getClass().getSimpleName(), value)
                .isEqualTo(value.getBytes());
    }

}
