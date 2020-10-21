package com.azzcs.jdbc;

import java.sql.*;

/**
 * @Author: wzg
 * @Date: 2020/10/21 10:49 上午
 */
public class Test {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 创建连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&useSSL=false","root","azzcs");

            // 编写sql
            String sql = "SELECT * FROM `user` WHERE id = ?";
            // 获取预处理statement
            statement = connection.prepareStatement(sql);

            // 添加参数
            statement.setInt(1,1);

            // 执行sql
            ResultSet resultSet = statement.executeQuery();

            // 解析返回值
            while (resultSet.next()){
                String userInfo = String.format("user[id:%d,name:%s]", resultSet.getInt("id"), resultSet.getString("name"));
                System.out.println(userInfo);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭资源
            try {
                if(statement != null && !statement.isClosed()) {
                    statement.close();
                }
                if(connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }
    }
}
