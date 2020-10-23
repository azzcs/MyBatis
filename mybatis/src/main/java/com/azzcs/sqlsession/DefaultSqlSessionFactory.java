package com.azzcs.sqlsession;

import com.azzcs.config.Configuration;

/**
 * @Author: wzg
 * @Date: 2020/10/23 9:49 上午
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }

}
