package io.github.lmikoto.ipersistence.sql;

import io.github.lmikoto.ipersistence.model.Configuration;
import io.github.lmikoto.ipersistence.model.MappedStatement;
import io.github.lmikoto.ipersistence.utils.GenericTokeParser;
import io.github.lmikoto.ipersistence.utils.ParameterMapping;
import io.github.lmikoto.ipersistence.utils.ParameterMappingTokenHandler;
import io.github.lmikoto.ipersistence.utils.TokenHandler;
import lombok.SneakyThrows;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liuyang
 * 2020/9/26 7:43 下午
 */
public class SimpleExecutor implements Executor {

    private Connection connection;

    @Override
    @SneakyThrows
    public <MODEL> List<MODEL> query(Configuration configuration, MappedStatement mappedStatement, Object... param) {

        // 初始化connection
        setConnection(configuration);

        // 组装入参，获取
        PreparedStatement preparedStatement = getPreparedStatement(mappedStatement,param);

        // 执行查询
        ResultSet resultSet = preparedStatement.executeQuery();

        // 组装返回结果
        List<MODEL> resultList = getResultList(mappedStatement,resultSet);

        // 关闭连接
        close();
        return resultList;

    }

    @Override
    @SneakyThrows
    public Integer update(Configuration configuration, MappedStatement mappedStatement, Object... param) {
        // 初始化connection
        setConnection(configuration);

        // 组装入参，获取
        PreparedStatement preparedStatement = getPreparedStatement(mappedStatement,param);

        // 执行更新
        int row = preparedStatement.executeUpdate();

        // 关闭连接
        close();
        return row;
    }

    @SneakyThrows
    private Class<?> getClass(String className) {
        if(Objects.nonNull(className)){
            Class<?> clazz = Class.forName(className);
            return clazz;
        }
        return null;
    }

    @SneakyThrows
    private void close(){
        connection.close();
    }

    private BandSql getBandSql(String sql) {
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokeParser genericTokeParser = new GenericTokeParser("#{","}",tokenHandler);
        String parseSql = genericTokeParser.parse(sql);
        return new BandSql(parseSql, tokenHandler.getParameterMappings());
    }

    @SneakyThrows
    private void setConnection(Configuration configuration){
        connection = configuration.getDataSource().getConnection();
    }

    @SneakyThrows
    private PreparedStatement getPreparedStatement(MappedStatement mappedStatement,Object... param){
        String sql = mappedStatement.getSql();
        BandSql bandSql = getBandSql(sql);

        PreparedStatement preparedStatement = connection.prepareStatement(bandSql.getSqlText());

        List<ParameterMapping> parameterMappingList = bandSql.getParameterMappingList();

        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterClass = getClass(parameterType);

        int index = 1;
        for (ParameterMapping parameterMapping : parameterMappingList) {
            String content = parameterMapping.getContent();
            Field field = parameterClass.getDeclaredField(content);
            field.setAccessible(true);

            Object o = field.get(param[0]);
            preparedStatement.setObject(index,o);
            index++;
        }
        return preparedStatement;
    }

    @SneakyThrows
    private <MODEL> List<MODEL> getResultList(MappedStatement mappedStatement,ResultSet resultSet){
        Class<?> resultClass = getClass(mappedStatement.getResultType());
        List<MODEL> resultList = new ArrayList<>();
        while (resultSet.next()){
            MODEL result = (MODEL) resultClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i < metaData.getColumnCount() + 1;i++){
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(columnName);

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(result,columnValue);
            }
            resultList.add(result);
        }
        return resultList;
    }
}