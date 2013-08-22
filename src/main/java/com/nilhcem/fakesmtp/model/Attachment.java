/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private final byte[] data;

    public Attachment(InputStream stream, String contentType) throws IOException {
        this.contentType = contentType;
        this.data = IOUtils.toByteArray(stream);
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getData() {
        return data;
    }
}
