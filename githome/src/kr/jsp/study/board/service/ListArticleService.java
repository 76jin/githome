package kr.jsp.study.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import kr.jsp.study.board.dao.ArticleDao;
import kr.jsp.study.board.vo.Article;
import kr.jsp.study.board.vo.ArticleListModel;
import kr.jsp.study.jdbc.ConnectionProvider;
import kr.jsp.study.jdbc.JDBCUtil;

public class ListArticleService {

    private static ListArticleService instance = new ListArticleService();
    public static ListArticleService getInstance() {
        return instance;
    }
    
    public static final int COUNT_PER_PAGE = 10;
    
    public ArticleListModel getArticleList(int requestPageNumber) {
        if (requestPageNumber < 0 ) {
            throw new IllegalArgumentException("page number < 0 :" + requestPageNumber);
        }
        ArticleDao articleDao = ArticleDao.getInstance();
        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            int totalArticleCount = articleDao.selectCount(conn);
            if (totalArticleCount == 0) {
                return new ArticleListModel();
            }
            
            int totalPageCount = calculateTotalPageCount(totalArticleCount);
            int firstRow = (requestPageNumber - 1) * COUNT_PER_PAGE + 1;
            int endRow = firstRow + COUNT_PER_PAGE - 1;
            if (endRow > totalArticleCount) {   // 어떤 경우지???
                endRow = totalArticleCount;
            }
            
            List<Article> articleList = articleDao.select(conn, firstRow, endRow);
            ArticleListModel articleListView = new ArticleListModel(articleList, requestPageNumber, totalPageCount, firstRow, endRow);
            return articleListView;
        } catch (SQLException ex) {
            throw new RuntimeException("DB 에러: " + ex.getMessage(), ex);
        } finally {
            JDBCUtil.close(conn);
        }
    }

    private int calculateTotalPageCount(int totalArticleCount) {
        if (totalArticleCount == 0) {
            return 0;
        }
        int pageCount = totalArticleCount / COUNT_PER_PAGE;
        if (totalArticleCount % COUNT_PER_PAGE > 0) {
            pageCount++;
        }
        return pageCount;
    }
}
