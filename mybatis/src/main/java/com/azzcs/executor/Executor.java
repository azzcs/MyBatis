package com.azzcs.executor;

import com.azzcs.config.Configuration;
import com.azzcs.config.MappedStatement;

import java.util.List;

/**
 * @Author: wzg
 * @Date: 2020/10/23 10:15 上午
 */
public interface Executor {
    <T>List<T> selectList(MappedStatement mappedStatement,
                          Configuration configuration,
                          Object param);
}
