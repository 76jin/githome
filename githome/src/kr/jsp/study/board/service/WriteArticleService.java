package kr.jsp.study.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import kr.jsp.study.board.dao.ArticleDao;
import kr.jsp.study.board.vo.Article;
import kr.jsp.study.jdbc.ConnectionProvider;
import kr.jsp.study.jdbc.JDBCUtil;

public class WriteArticleService {
    
    private static String SEQUENCE_NAME = "article";

    private static WriteArticleService instance = new WriteArticleService();
    public static WriteArticleService getInstance() {
        return instance;
    }
    
    public WriteArticleService() {
    }
    
    public Article write(WritingRequest writingRequest) throws IdGenerationFailedException {
        int groupId = IdGenerator.getInstance().generateNextId(SEQUENCE_NAME);
        
        Article article = writingRequest.toArticle();
        article.setGroupid(groupId);
        article.setPostingDate(new Date());
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        article.setSequenceNumber(decimalFormat.format(groupId) + "999999");
        
        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);
            
            int articleId = ArticleDao.getInstance().insert(conn, article);
            if (articleId == -1) {
                JDBCUtil.rollback(conn);
                throw new RuntimeException("DB 삽입 실패: " + articleId);
            }
            conn.commit();
            
            article.setId(articleId);
            return article;
        } catch (SQLException ex) {
            JDBCUtil.rollback(conn);
            throw new RuntimeException("DB 에러: " + ex.getMessage(), ex);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {
                }
            }
            JDBCUtil.close(conn);
        }
    }
    
}
