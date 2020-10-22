package com.azzcs.sqlsource;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:12 下午
 */
public interface SqlSource {

    /**
     * 根据入参对象，获取JDBC可以执行的SQL语句
     *
     * 执行阶段才会调用该方法
     *
     * @param paramObject
     * @return
     */
    BoundSql getBoundSql(Object paramObject);


}
