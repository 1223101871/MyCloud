package xcy.servlet;

import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Encoder;
import xcy.dao.FileDao;
import xcy.model.UserFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 下载
 */
@WebServlet("/DownLoadServlet")
public class DownLoadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        FileDao fileDao = new FileDao();
        UserFile file;
        String idStr = req.getParameter("id");
        String action = req.getParameter("action");
        int id = Integer.parseInt(idStr);
        file = fileDao.findFile(id);
        String filePath = file.getPath();
        String fileName = file.getFilename();

        try {
            fileName = filenameEncoding(fileName, req);
//            File f = new File(filePath + "\\" + fileName);
//            if (!f.exists()) {
//                System.out.println("你要下载的资源已经被删除");
//                fileDao.deleteFile(id);
//                return;
//            }
            String contentType = this.getServletContext()
                    .getMimeType(fileName);//通过文件名称获取MIME类型
            String contentDisposition = "attachment;filename=" + fileName;
            // 一个流
            FileInputStream input = null;
            input = new FileInputStream(filePath);
            System.out.println(input);
            //设置头
            resp.setHeader("Content-Type", contentType);
            resp.setHeader("Content-Disposition", contentDisposition);
            // 获取绑定了响应端的流
            ServletOutputStream output = resp.getOutputStream();
            IOUtils.copy(input, output);//把输入流中的数据写入到输出流中。
            input.close();
            file.setCount(file.getCount() + 1);
            fileDao.updateDown(file);
            if("allfile".equals(action)){
                req.getRequestDispatcher("allfile.jsp").forward(req,resp);
            }
            else if("allshare".equals(action)){
                req.getRequestDispatcher("allshare.jsp").forward(req,resp);
            }
            else if("myshare".equals(action)){
                req.getRequestDispatcher("myshare.jsp").forward(req,resp);
            }
            else if("mydisk".equals(action)){
                req.getRequestDispatcher("mydisk.jsp").forward(req,resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            resp.setContentType("text/html; charset=utf8");
            try {
                fileDao.deleteFile(id);
                resp.getWriter().print("下载失败,文件或已丢失!");

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ServletException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    // 用来对下载的文件名称进行编码的！
    public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
        String agent = request.getHeader("User-Agent"); //获取浏览器
        if (agent.contains("Firefox")) {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?"
                    + base64Encoder.encode(filename.getBytes("utf-8"))
                    + "?=";
        } else if (agent.contains("MSIE")) {
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
