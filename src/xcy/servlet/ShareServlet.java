package xcy.servlet;

import xcy.dao.FileDao;
import xcy.model.User;
import xcy.model.UserFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 分享
 */
@WebServlet("/ShareServlet")
public class ShareServlet extends HttpServlet {
    List<UserFile> list = new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String action = req.getParameter("action");
        User user = (User)req.getSession().getAttribute("user");
        FileDao fileDao = new FileDao();
        UserFile file = fileDao.findFile(id);
        if("takeshare".equals(action)){
            if(fileDao.updateShareStatus(file)>0){
                list = fileDao.searchMyDiskFile(user.getUid(),"");
                req.setAttribute("filelist",list);
                req.getRequestDispatcher("mydisk.jsp").forward(req,resp);
            }
        }

    }
}
