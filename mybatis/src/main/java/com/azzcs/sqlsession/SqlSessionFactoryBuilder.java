package com.azzcs.sqlsession;

import com.azzcs.config.Configuration;
import com.azzcs.config.XmlConfigBuilder;

import java.io.InputStream;

/**
 * @Author: wzg
 * @Date: 2020/10/23 10:01 上午
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) {
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration config = xmlConfigBuilder.parse(inputStream);
        return new DefaultSqlSessionFactory(config);
    }
}
