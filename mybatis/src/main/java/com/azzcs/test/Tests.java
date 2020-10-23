package com.azzcs.test;

import com.azzcs.config.Configuration;
import com.azzcs.io.Resources;
import com.azzcs.sqlsession.DefaultSqlSessionFactory;
import com.azzcs.sqlsession.SqlSession;
import com.azzcs.sqlsession.SqlSessionFactory;
import com.azzcs.sqlsession.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @Author: wzg
 * @Date: 2020/10/21 1:58 下午
 */
public class Tests {
    public static void main(String[] args) throws Exception {
        Configuration config;
        try (InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml")) {
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sessionFactory.openSqlSession();
            User param = new User();
            param.setId(1);
            User user = sqlSession.selectOne("com.azzcs.test.UserDao.findUserById", param);
            System.out.println(user);
        }
    }
}
