package xcy.dao;


import xcy.model.User;
import xcy.model.UserFile;
import xcy.util.DButil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户相关操作
 */
public class UserDao {

    Connection connection;
    PreparedStatement  psmt= null;
    ResultSet rs = null;
    private List<User> list = new ArrayList<>();
    public boolean login(User user) {
        connection = DButil.getConnection();
        boolean flag = false;
        String sql = "select * from user where username=? and password=?";
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setString(1,user.getUname());
            psmt.setString(2,user.getUpwd());
            rs = psmt.executeQuery();
            while (rs.next()){
                flag = true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeConnection(rs,psmt,connection);
        }
        return flag;
    }

    public boolean register(User user){
        connection = DButil.getConnection();
        String sql = "SELECT * FROM user WHERE username = ?";
        String sql2 = "insert into user values(0,?,?,0)";
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setString(1,user.getUname());
            rs = psmt.executeQuery();
            if(rs.next()){
                System.out.println("用户名重复");
                return false;
            }
            else {
                psmt = connection.prepareStatement(sql2);
                psmt.setString(1,user.getUname());
                psmt.setString(2,user.getUpwd());
                psmt.executeUpdate();
                System.out.println("注册成功");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeConnection(rs,psmt,connection);
        }
        return true;

    }


     public User searchUser(int uid){
        connection = DButil.getConnection();
        String sql = "SELECT * from user where id = ?";
         try {
             psmt = connection.prepareStatement(sql);
             psmt.setInt(1,uid);
             rs = psmt.executeQuery();
             while (rs.next()){
                 if(rs.getInt(1)==uid)
                 return new User(rs.getInt(1),rs.getString(2),rs.getString(3));
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             DButil.closeConnection(rs,psmt,connection);
         }
         return null;

     }

     public int findUserId(String uname){
         connection = DButil.getConnection();
         String sql = "SELECT * from user where username = ?";
         try {
             psmt = connection.prepareStatement(sql);
             psmt.setString(1,uname);
             rs = psmt.executeQuery();
             while (rs.next()){
                 return rs.getInt(1);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             DButil.closeConnection(rs,psmt,connection);
         }
         return 0;
     }

     public List<User> searchAllUser(String userName){
         connection = DButil.getConnection();
         String sql = "SELECT * from user where 1=1";
         try {
             if(!"".equals(userName) && userName!=null){
                 sql += " AND username LIKE '" + userName +"%'";
             }
             psmt = connection.prepareStatement(sql);
             rs = psmt.executeQuery();
             list = AddUser(rs);
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             DButil.closeConnection(rs,psmt,connection);
         }
         return list;
     }
    public List AddUser(ResultSet set) {

        try {
            while(set.next()){
               User user = new User();
               user.setUid(set.getInt(1));
               user.setUname(set.getString(2));
               user.setUpwd(set.getString(3));
               list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    public void deleteUser(int uid){
        connection = DButil.getConnection();
        String sql = "DELETE FROM user WHERE id = ?";
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1,uid);
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeConnection(rs,psmt,connection);
        }
    }


    public void updateUserPwd(int uid,String pwd){
        connection = DButil.getConnection();
        String sql = "UPDATE user set password = ? WHERE id = ?";
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setString(1,pwd);
            psmt.setInt(2,uid);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeConnection(rs,psmt,connection);
        }
    }

    public boolean checkPwd(String pwd1,int uid){
        connection = DButil.getConnection();
        String sql = "SELECT password FROM user WHERE id = ?";
        String password = null;
        try {
            psmt = connection.prepareStatement(sql);
            psmt.setInt(1,uid);
            rs = psmt.executeQuery();
            while (rs.next()){
                password = rs.getString(1);
            }
            if(pwd1.equals(password)){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeConnection(rs,psmt,connection);
        }
        return false;
    }

}
