package org.innovation.format.field.number.decimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.text.ParseException;

import org.junit.Test;

public class DecimalFieldFormattingTest {

    @Test
    public void testParsingDecimal() throws ParseException {
        DecimalFieldFormat format = new DecimalFieldFormat("###.##");
        Double value = 132.32;
        double parsedValue = format.read(value.toString().getBytes(), Double.class);
        assertThat(parsedValue).as("value parsed from %s for value %s", format.getClass().getSimpleName(), value)
                .isEqualTo(value);
    }

    @Test
    public void testParsingLongFailThrowsException() {
        DecimalFieldFormat format = new DecimalFieldFormat("abc");
        Double value = 132.32;
        Throwable thrown = catchThrowable(() -> format.read(value.toString().getBytes(), Double.class));
        assertThat(thrown).as("thrown exception when failing to parse field %s", value)
                .isInstanceOf(ParseException.class).hasMessage("Unparseable number: \"%s\"", value.toString());
    }

    @Test
    public void testWritingDouble() {
        DecimalFieldFormat format = new DecimalFieldFormat("#.##");
        Double value = 132.32;
        byte[] writtenValue = format.write(value);
        assertThat(writtenValue).as("value written from %s for value %s", format.getClass().getSimpleName(), value)
                .isEqualTo(value.toString().getBytes());
    }

    @Test
    public void testWritingWithDefaultPrefixFormat() {
        DecimalFieldFormat format = new DecimalFieldFormat("abc");
        Double value = 132.32;
        String expectedValue = "abc132";
        byte[] writtenValue = format.write(value);
        assertThat(writtenValue).as("value written from %s for value %s", format.getClass().getSimpleName(), value)
                .isEqualTo(expectedValue.getBytes());
    }
}
