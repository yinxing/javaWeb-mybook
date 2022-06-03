package com.example.mybook.dao;

import com.example.mybook.bean.Member;
import com.example.util.DBHelper;
import com.mysql.cj.protocol.a.MergingColumnDefinitionFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MemberDao {
    QueryRunner runner = new QueryRunner();
    public int add(String name, String pwd, long typeId, double balance,
                   String tel, String idNumber) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "insert into member (`name`,pwd,typeId,balance,regdate,tel,idNumber) values(?,?,?,?, current_date,?,?)";
        int count = runner.update(conn,sql,name,pwd,typeId,balance,tel,idNumber);
        DBHelper.close(conn);
        return count;
    }
    public int modify(long id,String name, long typeId, double balance,
                   String tel, String idNumber) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "update member set `name` = ?, typeId = ?, balance=?, tel=?, idNumber=? where id=?";
        int count = runner.update(conn,sql,name,typeId,balance,tel,idNumber,id);
        DBHelper.close(conn);
        return count;
    }
    public int modify(Member member) throws SQLException {
       int count =  modify(member.getId(),member.getName() ,member.getTypeId(),member.getBalance(),
                member.getTel(),member.getIdNumber());

       return count;
    }
    public int add(Member member) throws SQLException {
        int count = add(member.getName(),member.getPwd(),member.getTypeId(),member.getBalance(),member.getTel(),
                member.getIdNumber());
        return count;
    }
    public int remove(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "delete from member where id = ?";
        int count = runner.update(conn,sql,id);
        DBHelper.close(conn);
        return count;
    }
    public int modifyBanlance( double money, String idNumber) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "update member set balance = balance + ? where idNumber=?";
        int count = runner.update(conn,sql,money,idNumber);
        DBHelper.close(conn);
        return count;
    }public int modifyBanlance(long id, double money) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "update member set balance = balance + ? where id=?";
        int count = runner.update(conn,sql,money,id);
        DBHelper.close(conn);
        return count;
    }
    public List<Member> getAll() throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="select id,`name`,pwd,typeId,balance,regdate,tel,idNumber from member";
        List<Member> members = runner.query(conn,sql,new BeanListHandler<Member>(Member.class));
        DBHelper.close(conn);
        return  members;
    }
    public Member getById(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select id,`name`,pwd,typeId,balance,regdate,tel,idNumber from member where id=?";
        Member member = runner.query(conn,sql,new BeanHandler<Member>(Member.class),id);
        DBHelper.close(conn);
        return member;
    }
    public Member getByIdNumber(String idNumber) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select id,`name`,pwd,typeId,balance,regdate,tel,idNumber from member where idNumber=?";
        Member member = runner.query(conn,sql,new BeanHandler<Member>(Member.class),idNumber);
        DBHelper.close(conn);
        return member;
    }
    public boolean exists(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select count(*) from record where id = ?";
        Number number = runner.query(conn,sql,new ScalarHandler<>(),id);
        DBHelper.close(conn);
        return number.intValue()!=0 ? true : false;
    }

    public static void main(String[] args) {
        MemberDao memberDao = new MemberDao();
        int count = 0;
        try {

            System.out.println(memberDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
