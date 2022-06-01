package com.example.mybook.action;

import com.example.mybook.bean.Type;
import com.example.mybook.biz.TypeBiz;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/type.let")
public class TypeServlet extends HttpServlet {
    TypeBiz typeBiz = new TypeBiz();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * type.let?type=add:添加
     * type.let?type=modifypre:修改预备(req --> 转发 --> type_modify.jsp)
     * type.let?type=modify:修改(表单）
     * type.let?type=remove:删除
     * type_list.jsp 查询(数据放在application)
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out =resp.getWriter();
        ServletContext application = req.getServletContext();

        String type = req.getParameter("type");
        switch(type){
            case"add":
                add(req,resp,out,application);
                break;
            case"modifypre":
                modifypre(req,resp,out,application);
                break;
            case"modify":
                modify(req,resp,out,application);
                break;
            case"remove":
                remove(req,resp,out,application);
                break;
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp, PrintWriter out, ServletContext application) {
        //1.从表单中获取名字和父类型
        String typeName = req.getParameter("typeName");
        long parentId = Long.parseLong(req.getParameter("parentType"));
        // biz添加方法
        int count = typeBiz.add(typeName,parentId);
        // 更新application中types
        if(count > 0)
        {
            List<Type> types = typeBiz.getAll();
            application.setAttribute("types",types);
            out.println("<script>alert('添加成功');location.href='type_list.jsp';</script>");
        }else{
            out.println("<script>alert('添加失败');location.href='type_add.jsp';</script>");
        }


    }

    /**
     * type_list.jsp --> type.let?type=modifypre --> 转发 --> type_modify.jsp
     * @param req
     * @param resp
     * @param out
     * @param application
     */
    private void modifypre(HttpServletRequest req, HttpServletResponse resp, PrintWriter out, ServletContext application) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        Type type = typeBiz.getById(id);

        req.setAttribute("type",type);
        req.getRequestDispatcher("type_modify.jsp").forward(req,resp);


    }
    private void modify(HttpServletRequest req, HttpServletResponse resp, PrintWriter out, ServletContext application) {
        long id = Long.parseLong(req.getParameter("typeId"));
        String name = req.getParameter("typeName");
        long parentId = Long.parseLong(req.getParameter("parentType")) ;

        int count = typeBiz.modify(id,name,parentId);
        if(count > 0)
        {
            List<Type> types = typeBiz.getAll();
            application.setAttribute("types",types);
            out.println("<script>alert('修改成功');location.href='type_list.jsp';</script>");
        }else{
            out.println("<script>alert('修改失败');location.href='type_list.jsp';</script>");
        }



    }
    private void remove(HttpServletRequest req, HttpServletResponse resp, PrintWriter out, ServletContext application) {
        long id  = Long.parseLong(req.getParameter("id"));
        try{
            int count = typeBiz.remove(id);
            if(count > 0)
            {
                List<Type> types = typeBiz.getAll();
                application.setAttribute("types",types);
                out.println("<script>alert('删除成功');location.href='type_list.jsp';</script>");
            }else{
                out.println("<script>alert('删除失败');location.href='type_list.jsp';</script>");
            }
        }catch(Exception e){
            out.println("<script>alert('"+ e.getMessage()+"');location.href='type_list.jsp';</script>");
        }
    }
}
