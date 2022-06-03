package com.example.mybook.action;

import com.example.mybook.bean.User;
import com.example.mybook.biz.UserBiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user.let") // /: 项目的根目录
public class UserServlet extends HttpServlet {

    // 构建UserBiz的对象
    UserBiz userBiz = new UserBiz();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * /user.let?type=login 登录
     * /user.let?type=exit 安全退出
     * /user.let?type=modifyPwd 修改密码
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        // 1.判读用户请求的类型为login
        String method = req.getParameter("type");
        switch(method){
            case "login":
                // 2.从login.html中获取用户名和密码
                String name = req.getParameter("name");
                String pwd = req.getParameter("pwd");
                String usercode = req.getParameter("valcode");

                //从session中取验证码进行判断  测试时不用
                String code = session.getAttribute("code").toString();
                if(!code.equalsIgnoreCase(usercode))
                {
                    out.println(("<script>alert('验证码不正确！');location.href='login.html';</script>"));
                    return ;
                }

                // 3.调用UserBiz的getuser方法, 根据用户名和密码获取对应的用户对象

                User user = userBiz.getUser(name, pwd);
                // 4.判断用户对象是否为null
                if(user == null)
                {
                    // 4.1 如果是null表示用户名或密码不正确,提示错误,回到登录页面
                    out.println(("<script>alert('用户名或密码不存在');location.href='login.html';</script>"));

                }
                else{
                    // 4.2 非空,表示登录正确,将用户对象保存到session中,提示登录成功后,将页面跳转到index.jsp,保存session
                    session.setAttribute("user",user);
                    out.println("<script>alert('登录成功');location.href='index.jsp';</script>");
                }

                break;
            case "exit":
                session.invalidate();
                out.println("<script>parent.window.location.href='login.html';</script>");

                break;
            // 修改密码
            case "modifyPwd":
                String oldPwd = req.getParameter("oldpwd");
                String rightpwd = ((User)session.getAttribute("user")).getPwd();
                if(!oldPwd.equals(rightpwd))
                {
                    out.println("<script>alert('原密码错误');location.href='set_pwd.jsp';</script>");
                    return;
                }
                String newPwd = req.getParameter("newpwd2");
                long id = ((User)session.getAttribute("user")).getId();

                int count = userBiz.modifyPwd(id,newPwd);

                if(count > 0){
                    out.println("<script>alert('密码修改成功');parent.window.location.href='login.html';</script>");
                }else{
                    out.println("<script>alert('修改失败');</script>");
                }
                break;
        }
    }
}
