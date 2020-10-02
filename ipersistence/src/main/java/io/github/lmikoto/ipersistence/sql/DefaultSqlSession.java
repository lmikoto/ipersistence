package io.github.lmikoto.ipersistence.sql;

import io.github.lmikoto.ipersistence.model.Configuration;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.List;

/**
 * @author liuyang
 * 2020/9/26 7:26 下午
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <MODEL> List<MODEL> selectList(String statementId, Object... params) {
        Executor executor = new SimpleExecutor();
        return executor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
    }

    @Override
    public <MODEL> MODEL selectOne(String statementId, Object... param) {
        List<MODEL> objects = selectList(statementId, param);
        if (objects.size() == 1) {
            return objects.get(0);
        } else {
            throw new RuntimeException("查询结果为空或返回多个");
        }
    }

    @Override
    public Integer update(String statementId, Object... params) {
        Executor executor = new SimpleExecutor();
        return executor.update(configuration, configuration.getMappedStatementMap().get(statementId), params);
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{mapperClass}, (proxy, method, args) -> {
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();
            String statementId = className + "." + methodName;
            Type genericReturnType = method.getGenericReturnType();
            if(genericReturnType instanceof ParameterizedType){
               return selectList(statementId,args);
            } else if(genericReturnType == Integer.class){
                return update(statementId,args);
            } else{
                return selectOne(statementId,args);
            }
        });
        return (T) proxyInstance;
    }
}