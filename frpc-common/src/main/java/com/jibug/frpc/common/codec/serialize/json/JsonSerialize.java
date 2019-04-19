package com.jibug.frpc.common.codec.serialize.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jibug.frpc.common.codec.serialize.Serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author heyingcai
 */
public class JsonSerialize implements Serialize {

    @Override
    public void serialize(OutputStream output, Object object) throws IOException {
        SerializeWriter out = new SerializeWriter(new OutputStreamWriter(output));
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.config(SerializerFeature.WriteClassName, true);
        serializer.write(object);
    }

    @Override
    public <T> T deserialize(InputStream inputStream, byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        return JSON.parseObject(new String(bytes), clazz);
    }

}
