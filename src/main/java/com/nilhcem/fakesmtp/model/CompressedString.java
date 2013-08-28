package com.nilhcem.fakesmtp.model;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author soldier
 */
public class CompressedString {

    private final CompressedBytes bytes;

    public CompressedString(String str) {
        try {
            bytes = new CompressedBytes(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException exc) {
            throw new RuntimeException(exc);
        }
    }

    public String get() {
        try {
            return new String(bytes.get(), "UTF-8");
        } catch (UnsupportedEncodingException exc) {
            throw new RuntimeException(exc);
        }
    }
}
