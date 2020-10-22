package com.azzcs.sqlnode;

import com.azzcs.utils.OgnlUtils;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:58 下午
 */
public class IfSqlNode implements SqlNode{

    private String test;

    private SqlNode rootSqlNode;

    public IfSqlNode(String test, SqlNode rootSqlNode) {
        this.test = test;
        this.rootSqlNode = rootSqlNode;
    }

    @Override
    public void apply(DynamicContext context) {
        boolean testValue = OgnlUtils.evaluateBoolean(test, context.getBindings().get("_parameter"));
        if (testValue) {
            rootSqlNode.apply(context);
        }
    }
}
