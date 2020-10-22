package com.azzcs.config;

import com.azzcs.io.Resources;
import com.azzcs.utils.DocumentUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @Author: wzg
 * @Date: 2020/10/21 2:13 下午
 */
public class XmlConfigBuilder {

    private Configuration configuration;

    public Configuration parse(InputStream inputStream){
        Document document = DocumentUtils.readDocument(inputStream);
        Element root = document.getRootElement();
        parseConfiguration(root);
        return configuration;
    }

    private void parseConfiguration(Element root) {
        
        Element environments = root.element("environments");
        parseEnvironments(environments);
        
        Element mappers = root.element("mappers");
        parseMappers(mappers);
    }


    private void parseEnvironments(Element environments) {
        String environmentId = environments.attribute("default").getStringValue();
        if(environmentId == null || environmentId.equals("")){
            return;
        }
        List<Element> environmentList = environments.elements("environment");
        for(Element element:environmentList){
            String id = element.attribute("id").getStringValue();
            if(environmentId.equals(id)){
                parseDataSource(element.element("dataSource"));
                break;
            }
        }
    }

    private void parseDataSource(Element element) {
        String type = element.attributeValue("type");
        if("DBCP".equals(type)) {
            Properties properties = new Properties();
            BasicDataSource dataSource = new BasicDataSource();
            List<Element> propertiesElement = element.elements("property");
            for (Element property : propertiesElement) {
                String key = property.attributeValue("name");
                String value = property.attributeValue("value");
                properties.setProperty(key, value);
            }

            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            configuration = new Configuration(dataSource);
        }
    }

    private void parseMappers(Element mappers) {
        List<Element> mapperElements = mappers.elements("mapper");
        for(Element mapper:mapperElements){
            parseMapper(mapper);
        }
    }

    private void parseMapper(Element mapper) {
        String resource = mapper.attributeValue("resource");
        InputStream inputStream = Resources.getResourceAsStream(resource);
        Document document = DocumentUtils.readDocument(inputStream);

        XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(configuration);

        mapperBuilder.parse(document.getRootElement());
    }
}
