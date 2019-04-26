package com.jibug.frpc.common.codec.serialize.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.jibug.frpc.common.codec.serialize.Serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author heyingcai
 */
public class HessianSerialize implements Serialize {

    /**
     * hessian序列化
     *
     * @param object
     * @return
     * @throws IOException
     */
    @Override
    public void serialize(OutputStream output, Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        out.writeObject(object);
        out.completeMessage();
        out.close();
//        return bos.toByteArray();
    }

    /**
     * hessian反序列化
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public <T> T deserialize(InputStream inputStream, byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bis);
        input.startMessage();
        T result = (T) input.readObject(clazz);
        input.completeMessage();
        input.close();
        return result;
    }
}
