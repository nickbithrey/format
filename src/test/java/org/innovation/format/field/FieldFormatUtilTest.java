package org.innovation.format.field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.Test;

public class FieldFormatUtilTest {

    @Test
    public void testReadAlreadyReadField() throws ParseException {
        @SuppressWarnings("unchecked")
        FieldFormat<String> fieldFormat = mock(FieldFormat.class);
        Field<String> field = buildField(false, fieldFormat);
        String formattedResult = field.getValue() + " formatted";
        when(fieldFormat.read(any())).thenReturn(formattedResult);
        String result = FieldFormatUtil.readField(buildField(true, fieldFormat));

        assertThat(result).as("read field {}").isEqualTo("value");
        verifyZeroInteractions(fieldFormat);
    }

    @Test
    public void testReadFieldForFirstTime() throws ParseException {
        @SuppressWarnings("unchecked")
        FieldFormat<String> fieldFormat = mock(FieldFormat.class);
        Field<String> field = buildField(false, fieldFormat);
        String formattedResult = field.getValue() + " formatted";
        when(fieldFormat.read(any())).thenReturn(formattedResult);
        String result = FieldFormatUtil.readField(field);

        assertThat(result).as("read field {}").isEqualTo(formattedResult);
        verify(fieldFormat).read(field.getRawValue());
    }

    @Test
    public void testWriteField() {
        @SuppressWarnings("unchecked")
        FieldFormat<String> fieldFormat = mock(FieldFormat.class);
        Field<String> field = buildField(true, fieldFormat);
        when(fieldFormat.write(anyString())).thenReturn(field.getRawValue());
        byte[] result = FieldFormatUtil.writeField(field);

        assertThat(result).as("written field {}").isEqualTo(field.getRawValue());
        verify(fieldFormat).write(field.getValue());
    }

    @Test
    public void testWriteFieldNotReadAlready() {
        @SuppressWarnings("unchecked")
        FieldFormat<String> fieldFormat = mock(FieldFormat.class);
        Field<String> field = buildField(false, fieldFormat);
        when(fieldFormat.write(anyString())).thenReturn(field.getRawValue());
        byte[] result = FieldFormatUtil.writeField(field);

        assertThat(result).as("written field {}").isEqualTo(field.getRawValue());
        verifyZeroInteractions(fieldFormat);
    }

    private Field<String> buildField(boolean read, FieldFormat<String> fieldFormat) {
        return new TestField(read, fieldFormat);
    }

    private static class TestField implements Field<String> {

        private final boolean read;

        private final FieldFormat<String> fieldFormat;

        public TestField(boolean read, FieldFormat<String> fieldFormat) {
            this.read = read;
            this.fieldFormat = fieldFormat;
        }

        @Override
        public String getName() {
            return "name";
        }

        @Override
        public String getValue() {
            return "value";
        }

        @Override
        public void setValue(String value) {
        }

        @Override
        public boolean isRead() {
            return read;
        }

        @Override
        public byte[] getRawValue() {
            return "rawValue".getBytes();
        }

        @Override
        public void setRawValue(byte[] value) {
        }

        @Override
        public FieldFormat<String> getFormatter() {
            return fieldFormat;
        }

    }

}
