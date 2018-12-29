package xcy.servlet;

import xcy.dao.FileDao;
import xcy.model.Page;
import xcy.model.UserFile;
import xcy.util.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 所有共享
 */
@WebServlet("/AllShareServlet")
public class AllShareServlet extends HttpServlet {
    List<UserFile> list = new ArrayList<>();
    FileDao fileDao = new FileDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int currentPage = 1;
        Page p = PageUtil.allShareFindPage(currentPage,10,fileDao,"");
        list = p.getFiles();
        req.setAttribute("filelist", list);
        req.setAttribute("page",p);
        req.getRequestDispatcher("allshare.jsp?page="+currentPage).forward(req,resp);

    }
}
