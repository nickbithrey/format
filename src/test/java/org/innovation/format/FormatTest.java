package org.innovation.format;

import java.io.File;
import java.io.IOException;

import org.innovation.format.record.delimited.DelimitedRecordConfiguration;
import org.innovation.format.record.delimited.DelimitedRecordFormatUtil;
import org.junit.Test;

public class FormatTest {

    private Format buildFormat() {
        Format format = new Format();
        format.addBuilder(new DelimitedRecordFormatUtil());
        return format;
    }

    @Test
    public void testReadFile() throws IOException {
        buildFormat().readFile(new File(""), new DelimitedRecordConfiguration(",".getBytes()));
    }

}
