package com.example.mybook.dao;

import com.example.mybook.bean.Record;
import com.example.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RecordDao {
    QueryRunner runner = new QueryRunner();
    public List<Record> getRecordByBookId(long bookId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select * from record where bookId = ?";
        List<Record> records = runner.query(conn, sql, new BeanListHandler<Record>(Record.class),bookId);
        DBHelper.close(conn);
        return records;
    }
    public List<Record> getRecordByIdNum(String IdNum) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select * from record where memberId = (select id from member where idNumber = ? )";
        List<Record> records = runner.query(conn, sql, new BeanListHandler<Record>(Record.class),IdNum);
        DBHelper.close(conn);
        return records;
    }
    public List<Record> getRecordBymemberId(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select * from record where memberId = ? and backDate is null";
        List<Record> records = runner.query(conn, sql, new BeanListHandler<Record>(Record.class),id);
        DBHelper.close(conn);
        return records;
    }
    public int add(long memberId, long bookId, double deposit, long userId) throws SQLException {
        String sql = "insert into record values(null, ?, ?, CURRENT_DATE, null, ?, ?, 'asfasdf')";
        Connection conn = DBHelper.getConnection();
        int count = runner.update(conn,sql,memberId,bookId,deposit,userId);
        DBHelper.close(conn);
        return count;
    }
    public int modify (double deposit, long userId, long id) throws SQLException {
        String sql = "update record set backDate = current_date, deposit=?, userId=? where id=?";
        Connection conn = DBHelper.getConnection();
        int count = runner.update(conn,sql,deposit,userId,id);
        DBHelper.close(conn);
        return count;
    }
    public int modify (long id) throws SQLException {
        String sql = "update record set rentDate = current_date where id=?";
        Connection conn = DBHelper.getConnection();
        int count = runner.update(conn,sql,id);
        DBHelper.close(conn);
        return count;
    }
    public Record getById(long RecordId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select * from record where id = ?";
        Record record = runner.query(conn, sql, new BeanHandler<Record>(Record.class),RecordId);
        DBHelper.close(conn);
        return record;
    }
    public List<Map<String, Object>> query(int typeId, String keywork) throws SQLException {
        Connection conn = DBHelper.getConnection();
        StringBuilder sql = new StringBuilder("select * from recordView where 1=1");
        switch (typeId){
            case 0:
                break;
            case 1:
                sql.append(" and backDate is not null");
                break;
            case 2:
                sql.append(" and backDate is null");
                break;
            case 3:
                sql.append(" and returnDate < date_add(current_date,interval 7 DAY) and backDate is null");
                break;
        }

        if(keywork!=null)
        {
            sql.append(" and (bookName like '%"+keywork+"%' or memberName like '%"+keywork+"%' or concat(rentDate, '')\n" +
                    "like '%"+keywork+"%')");
        }
        List<Map<String, Object>> data = runner.query(conn, sql.toString(), new MapListHandler());
        DBHelper.close(conn);
        return data;
    }


    public static void main(String[] args) {
        try {
            System.out.println(new RecordDao().getRecordByBookId(3).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
