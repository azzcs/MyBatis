package com.azzcs.sqlsource;

import com.azzcs.utils.GenericTokenParser;
import com.azzcs.utils.ParameterMappingTokenHandler;

/**
 * @Author: wzg
 * @Date: 2020/10/23 11:01 上午
 */
public class SqlSourceParser {
    public SqlSource parse(String sql) {
        // 先处理${}，将处理之后的SQL语句，追加到context中
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        // 此处获取到的sql语句，是完全符合JDBC执行要求的语句
        sql = tokenParser.parse(sql);
        // 将解析之后的sql语句和参数集合都封装到StaticSqlSource中
        return new StaticSqlSource(sql, tokenHandler.getParameterMappings());
    }
}
