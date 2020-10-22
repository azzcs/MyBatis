package com.azzcs.sqlnode;

import java.util.List;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:47 下午
 */
public class MixedSqlNode implements SqlNode{
    private List<SqlNode> sqlNodes;

    public MixedSqlNode(List<SqlNode> sqlNodes) {
        this.sqlNodes = sqlNodes;
    }

    @Override
    public void apply(DynamicContext context) {
        for(SqlNode sqlNode:sqlNodes){
            sqlNode.apply(context);
        }
    }
}
