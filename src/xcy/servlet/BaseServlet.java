package xcy.servlet;

import com.sun.deploy.net.HttpRequest;
import xcy.dao.FileDao;
import xcy.dao.UserDao;
import xcy.model.Page;
import xcy.model.User;
import xcy.model.UserFile;
import xcy.util.PageUtil;
import xcy.util.StringUtl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行各种动作
 * 文件删除、查找 用户删除、查找
 * method：想要执行的操作
 * action:发出动作的jsp名
 */
@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        String action = req.getParameter("action");
        FileDao fileDao = new FileDao();
        UserDao userDao = new UserDao();
        List<UserFile> fileList = new ArrayList<>();
        User user = (User) req.getSession().getAttribute("user");
        List<User> userList = new ArrayList<>();
        if ("search".equals(method)) {
            String filenanme = req.getParameter("filename");

            if ("allshare".equals(action)) {
                allSharePageOperation(req,resp,fileList,fileDao,filenanme);
            } else if ("allfile".equals(action)) {
                pageOperation(req,resp,fileList,fileDao,filenanme);
            } else if ("myshare".equals(action)) {
                fileList = fileDao.searchMyShare(user.getUid(), filenanme);
                req.setAttribute("filelist", fileList);
                req.getRequestDispatcher("myshare.jsp").forward(req, resp);
            } else if ("mydisk".equals(action)) {
                fileList = fileDao.searchMyDiskFile(user.getUid(), filenanme);
                req.setAttribute("filelist", fileList);
                req.getRequestDispatcher("mydisk.jsp").forward(req, resp);
            } else if ("userlist".equals(action)) {
                userList = userDao.searchAllUser(filenanme);
                req.setAttribute("userlist", userList);
                req.getRequestDispatcher("userlist.jsp").forward(req, resp);
            }

        } else if ("delete".equals(method)) {
            String[] ids = req.getParameterValues("ids");
            System.out.println("ids.length:" + ids.length);
            if (ids.length != 0) {
                int[] fileIds = StringUtl.toInter(ids);
                for (int i = 0; i < fileIds.length; i++) {
                    fileDao.deleteFile(fileIds[i]);
                }

                if ("allshare".equals(action)) {
                    allSharePageOperation(req,resp,fileList,fileDao,"");
                } else if ("allfile".equals(action)) {
                    pageOperation(req,resp,fileList,fileDao,"");
                } else if ("myshare".equals(action)) {
                    fileList = fileDao.searchMyShare(user.getUid(), "");
                    req.setAttribute("filelist", fileList);
                    req.getRequestDispatcher("myshare.jsp").forward(req, resp);
                } else if ("mydisk".equals(action)) {
                    fileList = fileDao.searchMyDiskFile(user.getUid(), "");
                    req.setAttribute("filelist", fileList);
                    req.getRequestDispatcher("mydisk.jsp").forward(req, resp);
                }else if ("userfile".equals(action)) {
                    fileList = fileDao.searchMyShare(user.getUid(), "");
                    req.setAttribute("filelist", fileList);
                    req.getRequestDispatcher("userfile.jsp").forward(req, resp);
                }


            }

        }
        else if ("deleteuser".equals(method)) {
            String[] ids = req.getParameterValues("ids");
            System.out.println("ids.length:" + ids.length);
            if (ids.length != 0) {
                int[] userIds = StringUtl.toInter(ids);
                for (int i = 0; i < userIds.length; i++) {
                    userDao.deleteUser(userIds[i]);
                }
                userList = userDao.searchAllUser("");
                req.setAttribute("userlist", userList);
                req.getRequestDispatcher("userlist.jsp").forward(req, resp);
            }
        }
        else if("page".equals(method)){
             if("allfile".equals(action)){
                 pageOperation(req,resp,fileList,fileDao,"");

             }
             if("allshare".equals(action)){
                 allSharePageOperation(req,resp,fileList,fileDao,"");
             }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * 分页操作
     * @param req
     * @param resp
     * @param fileList  获取的文件列表
     * @param fileDao
     * @param filename  “”或查询的文件名
     */
    public void pageOperation(HttpServletRequest req,HttpServletResponse resp,List fileList,FileDao fileDao,String filename){
        String page = req.getParameter("page");
        if(page!=null&&!"".equals(page)){
            int currentPage = Integer.parseInt(page);
            System.out.println("currentPage:"+currentPage);
            Page p = PageUtil.allFileFindPage(currentPage,10,fileDao,filename);
            fileList = p.getFiles();
            req.setAttribute("filelist", fileList);
            req.setAttribute("page",p);
            try {
                req.getRequestDispatcher("allfile.jsp").forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void allSharePageOperation(HttpServletRequest req,HttpServletResponse resp,List fileList,FileDao fileDao,String filename){
        String page = req.getParameter("page");
        if(page!=null&&!"".equals(page)){
            int currentPage = Integer.parseInt(page);
            System.out.println("currentPage:"+currentPage);
            Page p = PageUtil.allShareFindPage(currentPage,10,fileDao,filename);
            fileList = p.getFiles();
            req.setAttribute("filelist", fileList);
            req.setAttribute("page",p);
            try {
                req.getRequestDispatcher("allshare.jsp").forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
