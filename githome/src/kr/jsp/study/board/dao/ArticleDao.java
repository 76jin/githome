package kr.jsp.study.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.jsp.study.board.vo.Article;
import kr.jsp.study.jdbc.JDBCUtil;

public class ArticleDao {

    private static ArticleDao instance = new ArticleDao();
    public static ArticleDao getInstance() {
        return instance;
    }
    
    public ArticleDao() {
    }
    
    public int selectCount(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select count(*) from article");
            rs.next();
            System.out.println("[DAO] selectCount:" + rs.getInt(1));
            return rs.getInt(1);
        } catch (SQLException ex) {
            throw ex;
        } finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(stmt);
        }
    }
    
    public List<Article> select(Connection conn, int firstRow, int endRow) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "select article_id, "
                + "group_id, sequence_no, posting_date, "
                + "read_count, writer_name, password, title "
                + "from article order by sequence_no desc limit ?, ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, firstRow - 1);
            pstmt.setInt(2, endRow - firstRow + 1);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                return Collections.emptyList();
            }
            List<Article> articlList = new ArrayList<Article>();
            do {
                Article article = makeArticleFromResultSet(rs, false);
                articlList.add(article);
            } while (rs.next());
            return articlList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(pstmt);
        }
    }

    private Article makeArticleFromResultSet(ResultSet rs, boolean readContent) throws SQLException {
        Article article = new Article();
        System.out.println("[DAO] Article DB id name:" + Article.TABLE.id.toString());
        
        article.setId(rs.getInt(Article.TABLE.id.toString()));
        article.setGroupid(rs.getInt(Article.TABLE.groupid.toString()));
        article.setSequenceNumber(rs.getString(Article.TABLE.sequenceNumber.toString()));
        article.setPostingDate(rs.getTimestamp(Article.TABLE.postingDate.toString()));
        article.setReadCount(rs.getInt(Article.TABLE.readCount.toString()));
        article.setWriterName(rs.getString(Article.TABLE.writerName.toString()));
        article.setPassword(rs.getString(Article.TABLE.password.toString()));
        article.setTitle(rs.getString(Article.TABLE.title.toString()));
        if (readContent) {
            article.setContent(rs.getString(Article.TABLE.content.toString()));
        }
        System.out.println("[DAO] Article:" + article.toString());
        return article;
    }
    
    public int insert(Connection conn, Article article) throws SQLException {
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        String insertQuery = "insert into article "
                + "("
                + Article.TABLE.groupid.toString() + ", "
                + Article.TABLE.sequenceNumber.toString() + ", "
                + Article.TABLE.postingDate.toString() + ", "
                + Article.TABLE.readCount.toString() + ", "
                + Article.TABLE.writerName.toString() + ", "
                + Article.TABLE.password.toString() + ", "
                + Article.TABLE.title.toString() + ", "
                + Article.TABLE.content.toString()
                + ") "
                + "values (?, ?, ?, 0, ?, ?, ?, ?)";
        String selectQuery = "select last_insert_id() from article";
        try {
            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, article.getGroupid());
            pstmt.setString(2, article.getSequenceNumber());
            pstmt.setTimestamp(3, new Timestamp(article.getPostingDate().getTime()));
            pstmt.setString(4, article.getWriterName());
            pstmt.setString(5, article.getPassword());
            pstmt.setString(6, article.getTitle());
            pstmt.setString(7, article.getContent());
            int insertedCount = pstmt.executeUpdate();
            
            if (insertedCount > 0) {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(selectQuery);
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(stmt);
            JDBCUtil.close(pstmt);
        }
    }
    
    public Article selectById(Connection conn, int articleId) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "select * from article where article_id = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, articleId);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Article article = makeArticleFromResultSet(rs, true);
            return article;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(pstmt);
        }
    }
    
    public void increaseReadCount(Connection conn, int articleId) throws SQLException {
        PreparedStatement pstmt = null;
        String query = "update article "
                + "set read_count = read_count + 1 "
                + "where article_id = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, articleId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            JDBCUtil.close(pstmt);
        }
    }
    
    public String selectLastSequenceNumber(Connection conn, 
            String searchMaxSeqNum, String searchMinSeqNum ) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "select min(sequence_no) from article "
                + "where sequence_no < ? and sequence_no >= ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, searchMaxSeqNum);
            pstmt.setString(2, searchMinSeqNum);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            System.out.println("=== [DAO]Before searchMinSeqNum:" + searchMinSeqNum);
            System.out.println("=== [DAO]After searchMinSeqNum:" + rs.getString(1));
            return rs.getString(1);
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(pstmt);
        }
    }
}
