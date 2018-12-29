package xcy.servlet;

import xcy.dao.FileDao;
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
 * 我的共享
 */
@WebServlet("/MyShareServlet")
public class MyShareServlet extends HttpServlet {
    List<UserFile> list = new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileDao fileDao = new FileDao();
        User user = (User)req.getSession().getAttribute("user");

        if(user!=null){
            list = fileDao.searchMyShare(user.getUid(),"");
//        System.out.println(list.size());
            req.setAttribute("filelist",list);
            req.getRequestDispatcher("myshare.jsp").forward(req,resp);
        }


    }
}
