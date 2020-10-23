package com.azzcs.sqlsession;

import java.util.List;

/**
 * @Author: wzg
 * @Date: 2020/10/23 9:47 上午
 */
public interface SqlSession {

    <T> T selectOne(String statementId, Object param);

    <T> List<T> selectList(String statementId, Object param);
}
