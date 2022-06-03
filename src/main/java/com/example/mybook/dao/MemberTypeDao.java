package com.example.mybook.dao;

import com.example.mybook.bean.MemberType;
import com.example.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MemberTypeDao {
    QueryRunner runner = new QueryRunner();

    public List<MemberType> getAll() throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select * from membertype";
        List<MemberType> types = runner.query(conn, sql, new BeanListHandler<MemberType>(MemberType.class));
        DBHelper.close(conn);
        return types;
    }

    public MemberType getById(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select * from membertype where id = ?";
        MemberType type = runner.query(conn, sql, new BeanHandler<MemberType>(MemberType.class),id);
        DBHelper.close(conn);
        return type;
    }

    public static void main(String[] args) {
        MemberTypeDao memberTypeDao = new MemberTypeDao();
        try {
            System.out.println(memberTypeDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
