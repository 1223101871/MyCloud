package xcy.dao;

import xcy.model.Page;
import xcy.model.UserFile;
import xcy.util.DButil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件相关操作
 */
public class FileDao {
    Connection connection;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    private UserDao user = new UserDao();
    private List<UserFile> list = new ArrayList<>();

    public List AddUserFile(ResultSet set) {

        try {
            list.clear();
            while (set.next()) {
                UserFile userFile = new UserFile();
                userFile.setId(set.getInt(1));
                userFile.setFilename(set.getString(2));
                userFile.setPath(set.getString(3));
                userFile.setCreateTime(set.getTimestamp(4));
                userFile.setFileSize(set.getString(7));
                userFile.setCount(set.getInt(8));
                userFile.setIsShared(set.getInt(5));
                userFile.setOwnerId(set.getInt(6));
                userFile.setOwner(user.searchUser(set.getInt(6)));
                list.add(userFile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }


    public void deleteFile(int fileId) {
        connection = DButil.getConnection();
        String sql = "DELETE FROM file WHERE id = ?";
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1, fileId);
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.closeConnection(rs, psmt, connection);
        }
    }

    public List searchShareFile(String filename,int page,int count) {
        String sql = "SELECT * FROM file WHERE isshared=1";
        connection = DButil.getConnection();
        sqlJoint(sql,filename,page,count);
        return list;
    }

    public List searchAllFile(String filename,int page,int count) {
        String sql = "SELECT * FROM file WHERE 1=1";
        connection = DButil.getConnection();
        sqlJoint(sql,filename,page,count);
        return list;
    }


    public List<UserFile> searchMyShare(int uid, String filename) {
        connection = DButil.getConnection();
        String sql = "SELECT * from file where ownerid = ? AND isshared=1";
        try {
            if (!"".equals(filename) && filename != null) {
                sql += " AND filename LIKE '%" + filename + "%'";
            }
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1, uid);
            rs = psmt.executeQuery();
            list = AddUserFile(rs);
            System.out.println(list.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.closeConnection(rs, psmt, connection);
        }
        return list;
    }

    public List<UserFile> searchMyDiskFile(int uid, String filename) {
        connection = DButil.getConnection();
        String sql = "SELECT * from file where ownerid = ?";
        try {
            if (!"".equals(filename) && filename != null) {
                sql += " AND filename LIKE '%" + filename + "%'";
            }
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1, uid);
            rs = psmt.executeQuery();
            list = AddUserFile(rs);
            System.out.println("mydisk:" + list.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.closeConnection(rs, psmt, connection);
        }
        return list;
    }

    public void save(UserFile userFile) {
        String sql = "insert into file values(NULL,?,?,?,?,?,?,?,NULL)";
        connection = DButil.getConnection();
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setString(1, userFile.getFilename());
            psmt.setString(2, userFile.getPath());
            psmt.setTimestamp(3, userFile.getCreateTime());
            psmt.setInt(4, userFile.getIsShared());
            psmt.setInt(5, userFile.getOwnerId());
            psmt.setString(6, userFile.getFileSize());
            psmt.setInt(7, userFile.getCount());
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.closeConnection(rs, psmt, connection);
        }

    }

    public UserFile findFile(int id) {
        UserFile userFile = new UserFile();
        connection = DButil.getConnection();
        String sql = "SELECT * from file where id = ?";
        try {

            psmt = connection.prepareStatement(sql);
            psmt.setInt(1, id);
            rs = psmt.executeQuery();
            while (rs.next()) {
                userFile.setId(rs.getInt(1));
                userFile.setFilename(rs.getString(2));
                userFile.setPath(rs.getString(3));
                userFile.setCreateTime(rs.getTimestamp(4));
                userFile.setFileSize(rs.getString(7));
                userFile.setCount(rs.getInt(8));
                userFile.setIsShared(rs.getInt(5));
                userFile.setOwnerId(rs.getInt(6));
                userFile.setOwner(user.searchUser(rs.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.closeConnection(rs, psmt, connection);
        }
        return userFile;
    }

    public void updateDown(UserFile userFile) {
        String sql = "UPDATE file set count=? where  id=?";
        connection = DButil.getConnection();
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1, userFile.getCount());
            psmt.setInt(2, userFile.getId());
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeConnection(rs,psmt,connection);
        }

    }

    public int updateShareStatus(UserFile file) {
        int isShared = file.getIsShared();
        if (isShared == 0) {
            isShared = 1;
        }else {
            isShared = 0;
        }
        file.setIsShared(isShared);
        String sql = "UPDATE file set isshared=? where id=?";
        connection = DButil.getConnection();
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1, file.getIsShared());
            psmt.setInt(2, file.getId());
            return  psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeConnection(rs,psmt,connection);
        }
       return 0;
    }

    public List findAllUserFile(int uid){
        String sql = "SELECT * FROM file where ownerid=?";
        connection = DButil.getConnection();
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1, uid);
            rs = psmt.executeQuery();
            list = AddUserFile(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeConnection(rs,psmt,connection);
        }
        return list;
    }

    public List findFileList(int page ,int count){
        String sql = "SELECT *FROM file LIMIT ?,?";
        connection = DButil.getConnection();
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1,(page-1)*count);
            psmt.setInt(2,count);
            rs = psmt.executeQuery();
            list = AddUserFile(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeConnection(rs,psmt,connection);
        }

        return list;
    }

    public int count(String filename){
        int count = 0;
        String sql = "SELECT count(*) FROM file where 1=1";
        connection = DButil.getConnection();
        try {
            if (!"".equals(filename) && filename != null) {
                sql += " AND filename LIKE '%" + filename + "%'";
            }
            psmt = connection.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeConnection(rs,psmt,connection);
        }
        return count;
    }

    public void sqlJoint(String sql,String filename,int page,int count){
        try {
            if (!"".equals(filename) && filename != null) {
                sql += " AND filename LIKE '%" + filename + "%'";
            }
            if(count!=0){
                sql += " LIMIT "+(page-1)*count+","+count;
            }
            psmt = connection.prepareStatement(sql);
            rs = psmt.executeQuery();
            list = AddUserFile(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.closeConnection(rs, psmt, connection);
        }
    }

}
