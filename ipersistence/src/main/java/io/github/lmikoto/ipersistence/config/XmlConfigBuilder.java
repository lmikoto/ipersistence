package io.github.lmikoto.ipersistence.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import io.github.lmikoto.ipersistence.io.Resource;
import io.github.lmikoto.ipersistence.model.Configuration;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author liuyang
 * 2020/9/26 5:23 下午
 */
public class XmlConfigBuilder {

    private Configuration configuration;

    public XmlConfigBuilder(){
        this.configuration = new Configuration();
    }

    /**
     * 输入流转换configuration
     * @param in
     * @return
     */
    @SneakyThrows
    public Configuration parseConfig(InputStream in){
        Document document = new SAXReader().read(in);
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//property");
        Properties props = new Properties();
        for (Element element: list){
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            props.setProperty(name,value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(props.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(props.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(props.getProperty("username"));
        comboPooledDataSource.setPassword(props.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element mapperElement : mapperList) {
            String mapperPath = mapperElement.attributeValue("resource");
            InputStream mapperResource = Resource.getResourceAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(mapperResource);
        }
        return configuration;
    }
}
