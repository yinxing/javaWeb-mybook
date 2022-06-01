package com.example.mybook.dao;

import com.example.mybook.bean.Type;
import com.example.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.lang.model.element.TypeElement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TypeDao {
    QueryRunner runner = new QueryRunner();

    /**
     * 添加图书类型
     * @param name
     * @param parentId
     * @return
     */
    public int add(String name, long parentId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "insert into type values(null,?,?)";
        int count = runner.update(conn,sql,name,parentId);
        conn.close();
        return count;
    }

    /**
     * 获取所有类型
     * @return
     */
    public List<Type> getAll() throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select id, name, parentId from type";
        List<Type> types = runner.query(conn, sql, new BeanListHandler<Type>(Type.class));

        conn.close();
        return types;
    }

    /**
     * 根据类型编号获取类型对象
     * @param typeId
     * @return
     */
    public Type getById(long typeId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select id, name, parentId from type where id = ?";
        Type type = runner.query(conn,sql,new BeanHandler<Type>(Type.class),typeId);

        conn.close();
        return type;
    }

    /**
     * 修改图书类型
     * @param id
     * @param name
     * @param parentId
     * @return
     */
    public int modify(long id, String name, long parentId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "update type set name=?, parentId=? where id=?";
        int count = runner.update(conn,sql,name,parentId,id);
        conn.close();
        return count;
    }

    /**
     * 删除图书类型
     * @param id
     * @return
     */
    public int remove(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "delete from type where id=?";
        int count = runner.update(conn,sql,id);
        conn.close();
        return count;
    }
    public static void main(String args[])
    {
        TypeDao typedao = new TypeDao();
        int count = 0;
        Type type = null;
        
        try {
            type = typedao.getById(14);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(type);
    }

}
