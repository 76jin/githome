package kr.jsp.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    public static Connection getConnection() {
        Connection connection = null;
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            
            String jdbcDriver = "jdbc:mysql://localhost:3306/jspstudydb?" + 
                    "useUnicode=true&characterEncoding=utf8";
            String dbUser = "jspstudy01";
            String dbPassword = "jspstudy01";
            connection = (Connection) DriverManager.getConnection(jdbcDriver, dbUser, dbPassword);
//      } catch (ClassNotFoundException e) {
//          e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return connection;
    }
}
