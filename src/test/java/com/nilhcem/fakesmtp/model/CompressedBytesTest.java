package com.nilhcem.fakesmtp.model;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author soldier
 */
public class CompressedBytesTest {

    @Test
    public void testCompressAndDecompress() {
        byte[] in = new byte[]{0, 1, 2, 2, 3, 5, 5, 1, 1};
        CompressedBytes cb = new CompressedBytes(in);
        byte[] out = cb.get();
        Assert.assertArrayEquals(in, out);
    }
}
