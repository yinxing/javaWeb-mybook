package com.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    /**
     * 获取上传图片名
     * @return
     */
    public static String getImageName(){
        SimpleDateFormat  sdf= new SimpleDateFormat("yyyyMMddHHmmssS");
        return sdf.format(new Date());
    }

    public  static java.sql.Date getNewDate(java.sql.Date date, long amount)
    {
        long mills = date.getTime();
        mills += amount*24*60*60*1000;
        java.sql.Date backDate = new java.sql.Date(mills);
        return backDate;
    }
    public static int getSpan(Date date1, Date date2){
        long span = date1.getTime() - date2.getTime();
        int day = (int)span / 1000 / 60 / 60 / 24;
        return Math.abs(day);
    }

    public static void main(String[] args) {
     //   System.out.println(getImageName());
        java.sql.Date now = java.sql.Date.valueOf("2022-6-1");
        System.out.println(getNewDate(now,10));

    }

}
