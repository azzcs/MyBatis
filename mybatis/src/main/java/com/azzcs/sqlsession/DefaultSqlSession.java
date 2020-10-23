package com.azzcs.sqlsession;

import com.azzcs.config.Configuration;
import com.azzcs.config.MappedStatement;
import com.azzcs.executor.CachingExecutor;
import com.azzcs.executor.Executor;
import com.azzcs.executor.SimpleExecutor;

import java.util.List;

/**
 * @Author: wzg
 * @Date: 2020/10/23 10:08 上午
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statementId, Object param) {
        List<Object> objects = this.selectList(statementId, param);
        if(objects != null && objects.size() == 1){
            return (T)objects.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statementId);
        Executor executor = new CachingExecutor(new SimpleExecutor());
        return executor.selectList(mappedStatement,configuration,param);
    }
}
