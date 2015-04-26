package kr.jsp.study.board.service;

import java.sql.Connection;
import java.sql.SQLException;

import kr.jsp.study.board.dao.ArticleDao;
import kr.jsp.study.board.vo.Article;
import kr.jsp.study.jdbc.ConnectionProvider;
import kr.jsp.study.jdbc.JDBCUtil;

public class ReadArticleService {

    private static ReadArticleService instance = new ReadArticleService();
    public static ReadArticleService getInstance() {
        return instance;
    }
    
    public ReadArticleService() {
    }
    
    public Article readArticle(int articleId) throws ArticleNotFoundException {
        return selectArticle(articleId, true);
    }
    
    public Article getArticle(int articleId) throws ArticleNotFoundException {
        return selectArticle(articleId, false);
    }

    private Article selectArticle(int articleId, boolean increaseCount) throws ArticleNotFoundException {
        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            ArticleDao articleDao = ArticleDao.getInstance();
            Article article = articleDao.selectById(conn, articleId);
            if (article == null) {
                throw new ArticleNotFoundException("게시글이 존재하지 않음 : " + articleId);
            }
            if (increaseCount) {
                articleDao.increaseReadCount(conn, articleId);
                article.setReadCount(article.getReadCount() + 1);
            }
            return article;
        } catch (SQLException ex) {
            throw new RuntimeException("DB 에러: " + ex.getMessage(), ex);
        } finally {
            JDBCUtil.close(conn);
        }
    }
}
