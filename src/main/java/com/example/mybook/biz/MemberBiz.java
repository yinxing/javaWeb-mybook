package com.example.mybook.biz;

import com.example.mybook.bean.Member;
import com.example.mybook.bean.MemberType;
import com.example.mybook.dao.MemberDao;
import com.example.mybook.dao.MemberTypeDao;

import java.sql.SQLException;
import java.util.List;

public class MemberBiz {
    MemberDao memberDao = new MemberDao();
    public int add(String name, String pwd, long typeId, double balance,
                   String tel, String idNumber){
        int count = 0;
        try {
            count = memberDao.add(name, pwd, typeId, balance, tel, idNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int modify(long id,String name, long typeId, double balance,
                   String tel, String idNumber){
        int count = 0;
        try {
            count = memberDao.modify(id,name, typeId, balance, tel, idNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public int remove(long id) throws Exception{
        Member member = memberDao.getById(id);
        if(member.getBalance() > 0)
        {
            throw new Exception("此会员余额大于0，无法删除！");
        }
        if(memberDao.exists(id)){
            throw new Exception("此会员有子信息，删除失败");
        }
        int count = 0;
        try {
            count = memberDao.remove(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int modifyBanlance( double money, String idNumber){
        int count = 0;
        try {
            count = memberDao.modifyBanlance(money,idNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public List<Member> getAll(){
        MemberTypeDao memberTypeDao = new MemberTypeDao();
        List<Member> members = null;
        try {
            members = memberDao.getAll();
            for(Member member : members)
            {
                MemberType type = memberTypeDao.getById(member.getTypeId());
                member.setType(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    public Member getById(long id){
        try {
            return memberDao.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Member getByIdNumber(String idNumber){
        MemberTypeDao memberTypeDao = new MemberTypeDao();
        Member member = null;
        try {
            member =  memberDao.getByIdNumber(idNumber);
            MemberType memberType = memberTypeDao.getById(member.getTypeId());
            member.setType(memberType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    public static void main(String[] args) {
       //System.out.println(new MemberBiz().modify(6,"fa", "dfas",1,123,"1312","1231232"));
    }

}
