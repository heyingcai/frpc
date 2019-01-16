package com.jibug.frpc.common.codec.compress;

import org.anarres.lzo.LzoAlgorithm;
import org.anarres.lzo.LzoCompressor;
import org.anarres.lzo.LzoDecompressor;
import org.anarres.lzo.LzoInputStream;
import org.anarres.lzo.LzoLibrary;
import org.anarres.lzo.LzoOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author heyingcai
 */
public class LzoCompress implements Compress {

    @Override
    public byte[] compress(byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }
        LzoCompressor compressor = LzoLibrary.getInstance().newCompressor(LzoAlgorithm.LZO1X, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        LzoOutputStream los = new LzoOutputStream(os, compressor);
        los.write(bytes);
        los.close();
        return os.toByteArray();
    }

    @Override
    public byte[] restore(byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }
        LzoDecompressor decompressor = LzoLibrary.getInstance().newDecompressor(LzoAlgorithm.LZO1X, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        LzoInputStream us = new LzoInputStream(is, decompressor);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = us.read(buffer)) != -1) {
            os.write(buffer, 0, count);
        }
        return os.toByteArray();
    }
}
