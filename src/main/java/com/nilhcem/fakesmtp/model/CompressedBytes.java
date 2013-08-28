package com.nilhcem.fakesmtp.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author soldier
 */
public class CompressedBytes {

    private final byte[] data;

    public CompressedBytes(byte[] d) {
        try {
            data = compress(d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] get() {
        try {
            return decompress(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] decompress(byte[] inputData) throws IOException {
        InputStream is = new GZIPInputStream(new ByteArrayInputStream(inputData));
        ByteArrayOutputStream os = new ByteArrayOutputStream(inputData.length);
        IOUtils.copy(is, os);
        return os.toByteArray();
    }

    private static byte[] compress(byte[] inputData) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream(inputData.length);
        try (OutputStream os = new GZIPOutputStream(buf)) {
            os.write(inputData);
            os.flush();
        }
        return buf.toByteArray();
    }
}
