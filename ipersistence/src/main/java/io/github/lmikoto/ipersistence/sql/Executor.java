package io.github.lmikoto.ipersistence.sql;

import io.github.lmikoto.ipersistence.model.Configuration;
import io.github.lmikoto.ipersistence.model.MappedStatement;

import java.util.List;

/**
 * @author liuyang
 * 2020/9/26 7:41 下午
 */
public interface Executor {

    /**
     *  查询
     * @param configuration
     * @param mappedStatement
     * @param param
     * @param <MODEL>
     * @return
     */
     <MODEL> List<MODEL> query(Configuration configuration, MappedStatement mappedStatement,Object... param);

    /**
     * 更新操作
     * @param configuration
     * @param mappedStatement
     * @param param
     * @return
     */
     Integer update(Configuration configuration, MappedStatement mappedStatement,Object... param);
}
