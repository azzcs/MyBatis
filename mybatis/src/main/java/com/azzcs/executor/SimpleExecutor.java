package com.azzcs.executor;

import com.azzcs.config.Configuration;
import com.azzcs.config.MappedStatement;
import com.azzcs.sqlsource.BoundSql;
import com.azzcs.sqlsource.ParameterMapping;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wzg
 * @Date: 2020/10/23 10:30 上午
 */
public class SimpleExecutor extends BaseExecutor {

    @Override
    protected List<Object> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration, Object param, BoundSql boundSql) {
        List<Object> results = new ArrayList<Object>();
        try {
            Connection connection = getConnection(configuration);
            String sql = boundSql.getSql();
            // 判断创建哪种Statement
            ResultSet resultSet = null;
            if ("prepared".equals(mappedStatement.getStatementType())) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                parameterize(preparedStatement, mappedStatement, boundSql, param);
                resultSet = preparedStatement.executeQuery();
            }
            // 处理结果集
            if (resultSet != null) {
                handleResultSet(mappedStatement, resultSet, results);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return results;
    }

    private void handleResultSet(MappedStatement mappedStatement, ResultSet resultSet, List<Object> results) {
        try {
            Class<?> resultTypeClass = mappedStatement.getResultTypeClass();
            while (resultSet.next()) {
                Object result = resultTypeClass.newInstance();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for(int i =0; i < columnCount; i++){
                    String columnName = metaData.getColumnName(i+1);
                    Object  value = resultSet.getObject(i+1);
                    Field field = resultTypeClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(result,value);
                }
                results.add(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parameterize(PreparedStatement preparedStatement, MappedStatement mappedStatement, BoundSql boundSql, Object param) {
        try {
            Class<?> parameterTypeClass = mappedStatement.getParameterTypeClass();
            if (parameterTypeClass == Integer.class) {

            } else {
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                for (int i = 0; i < parameterMappings.size(); i++) {
                    ParameterMapping parameterMapping = parameterMappings.get(i);
                    Field field = parameterTypeClass.getDeclaredField(parameterMapping.getName());
                    field.setAccessible(true);
                    Object value = field.get(param);
                    preparedStatement.setObject(i+1,value);
                }
            }
        }catch (Exception e){

        }
    }

    private Connection getConnection(Configuration configuration) {
        try {
            return configuration.getDataSource().getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
