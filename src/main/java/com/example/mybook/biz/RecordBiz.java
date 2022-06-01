package com.example.mybook.biz;

import com.example.mybook.bean.Record;
import com.example.mybook.dao.RecordDao;

import java.sql.SQLException;
import java.util.List;

public class RecordBiz {
    RecordDao recordDao = new RecordDao();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;

    }
}
