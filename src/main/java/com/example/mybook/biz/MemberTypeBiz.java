package com.example.mybook.biz;

import com.example.mybook.bean.MemberType;
import com.example.mybook.dao.MemberTypeDao;
import com.example.util.DBHelper;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MemberTypeBiz {
    MemberTypeDao memberTypeDao = new MemberTypeDao();

    public List<MemberType> getAll() {
        try {
            return memberTypeDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MemberType getById(long id)  {
        try {
            return memberTypeDao.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
