package xcy.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import xcy.dao.FileDao;
import xcy.dao.PictureDao;
import xcy.model.Picture;
import xcy.model.User;
import xcy.model.UserFile;
import xcy.util.StringUtl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 上传文件到本地
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String savePath = "C:/Users/夏晨阳/Desktop/文档/java web/MyCloud";
        String tempPath = "C:/Users/夏晨阳/Desktop/文档/java web/MyCloud/tmp";
        String imgSavePath =  this.getServletContext().getRealPath("/WEB-INF/img");
        String action = req.getParameter("action");
        File tempFile = new File(tempPath);
        User user = (User)req.getSession().getAttribute("user");
        List<UserFile> list = new ArrayList<>();
        FileDao fileDao = new FileDao();
        PictureDao pictureDao = new PictureDao();
        List<Picture> pictures = new ArrayList<>();
        if(!tempFile.exists()){
            tempFile.mkdir();
        }

        DiskFileItemFactory factory = new DiskFileItemFactory(1024*1024*10,tempFile);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");

        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            for(FileItem item : fileItems) {
                if(item.isFormField()){

                }
                else {
                    String fileName = item.getName();
                    if(fileName == null || fileName.trim().equals("")){
                        continue;
                    }
                    int index = fileName.lastIndexOf("\\");
                    if(index != -1) {
                        fileName = fileName.substring(index+1);
                    }
                    String root;
                    if("file".equals(action)){
                         root = savePath+user.getUname();
                    }else{
                        root = imgSavePath;
                    }
                    long size = item.getSize();
                    String sizeString = StringUtl.toConversion(size);
                    Timestamp ts = new Timestamp(new Date().getTime());
                    File file = new File(root,fileName);

                    //解决文件同名
                    int cnt = 1;
                    while(file.exists()){
                        StringBuffer sb = new StringBuffer(fileName);
                        sb.insert(sb.lastIndexOf("."), "("+cnt+")");
                        file = new File(root,sb.toString());
                        cnt++;

                    }
                    if(!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    try {

                        item.write(file);
                        //上传成功，数据库保存记录
                        if("file".equals(action)){
                            UserFile userFile = new UserFile();
                            userFile.setCreateTime(ts);
                            userFile.setFilename(file.getName());
                            userFile.setFileSize(sizeString);
                            userFile.setIsShared(0);
                            userFile.setOwnerId(user.getUid());
                            userFile.setPath(file.getAbsolutePath());
                            fileDao.save(userFile);
                            list = fileDao.searchMyDiskFile(user.getUid(),"");
                            req.setAttribute("filelist",list);
                            req.getRequestDispatcher("mydisk.jsp").forward(req,resp);
                        }else{
                            Picture picture = new Picture();
                            picture.setCreateTime(ts);
                            picture.setFilename(file.getName());
                            picture.setFileSize(sizeString);
                            picture.setIsShared(0);
                            picture.setOwnerId(user.getUid());
                            picture.setPath(file.getAbsolutePath());
                            pictureDao.save(picture);
                            pictures = pictureDao.searchMyPicture(user.getUid(),"");
                            req.setAttribute("piclist",pictures);
                            req.getRequestDispatcher("picupload.jsp").forward(req,resp);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
