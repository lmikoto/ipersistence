package io.github.lmikoto.ipersistence.sql;

import io.github.lmikoto.ipersistence.model.Configuration;

/**
 * @author liuyang
 * 2020/9/26 7:22 下午
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
