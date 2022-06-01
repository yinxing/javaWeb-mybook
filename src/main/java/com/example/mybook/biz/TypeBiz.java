package com.example.mybook.biz;

import com.example.mybook.bean.Book;
import com.example.mybook.bean.Type;
import com.example.mybook.dao.BookDao;
import com.example.mybook.dao.TypeDao;

import java.sql.SQLException;
import java.util.List;

public class TypeBiz {
    TypeDao typeDao = new TypeDao();

    public List<Type> getAll(){
        try {
            return typeDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int modify(long id, String name, long parentId)
    {
        int count = 0;
        try {
            count = typeDao.modify(id,name,parentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int add(String name, long parentId)
    {
        int count = 0;
        try {
            count = typeDao.add(name,parentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
// 删除 type 为书本的外键需要判断
    public int remove(long id) throws Exception{
        BookDao bookdao = new BookDao();
        int count = 0;
        try {
            List<Book> books = bookdao.getBooksByTypeId(id);
            if(books.size() > 0)
            {
                throw new Exception("删除类型用子信息，删除失败");
            }
            count = typeDao.remove(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Type getById(long id){
        try {
            return typeDao.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String args[])
    {
        TypeBiz typebiz = new TypeBiz();
        try {
            System.out.println(typebiz.add("王者", 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
