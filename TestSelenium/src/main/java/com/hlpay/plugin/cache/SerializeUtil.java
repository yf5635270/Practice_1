package com.hlpay.plugin.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列化工具 实现对象序列化处理(TODO:建议放到plugin下IO相关的项目中)
 *
 * @author 黄麟峰
 */
public class SerializeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerializeUtil.class);

    /**
     * 序列化
     *
     * @param object 待序列化对象
     * @return 序列化的字节
     */
    public static byte[] serialize(Object object) {
        try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(byteOutputStream)) {
                oos.writeObject(object);
                byte[] bytes = byteOutputStream.toByteArray();
                return bytes;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("serialize Object error! maybe the object mush extends Serializable!");
        }
    }

    /**
     * 反序列化
     *
     * @param bytes 已序列化的字节
     * @return 反序列化后的对象
     */
    public static Object deserialize(byte[] bytes) {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }

        ByteArrayInputStream bais = null;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException("deserialize Object error!IO Exception.");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException("deserialize Object error!Class Not Found.");
        }
    }
}
