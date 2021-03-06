package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author soldier
 */
public class Attachment {

    private final String contentType;
    private final String disposition;
    private final String filename;
    private final CompressedBytes data;

    public Attachment(InputStream stream, String contentType, String disposition, String filename) throws IOException {
        this.contentType = contentType;
        this.disposition = disposition;
        this.filename = filename;
        this.data = new CompressedBytes(IOUtils.toByteArray(stream));
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getData() {
        return data.get();
    }

    public String getDisposition() {
        return disposition;
    }

    public String getFilename() {
        return filename;
    }

    public String getMime() {
        if (contentType.contains(";")) {
            return contentType.substring(0, contentType.indexOf(';')).trim();
        }
        return contentType;
    }
}
