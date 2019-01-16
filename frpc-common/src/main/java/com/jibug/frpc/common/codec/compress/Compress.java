package com.jibug.frpc.common.codec.compress;

import java.io.IOException;

/**
 * @author heyingcai
 */
public interface Compress {

    /**
     * 压缩字节
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    byte[] compress(byte[] bytes) throws IOException;

    /**
     * 还原字节
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    byte[] restore(byte[] bytes) throws IOException;

}
