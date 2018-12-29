package xcy.servlet;

import xcy.dao.FileDao;
import xcy.dao.UserDao;
import xcy.model.User;
import xcy.model.UserFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户列表
 */
@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
    List<User> list = new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        list = userDao.searchAllUser("");
        req.setAttribute("userlist",list);
        req.getRequestDispatcher("userlist.jsp").forward(req,resp);

    }
}
