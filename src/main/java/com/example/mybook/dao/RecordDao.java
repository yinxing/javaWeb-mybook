package com.example.mybook.dao;

import com.example.mybook.bean.Record;
import com.example.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RecordDao {
    QueryRunner runner = new QueryRunner();
    public List<Record> getRecordByBookId(long bookId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select * from record where bookId = ?";
        List<Record> records = runner.query(conn, sql, new BeanListHandler<Record>(Record.class),bookId);
        return records;
    }

    public static void main(String[] args) {
        try {
            System.out.println(new RecordDao().getRecordByBookId(3).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
