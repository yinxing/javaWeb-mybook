package com.example.mybook.biz;

import com.example.mybook.bean.Book;
import com.example.mybook.bean.Member;
import com.example.mybook.bean.Record;
import com.example.mybook.dao.BookDao;
import com.example.mybook.dao.MemberDao;
import com.example.mybook.dao.RecordDao;
import com.example.util.DBHelper;
import com.example.util.DateHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class RecordBiz {
    RecordDao recordDao = new RecordDao();
    BookDao bookDao = new BookDao();
    MemberDao memberDao = new MemberDao();
    MemberBiz memberBiz = new MemberBiz();
    public List<Record> getRecordByIdNum(String IdNum){
        List<Record> records = null;
        try {
            records=recordDao.getRecordByIdNum(IdNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
    public List<Record> getRecordBymemberId(long id){
        List<Record> records = null;
        try {
            records=recordDao.getRecordBymemberId(id);

            Member member = memberBiz.getById(id);
            for(Record record : records)
            {
                long bookId = record.getBookId();
                Book book = bookDao.getById(bookId);
                record.setBook(book);
                record.setMember(member);

                long day = member.getType().getKeepDay();
                java.sql.Date backdate = DateHelper.getNewDate(record.getRentDate(),day);
                record.setBackDate(backdate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;

    }

   /**
           * 借阅:
            * 1.借一本数： record表添加一行信息(recordDao,insert) 1
            * 2.这本书的数量-1 (bookDao,update) 1
            * 3.会员信息表中 ,更改余额( memberDao,update) 1
            * 要么全部成功，要么全部失败(一个业务(事务处理))
            * 前提:用同一个connection对象(如何?)
     * @param memberId
     * @param bookIdList
     * @param userId
     * @return 0:操作失败  1:操作成功
     */
    public int add(long memberId, List<Long> bookIdList, long userId){
        // 启动事务
        try {
            DBHelper.beginTransaction();
            double total = 0;
            for(long id : bookIdList)
            {
                Book book = bookDao.getById(id);

                double price = book.getPrice();
                double regprice = price*0.3f;
                total += regprice;
                recordDao.add(memberId,id,regprice,userId);

                bookDao.modify(id,-1);

            }
           memberDao.modifyBanlance(memberId,0-total);
            // 事务结束
            DBHelper.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DBHelper.rollbackTransaction();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return 0;
            }
        }

        return 1; // 成功
    }

    public int modify(long id){
        int count = 0;
        try {
            count = recordDao.modify(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int modify(long memberId, List<Long> recordIds, long userId)
    {
        try {
            DBHelper.beginTransaction();
            double total = 0;
            Member member = memberBiz.getById(memberId);
            for(long recordId : recordIds)
            {
                Record record = recordDao.getById(recordId);

                java.sql.Date date = DateHelper.getNewDate(record.getRentDate(), member.getType().getKeepDay());
                java.util.Date currentDate = new java.util.Date();
                int day = 0;
                if(currentDate.after(date)){
                    day = DateHelper.getSpan(currentDate, date);
                    day = Math.max((int)record.getDeposit(), day);
                }
                total += record.getDeposit() - day;
                recordDao.modify((double) day,userId,recordId);
                bookDao.modify(record.getBookId(),1);
            }
            memberDao.modifyBanlance(memberId,total);
            DBHelper.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DBHelper.rollbackTransaction();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return 0;
        }
        return 1;

    }
    public List<Map<String, Object>> query(int typeId, String keyword)
    {
        List<Map<String, Object>> rows = null;
        try {
            rows = recordDao.query(typeId,keyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;

    }
}
