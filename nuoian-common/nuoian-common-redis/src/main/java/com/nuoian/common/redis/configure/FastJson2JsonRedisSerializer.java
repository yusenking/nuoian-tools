package com.nuoian.common.redis.configure;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/8 16:58
 * @Description: Redis使用FastJson序列化
 * @Package: com.nuoian.common.redis.configure
 * @Version: 1.0
 */
@SuppressWarnings({"unused"})
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T>
{
    private ObjectMapper objectMapper = new ObjectMapper();

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final Class<T> clazz;

    public FastJson2JsonRedisSerializer(Class<T> clazz)
    {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException
    {
        if (t == null)
        {
            return new byte[0];
        }
        return JSONObject.toJSONString(t).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException
    {
        if (bytes == null || bytes.length <= 0)
        {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return JSON.parseObject(str, clazz);
    }

    public void setObjectMapper(ObjectMapper objectMapper)
    {
        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    protected JavaType getJavaType(Class<?> clazz)
    {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
