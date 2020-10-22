package com.azzcs.config;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wzg
 * @Date: 2020/10/21 2:13 下午
 */
public class Configuration {

    private DataSource dataSource;

    private Map<String,MappedStatement> mappedStatements = new HashMap<>();

    public Configuration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
        this.mappedStatements.put(statementId,mappedStatement);
    }

    public MappedStatement getMappedStatement(String statementId){
        return this.mappedStatements.get(statementId);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
