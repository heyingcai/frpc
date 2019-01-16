package com.jibug.frpc.common.codec.compress;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * @author heyingcai
 */
public class SnappyCompress implements Compress{

    @Override
    public byte[] compress(byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }
        return Snappy.compress(bytes);
    }

    @Override
    public byte[] restore(byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }
        return Snappy.uncompress(bytes);
    }
}
