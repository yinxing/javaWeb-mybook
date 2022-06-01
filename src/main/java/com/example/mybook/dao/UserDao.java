package com.example.mybook.dao;

import com.example.mybook.bean.User;
import com.example.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 用户表的数据操作对象
 */
public class UserDao {
    // 创建QueryRunner 对象(jdbc--->dbutils)
    QueryRunner runner = new QueryRunner();
    public User getUser(String name, String pwd) throws SQLException
    {
        // 1.调用DBHelper获取连接
        Connection conn = DBHelper.getConnection();
        // 2.准备执行的sql语句
        String sql = "select * from user where name=? and pwd=? and state= 1";
        // 3.调用查询方法,将查询的数据分装成user对象
        User user = runner.query(conn, sql, new BeanHandler<User>(User.class),name,pwd);
        // 4.关闭连接对象
        conn.close();
        // 5.返回user
        return user;
    }

    /**
     * 修改密码
     * @param id 需要修改用户的id
     * @param pwd 密码
     * @return
     */
    public int modifyPwd(long id, String pwd) throws SQLException{
        String sql = "update user set pwd=? where id=?";
        Connection conn = DBHelper.getConnection();
        int count = runner.update(conn,sql,pwd,id);
        conn.close();
        return count;

    }

    public static void main(String[] args)
    {
        int count = 0;
        try{
             count = new UserDao().modifyPwd(3, "123");
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        System.out.println(count);
    }
}
