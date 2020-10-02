package io.github.lmikoto.ipersistence.model;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyang
 * 2020/9/26 5:02 下午
 */
@Data
public class Configuration {

    private DataSource dataSource;

    /**
     * key statementId
     * value mappedStatement
     */
    private Map<String,MappedStatement> mappedStatementMap = new HashMap<>();
}
