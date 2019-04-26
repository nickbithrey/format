package org.innovation.format.field.number.integer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.text.ParseException;

import org.innovation.format.field.number.integer.IntegerFieldFormat;
import org.junit.Test;

public class IntegerFieldFormattingTest {

    @Test
    public void testParsingLong() throws ParseException {
        IntegerFieldFormat format = new IntegerFieldFormat("#");
        Long value = 132l;
        Long parsedValue = format.read(value.toString().getBytes());
        assertThat(parsedValue).as("value parsed from %s for value %s", format.getClass().getSimpleName(), value)
                .isEqualTo(value);
    }

    @Test
    public void testParsingLongFailThrowsException() {
        IntegerFieldFormat format = new IntegerFieldFormat("abc");
        Long value = 132l;
        Throwable thrown = catchThrowable(() -> format.read(value.toString().getBytes()));
        assertThat(thrown).as("thrown exception when failing to parse field %s", value)
                .isInstanceOf(ParseException.class).hasMessage("Unparseable number: \"%s\"", value.toString());
    }

    @Test
    public void testWritingString() {
        IntegerFieldFormat format = new IntegerFieldFormat("#");
        Long value = 132l;
        byte[] writtenValue = format.write(value);
        assertThat(writtenValue).as("value written from %s for value %s", format.getClass().getSimpleName(), value)
                .isEqualTo(value.toString().getBytes());
    }

    @Test
    public void testWriting() {
        IntegerFieldFormat format = new IntegerFieldFormat("abc");
        Long value = 132l;
        String expectedValue = "abc" + value.toString();
        byte[] writtenValue = format.write(value);
        assertThat(writtenValue).as("value written from %s for value %s", format.getClass().getSimpleName(), value)
                .isEqualTo(expectedValue.getBytes());
    }
}
