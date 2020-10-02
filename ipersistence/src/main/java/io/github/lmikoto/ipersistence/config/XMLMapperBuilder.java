package io.github.lmikoto.ipersistence.config;

import io.github.lmikoto.ipersistence.model.Configuration;
import io.github.lmikoto.ipersistence.model.MappedStatement;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author liuyang
 * 2020/9/26 7:01 下午
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration conf){
        this.configuration = conf;
    }

    @SneakyThrows
    public void parse(InputStream in){
        Document document = new SAXReader().read(in);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        parseSelect(rootElement,namespace);
        parseUpdate(rootElement,namespace);
    }

    private void parseSelect(Element rootElement,String namespace){
        List<Element> selectList = rootElement.selectNodes("//select");
        for(Element element: selectList){
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sqlText = element.getTextTrim();

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sqlText);
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
    }

    private void parseUpdate(Element rootElement,String namespace){
        List<Element> updateList = rootElement.selectNodes("//update|insert|delete");
        for(Element element: updateList){
            String id = element.attributeValue("id");
            String resultType = "java.lang.Integer";
            String parameterType = element.attributeValue("parameterType");
            String sqlText = element.getTextTrim();

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sqlText);
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
    }
}
