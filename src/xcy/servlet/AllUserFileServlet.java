package xcy.servlet;

import xcy.dao.FileDao;
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
 * 指定用户所有文件
 */
@WebServlet("/AllUserFileServlet")
public class AllUserFileServlet extends HttpServlet {
    List<UserFile> list = new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileDao fileDao = new FileDao();
        int uid = Integer.parseInt(req.getParameter("id"));
        list = fileDao.findAllUserFile(uid);
        req.setAttribute("filelist",list);
        req.getRequestDispatcher("userfile.jsp").forward(req,resp);
    }
}
