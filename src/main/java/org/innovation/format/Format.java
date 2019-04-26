package org.innovation.format;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.innovation.format.exception.FormatException;
import org.innovation.format.record.Record;
import org.innovation.format.record.RecordConfiguration;
import org.innovation.format.record.RecordFormatBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.google.common.io.Files;

public class Format {

    private static final Logger LOGGER = LoggerFactory.getLogger(Format.class);

    private final Collection<RecordFormatBuilder<?>> builders = new HashSet<>();

    public <T extends RecordConfiguration> List<Record> readFile(File file, T config) throws IOException {
        try (FileInputStream fis = Files.newInputStreamSupplier(file).getInput();
                BufferedInputStream bis = new BufferedInputStream(fis);) {
            return readInputStream(bis, config);
        }
    }

    public <T extends RecordConfiguration> List<Record> readResource(Resource resource, T config) throws IOException {
        try (InputStream is = resource.getInputStream(); BufferedInputStream bis = new BufferedInputStream(is);) {
            return readInputStream(bis, config);
        }
    }

    public <T extends RecordConfiguration> List<Record> readInputStream(InputStream is, T config) throws IOException {
        return readFromInputStream(is, config, System.lineSeparator().getBytes());
    }

    private <T extends RecordConfiguration> List<Record> readFromInputStream(InputStream is, T config,
            byte[] recordSeparator) throws IOException {
        List<Record> records = new ArrayList<>();

        RecordFormatBuilder<T> builder = findBuilder(config);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            int i = 0;
            AtomicLong rowNum = new AtomicLong(1l);
            byte[] prev = new byte[recordSeparator.length];
            while ((i = is.read()) != -1) {
                baos.write(i);

                byte[] curr = Arrays.copyOfRange(prev, 1, prev.length + 1);
                curr[curr.length - 1] = (byte) i;
                prev = curr;

                if (!Arrays.equals(curr, recordSeparator)) {
                    continue;
                }

                byte[] byteArray = baos.toByteArray();
                byte[] recordBytes = Arrays.copyOfRange(byteArray, 0, byteArray.length - recordSeparator.length);
                Record record = builder.readRecord(recordBytes, config);
                records.add(record);
                LOGGER.debug("Read record {} - {}", rowNum.getAndIncrement(), record);
                baos.reset();
            }
            records.add(builder.readRecord(baos.toByteArray(), config));
        }
        return records;
    }

    public <T extends RecordConfiguration> OutputStream writeToFile(File file, List<Record> records, T config)
            throws IOException {
        return writeToFile(file, records, config, false);
    }

    public <T extends RecordConfiguration> OutputStream writeToFile(File file, List<Record> records, T config,
            boolean append) throws IOException {
        try (FileOutputStream fos = Files.newOutputStreamSupplier(file, append).getOutput();
                BufferedOutputStream bos = new BufferedOutputStream(fos);) {
            return writeToOutputStream(bos, records, config);
        }
    }

    public <T extends RecordConfiguration> OutputStream writeToOutputStream(OutputStream os, List<Record> records,
            T config) {
        RecordFormatBuilder<T> builder = findBuilder(config);
        AtomicLong rowNum = new AtomicLong(1l);
        records.forEach(record -> {
            try {
                os.write(builder.writeRecord(record, config));
                os.write(System.lineSeparator().getBytes());
                LOGGER.debug("Written record {} - {}", rowNum.getAndIncrement(), record);
            } catch (IOException e) {
                throw new FormatException(rowNum.getAndIncrement(), config, e);
            }
        });
        return os;
    }

    @SuppressWarnings("unchecked")
    private <T extends RecordConfiguration> RecordFormatBuilder<T> findBuilder(T config) {
        Set<RecordFormatBuilder<?>> validRecordBuilders = builders.stream()
                .filter(builder -> builder.isValidRecordConfiguration(config)).collect(Collectors.toSet());
        if (validRecordBuilders.size() != 1) {
            throw new IllegalStateException("too many builders for configuration " + config);
        }
        return (RecordFormatBuilder<T>) validRecordBuilders.iterator().next();
    }

    public boolean addBuilder(RecordFormatBuilder<?> builders) {
        return this.builders.add(builders);
    }

}
