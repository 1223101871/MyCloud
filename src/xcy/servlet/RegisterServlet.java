package xcy.servlet;


import xcy.dao.UserDao;
import xcy.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注册
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uname = req.getParameter("uname");
        String upwd = req.getParameter("upwd");
       // String ugenner = req.getParameter("ugenner");
       // String upwd2 = req.getParameter("upwd2");

            User user = new User();
            user.setUname(uname);
            user.setUpwd(upwd);
            //   user.setUgenner(ugenner);
            System.out.println(user.getUname());
            UserDao userDao = new UserDao();

            if(userDao.register(user)){
                req.getRequestDispatcher("login.jsp").forward(req,resp);
            }else{
//                resp.sendRedirect("login.jsp");
            }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
