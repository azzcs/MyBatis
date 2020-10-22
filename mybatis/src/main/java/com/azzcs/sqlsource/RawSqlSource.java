package com.azzcs.sqlsource;

import com.azzcs.sqlnode.SqlNode;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:51 下午
 */
public class RawSqlSource implements SqlSource{
    private SqlNode sqlNode;

    public RawSqlSource(SqlNode sqlNode) {
        this.sqlNode = sqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object paramObject) {
        return null;
    }
}
