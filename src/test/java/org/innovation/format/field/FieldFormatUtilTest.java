package org.innovation.format.field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.Test;

public class FieldFormatUtilTest {

    @Test
    public void testReadField() throws ParseException {
        FieldFormat fieldFormat = mock(FieldFormat.class);
        Field field = buildField(fieldFormat);
        String formattedResult = field.getValue() + " formatted";
        when(fieldFormat.read(any(), any())).thenReturn(formattedResult);
        String result = FieldFormatUtil.readField(field, String.class);

        assertThat(result).as("read field %s", field).isEqualTo(formattedResult);
        verify(fieldFormat).read(field.getValue(), String.class);
    }

    @Test
    public void testWriteField() {
        FieldFormat fieldFormat = mock(FieldFormat.class);
        Field field = buildField(fieldFormat);
        String value = "value";
        when(fieldFormat.write(anyString())).thenReturn(value.getBytes());
        FieldFormatUtil.writeField(field, value);

        assertThat(value.getBytes()).as("written field %s", field).isEqualTo(field.getValue());
        verify(fieldFormat).write(value);
    }

    private Field buildField(FieldFormat fieldFormat) {
        return new BaseField("name", fieldFormat);
    }

}
