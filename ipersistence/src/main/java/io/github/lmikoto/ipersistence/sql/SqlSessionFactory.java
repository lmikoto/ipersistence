package io.github.lmikoto.ipersistence.sql;

/**
 * @author liuyang
 * 2020/9/26 5:16 下午
 */
public interface SqlSessionFactory {

    /**
     * 打开session
     * @return
     */
    SqlSession openSession();
}
