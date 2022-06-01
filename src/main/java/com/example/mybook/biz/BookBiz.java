package com.example.mybook.biz;

import com.example.mybook.bean.Book;
import com.example.mybook.bean.Record;
import com.example.mybook.bean.Type;
import com.example.mybook.dao.BookDao;
import com.example.mybook.dao.RecordDao;
import com.example.mybook.dao.TypeDao;

import java.sql.SQLException;
import java.util.List;

public class BookBiz {
    BookDao bookDao = new BookDao();
    public List<Book> getByPage(int pageIndex, int pageSize ){
    List<Book> books = null;
    TypeDao typeDao = new TypeDao();
        try {
            books = bookDao.getByPage(pageIndex,pageSize);
            for(Book book: books)
            {
                long typeId = book.getTypeId();
                Type type = typeDao.getById(typeId);
                book.setType(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;

    }
    public Book getById(long id){
        Book book = null;
        TypeDao typeDao = new TypeDao();
        try {
             book = bookDao.getById(id);
            long typeId = book.getTypeId();
            Type type = typeDao.getById(typeId);
            book.setType(type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
    public int getCount(){
        int count = 0;
        try {
            count = bookDao.getCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public int remove(long id) throws Exception{
        RecordDao recordDao = new RecordDao();
        int count = 0;
        try {
            List<Record> records = recordDao.getRecordByBookId(id);
            if(records.size() > 0)
            {
                throw new Exception("删除的书籍有子信息，删除失败");
            }
            count = bookDao.remove(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }
    public int modify(long id,long typeId, String name, double price, String desc, String pic, String publish,
                      String author, long stock, String address){
        int count = 0;
        try {
            count = bookDao.modify(id, typeId, name, price, desc, pic, publish, author, stock, address);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public int modify(Book book)
    {
        int count = 0;
        try {
            count = bookDao.modify(book.getId(),book.getTypeId(),book.getName(),book.getPrice(),
                    book.getDesc(),book.getPic(),book.getPublish(),book.getAuthor(),book.getStock(),
                    book.getAddress());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public int add(long typeId, String name, double price, String desc, String pic, String publish,
                   String author, long stock, String address){
        int count = 0;
        try {
          count =  bookDao.add(typeId, name, price, desc, pic, publish, author, stock, address);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public int add(Book book)
    {
        return add(book.getTypeId(),book.getName(),book.getPrice(),book.getDesc(),book.getPic(),book.getPublish(),
                book.getAuthor(),book.getStock(),book.getAddress());

    }

    public List<Book> getBooksByTypeId(long typeId){
        try {
            return bookDao.getBooksByTypeId(typeId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public int getPageCount(int pazeSize){
        int rowCount = 0;
        try {
            rowCount = bookDao.getCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (rowCount - 1) / pazeSize + 1;
    }
}
