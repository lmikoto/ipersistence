package io.github.lmikoto.ipersistence.sql;

import io.github.lmikoto.ipersistence.config.XmlConfigBuilder;
import io.github.lmikoto.ipersistence.model.Configuration;

import java.io.InputStream;

/**
 * @author liuyang
 * 2020/9/26 5:14 下午
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) {
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);
        SqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
