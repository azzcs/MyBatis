package com.azzcs.executor;

import com.azzcs.config.Configuration;
import com.azzcs.config.MappedStatement;

import java.util.List;

/**
 * @Author: wzg
 * @Date: 2020/10/23 10:32 上午
 */
public class CachingExecutor implements Executor{

    private Executor delegate;

    public CachingExecutor(Executor executor) {
        this.delegate = executor;
    }

    @Override
    public <T> List<T> selectList(MappedStatement mappedStatement, Configuration configuration, Object param) {
        // TODO 处理缓存


        return delegate.selectList(mappedStatement,configuration,param);
    }
}
