package com.azzcs.sqlnode;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:24 下午
 */
public interface SqlNode {
    void apply(DynamicContext context);
}
