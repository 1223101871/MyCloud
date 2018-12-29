package xcy.util;

import java.sql.*;

/**
 * 数据库连接、关闭
 */
public class DButil {
    private static final String URL = "jdbc:mysql://localhost:3306/icloud?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }
    public static void closeConnection(ResultSet rs, PreparedStatement psmt,Connection conn){
        try {
            if(rs != null){
                rs.close();
            }
            if(psmt != null){
                psmt.close();
            }
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
