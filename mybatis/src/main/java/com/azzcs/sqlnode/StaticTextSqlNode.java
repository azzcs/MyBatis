package com.azzcs.sqlnode;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:44 下午
 */
public class StaticTextSqlNode implements SqlNode{

    private String sqlText;

    public StaticTextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {
        context.appendSql(sqlText);
    }
}
