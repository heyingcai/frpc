package com.jibug.frpc.common.codec.serialize.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jibug.frpc.common.codec.serialize.Serialize;

import java.io.IOException;

/**
 * @author heyingcai
 */
public class JsonSerialize implements Serialize {

    @Override
    public byte[] serialize(Object object) throws IOException {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.config(SerializerFeature.WriteClassName, true);
        serializer.write(object);
        return out.toBytes("utf-8");
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        return JSON.parseObject(new String(bytes), clazz);
    }

}
