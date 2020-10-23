package com.azzcs.sqlsource;

import com.azzcs.sqlnode.DynamicContext;
import com.azzcs.sqlnode.SqlNode;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:51 下午
 */
public class DynamicSqlSource implements SqlSource{

    private SqlNode sqlNode;

    public DynamicSqlSource(SqlNode sqlNode) {
        this.sqlNode = sqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object paramObject) {
        DynamicContext context = new DynamicContext(paramObject);
        // 将SqlNode处理成一条SQL语句
        sqlNode.apply(context);
        // 该SQL语句，此时还包含#{}，不包含${}
        String sql = context.getSql();
        // 通过SqlSourceParser去解析SqlSource中的#{}
        SqlSourceParser sqlSourceParser = new SqlSourceParser();
        // 将解析的结果，最终封装成StaticSqlSource
        SqlSource sqlSource = sqlSourceParser.parse(sql);
        // 调用StaticSqlSource的方法
        return sqlSource.getBoundSql(paramObject);
    }
}
