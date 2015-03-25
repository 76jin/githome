package kr.jsp.study.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kr.jsp.study.jdbc.ConnectionProvider;
import kr.jsp.study.jdbc.JDBCUtil;
import kr.jsp.study.vo.User;

public class UserDao {

    public int insert(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;
        
        // insert into MEMBER(MEMBERID, PASSWORD, NAME, EMAIL) values('madvirus', '1234', '최범균', mad@test.com);
        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement("insert into MEMBER values(?,?,?,?)");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.close(pstmt);
            JDBCUtil.close(conn);
        }
        System.out.println("result inserted:" + result);
        return result;
    }
    
    public List<User> select(String id) {
        
        return null;
    }
    
    public User getLoginUser(String id, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        
        // select * from MEMBER where MEMBERID='test01' and PASSWORD='test01';
        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement("select * from MEMBER where MEMBERID=? and PASSWORD=?");
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            
            rs = pstmt.executeQuery();
            if ( rs.next() ) {
                user = new User();
                user.setId(rs.getString("MEMBERID"));
                user.setName(rs.getString("NAME"));
                user.setEmail(rs.getString("EMAIL"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(pstmt);
            JDBCUtil.close(conn);
        }
        System.out.println("result user:" + user);
        return user;
    }
    
    public int update(User user) {
        
        return 0;
    }
    
    public int delete(String id) {
        
        return 0;
    }

}
