package xcy.servlet;

import xcy.dao.UserDao;
import xcy.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        UserDao userDao = new UserDao();
        String pwd = req.getParameter("password");
        String pwd1 = req.getParameter("password1");
        int uid = user.getUid();
        if(pwd == pwd1){

        }else{
            if(userDao.checkPwd(pwd,uid)){
                userDao.updateUserPwd(uid,pwd1);
            }
        }
    }
}
