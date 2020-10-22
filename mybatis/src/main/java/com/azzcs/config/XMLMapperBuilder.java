package com.azzcs.config;

import org.dom4j.Element;

import java.util.List;

/**
 * @Author: wzg
 * @Date: 2020/10/22 2:47 下午
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(Element mapperElement) {
        String namespace = mapperElement.attributeValue("namespace");
        List<Element> selectElement = mapperElement.elements("select");
        for(Element select:selectElement){
            XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration);
            statementBuilder.parse(select,namespace);
        }
    }
}
