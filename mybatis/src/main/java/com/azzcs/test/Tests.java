package com.azzcs.test;

import com.azzcs.config.Configuration;
import com.azzcs.config.XmlConfigBuilder;
import com.azzcs.io.Resources;

import java.io.InputStream;

/**
 * @Author: wzg
 * @Date: 2020/10/21 1:58 下午
 */
public class Tests {
    public static void main(String[] args) throws Exception {
        String location = "mybatis-config.xml";

        Configuration config;
        try (InputStream inputStream = Resources.getResourceAsStream(location)) {
            XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
            config = xmlConfigBuilder.parse(inputStream);
        }
        System.out.println(config);
    }
}
