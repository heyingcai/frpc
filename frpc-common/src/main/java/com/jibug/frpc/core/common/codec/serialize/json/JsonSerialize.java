package com.jibug.frpc.core.common.codec.serialize.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.jibug.frpc.core.common.codec.serialize.Serialize;

import java.io.IOException;

/**
 * @author heyingcai
 */
public class JsonSerialize implements Serialize {

    public byte[] serialize(Object object) throws IOException {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.config(SerializerFeature.WriteClassName, true);
        serializer.write(object);
        return out.toBytes(IOUtils.UTF8);
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        return JSON.parseObject(new String(bytes), clazz);
    }

}
