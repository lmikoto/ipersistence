package io.github.lmikoto.ipersistence.io;

import java.io.InputStream;

/**
 * @author liuyang
 * 2020/9/26 4:43 下午
 */
public class Resource {

    /**
     * 将配置文件加载到内存里
     * @param path
     * @return
     */
    public static InputStream getResourceAsStream(String path){
        return Resource.class.getClassLoader().getResourceAsStream(path);
    }
}
