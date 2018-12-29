package xcy.dao;

import xcy.model.Picture;
import xcy.model.User;
import xcy.model.UserFile;
import xcy.util.DButil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PictureDao {
    Connection connection;
    PreparedStatement psmt= null;
    ResultSet rs = null;
    private UserDao user = new UserDao();
    private List<Picture> list = new ArrayList<>();
    public void save(Picture picture) {
        String sql = "insert into picture values(NULL,?,?,?,?,?,?,?)";
        connection = DButil.getConnection();
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setString(1, picture.getFilename());
            psmt.setString(2, picture.getPath());
            psmt.setTimestamp(3, picture.getCreateTime());
            psmt.setInt(4, picture.getIsShared());
            psmt.setInt(5, picture.getOwnerId());
            psmt.setString(6, picture.getFileSize());
            psmt.setInt(7, picture.getCount());
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.closeConnection(rs, psmt, connection);
        }

    }

    public List<Picture> searchMyPicture(int uid, String filename) {
        connection = DButil.getConnection();
        String sql = "SELECT * from picture where ownerid = ?";
        try {
            if (!"".equals(filename) && filename != null) {
                sql += " AND filename LIKE '%" + filename + "%'";
            }
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1, uid);
            rs = psmt.executeQuery();
            list = AddPicture(rs);
            System.out.println("mydisk:" + list.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.closeConnection(rs, psmt, connection);
        }
        return list;
    }

    public List AddPicture(ResultSet set) {

        try {
            list.clear();
            while (set.next()) {
                Picture picture = new Picture();
                picture.setId(set.getInt(1));
                picture.setFilename(set.getString(2));
                picture.setPath(set.getString(3));
                picture.setCreateTime(set.getTimestamp(4));
                picture.setFileSize(set.getString(7));
                picture.setCount(set.getInt(8));
                picture.setIsShared(set.getInt(5));
                picture.setOwnerId(set.getInt(6));
                picture.setOwner(user.searchUser(set.getInt(6)));
                list.add(picture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

}
