package io.github.lmikoto.ipersistence.sql;

import java.util.List;

/**
 * @author liuyang
 * 2020/9/26 7:25 下午
 */
public interface SqlSession {

    /**
     * select list
     * @param statementId
     * @param params
     * @param <MODEL>
     * @return
     */
   <MODEL> List<MODEL> selectList(String statementId,Object... params);

    /**
     * select one
     * @param statementId
     * @param params
     * @param <MODEL>
     * @return
     */
   <MODEL> MODEL selectOne(String statementId,Object... params);


    /**
     * 更新
      * @param statementId
     * @param params
     * @return
     */
   Integer update(String statementId,Object... params);

    /**
     * 生成dao对象
     * @param mapperClass
     * @return
     */
   <T> T getMapper(Class<?> mapperClass);
}
