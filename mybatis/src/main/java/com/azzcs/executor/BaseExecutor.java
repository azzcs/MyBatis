package com.azzcs.executor;

import com.azzcs.config.Configuration;
import com.azzcs.config.MappedStatement;
import com.azzcs.sqlsource.BoundSql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wzg
 * @Date: 2020/10/23 10:23 上午
 */
public abstract class BaseExecutor implements Executor{

    private Map<String,List<Object>> cache = new HashMap<>();

    @Override
    public <T> List<T> selectList(MappedStatement mappedStatement, Configuration configuration, Object param) {
        BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(param);
        List<Object> objects = cache.get(boundSql.getSql());
        if(objects != null){
            return (List<T>)objects;
        }
        objects = queryFromDataBase(mappedStatement,configuration,param,boundSql);
        if(objects != null){
            cache.put(boundSql.getSql(),objects);
        }
        return (List<T>)objects;
    }

    protected abstract List<Object> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration, Object param, BoundSql boundSql);
}
