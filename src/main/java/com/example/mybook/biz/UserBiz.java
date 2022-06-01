package com.example.mybook.biz;

import com.example.mybook.bean.User;
import com.example.mybook.dao.UserDao;

import java.sql.SQLException;

public class UserBiz {
    UserDao userDao = new UserDao();
    //获取对象
    public User getUser(String name, String pwd){
        User user= null;
        try{
            user = userDao.getUser(name, pwd);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return user;
    }
    //修改密码
    public int modifyPwd(long id, String pwd){
        int count = 0;
        try{
            count = userDao.modifyPwd(id,pwd);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return count;
    }
    public static void main(String[] args)
    {
        int count = 0;
            count = new UserBiz().modifyPwd(3, "123");
        System.out.println(count);
    }
}
