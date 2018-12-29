package xcy.servlet;

import xcy.dao.UserDao;
import xcy.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 登录
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uname = req.getParameter("uname");
        String upwd = req.getParameter("upwd");
      //  String rmUp = req.getParameter("rmUp");
        User user = new User();
        user.setUname(uname);
        user.setUpwd(upwd);
        UserDao userDao = new UserDao();
        if(userDao.login(user)){

            req.setAttribute("user",user);
            req.setAttribute("uname",uname);
            int uid = userDao.findUserId(user.getUname());
            user.setUid(uid);
            req.getSession().setAttribute("user",user);
//            HttpSession session = req.getSession();
//            session.setAttribute("Suname",uname);
//            if("1".equals(rmUp)){
//
//                //构建cookie对象
//                Cookie cuname = new Cookie("cuname",uname);
//                Cookie cupwd = new Cookie("cupwd",upwd);
//
//                //向响应头添加cookie
//                resp.addCookie(cuname);
//                resp.addCookie(cupwd);
//            }


            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }else {
            resp.sendRedirect("login.jsp");
        }
    }
}
