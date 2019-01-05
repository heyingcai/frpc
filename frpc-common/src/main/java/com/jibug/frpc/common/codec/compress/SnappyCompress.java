package com.jibug.frpc.core.common.codec.compress;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * @author heyingcai
 */
public class SnappyCompress implements Compress{

    public byte[] compress(byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }
        return Snappy.compress(bytes);
    }

    public byte[] restore(byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }
        return Snappy.uncompress(bytes);
    }
}
