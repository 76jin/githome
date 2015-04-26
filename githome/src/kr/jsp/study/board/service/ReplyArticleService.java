package kr.jsp.study.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import kr.jsp.study.board.dao.ArticleDao;
import kr.jsp.study.board.vo.Article;
import kr.jsp.study.jdbc.ConnectionProvider;
import kr.jsp.study.jdbc.JDBCUtil;

public class ReplyArticleService {

    // instance
    // getInstance
    private static ReplyArticleService instance = new ReplyArticleService();
    public static ReplyArticleService getInstance() {
        return instance;
    }
    
    public ReplyArticleService() {
    }
    
    public Article reply(ReplyingRequest replyingRequest)
        throws ArticleNotFoundException, CannotReplyArticleException, 
        LastChildAleadyExistsException {
        
        // 준비사항: 커넥션 객체, 답글 폼에서 얻은 정보(답글 Article)
        Connection conn = null;
        Article article = replyingRequest.toArticle();  // 정보 추가 필요.
        
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);
            
            ArticleDao articleDao = ArticleDao.getInstance();
            
            // 답글 달기
            // 기능: 답글달기 -> insert
            // 필요한 거: 답글 내용, 부모글 Id와 정보, 새 글에 정보 추가.
            Article parent = articleDao.selectById(conn, replyingRequest.getParentArticleId());
            
            try {
                checkParent(parent, replyingRequest.getParentArticleId());
            } catch (Exception e) {
                JDBCUtil.rollback(conn);
                if (e instanceof ArticleNotFoundException) {
                    throw (ArticleNotFoundException)e;
                } else if (e instanceof CannotReplyArticleException) {
                    throw (CannotReplyArticleException)e;
                } else if (e instanceof LastChildAleadyExistsException) {
                    throw (LastChildAleadyExistsException)e;
                }
            }
            
            // Todo: 새 자식글의 순서 번호 구하기
            // 부모 글에서 자식글들의 순서번호 최대/최소 구하기
            // 1. 부모 글의 순서 번호를 가져온다.
            String searchMaxSeqNum = parent.getSequenceNumber();
            // 2. 부모 글에서 자식글의 최소 순서번호를 가져온다.
            String searchMinSeqNum = getSearchMinSeqNum(parent);
            // 3. 부모 글을 통해 마지막 자식글의 순서번호를 가져온다.
            String lastChildSeq = articleDao.selectLastSequenceNumber(conn, searchMaxSeqNum, searchMinSeqNum);
            
            /* Todo: 새 글에 추가 정보 저장.
                private int groupid;
                private String sequenceNumber;
                private Date postingDate;
                private int readCount = 0
             */
            // Todo: 답변 글의 그룹번호와 순서번호 설정
            // 1. 가장 작은 자식글 번호를 이용해서 새 자식글 번호 만들기.
            String sequenceNumber = getSequenceNumber(parent, lastChildSeq);
            // 2. 새 자식글에 그룹번호 저장.
            article.setGroupid(parent.getGroupid());
            // 2. 새 자식글 새 순서 번호 저장.
            article.setSequenceNumber(sequenceNumber);
            // 3. 새 자식글에 글쓴 시간 저장
            article.setPostingDate(new Date());
            
            // Todo: 답 글을 테이블에 삽입
            int articleId = articleDao.insert(conn, article);
            if (articleId == -1) {
                throw new RuntimeException("DB 삽입 실패: " + articleId);
            }
            conn.commit();
            article.setId(articleId);
            return article;
        } catch (SQLException e) {
            JDBCUtil.rollback(conn);
            throw new RuntimeException("DB 에러: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (Exception e) {
                }
            }
            JDBCUtil.close(conn);
        }
    }

    // 가장 작은 자식글 번호를 이용해서 새 자식글 번호를 만든다.
    private String getSequenceNumber(Article parent, String lastChildSeq) 
        throws LastChildAleadyExistsException {
        long parentSeqLong = Long.parseLong(parent.getSequenceNumber());
        int parentLevel = parent.getLevel();
        
        long decUnit = 0;
        if (parentLevel == 0) {
            decUnit = 10000L;   // 98 0000 로 만들기 위해.
        } else if (parentLevel == 1) {
            decUnit = 100L;     // 98 98 00 로 만들기 위해.
        } else if (parentLevel == 2) {
            decUnit = 1L;       // 98 98 98 로 만들기 위해.
        }
        
        String sequenceNumber = null;
        DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
        if (lastChildSeq == null) {     // 답변글이 없음.
            // 101 99 000000 - 10000L = 101 98 0000 (첫번째 자식글 순서번호가 됨)
            sequenceNumber = decimalFormat.format(parentSeqLong - decUnit);
        } else {                        // 답변글이 있음.
            // 마지막 답변글인지 확인
            String orderOfLastChildSeq = null;
            if (parentLevel == 0) {
                orderOfLastChildSeq = lastChildSeq.substring(10, 12); // 101 xx 0000
                sequenceNumber = lastChildSeq.substring(0, 12) + "9999";
            } else if (parentLevel == 1) {
                orderOfLastChildSeq = lastChildSeq.substring(12, 14); // 101 98 xx 00
                sequenceNumber = lastChildSeq.substring(0, 14) + "99";
            } else if (parentLevel == 2) {
                orderOfLastChildSeq = lastChildSeq.substring(14, 16); // 101 98 98 xx
                sequenceNumber = lastChildSeq;
            }
            if (orderOfLastChildSeq.equals("00")) {
                throw new LastChildAleadyExistsException("마지막 자식글이 이미 존재합니다:" + lastChildSeq);
            }
            long seq = Long.parseLong(sequenceNumber) - decUnit;
            sequenceNumber = decimalFormat.format(seq);
        }
        return sequenceNumber;
    }

    private String getSearchMinSeqNum(Article parent) {
        String parentSeqNum = parent.getSequenceNumber();
        DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
        long parentSeqLongValue = Long.parseLong(parentSeqNum);
        long searchMinLongValue = 0;
        switch (parent.getLevel()) {
        case 0:
            searchMinLongValue = parentSeqLongValue / 1000000L * 1000000L;
            break;
        case 1:
            searchMinLongValue = parentSeqLongValue / 10000L * 10000L;
            break;
        case 2:
            searchMinLongValue = parentSeqLongValue / 100L * 100L;
            break;
        }
        return decimalFormat.format(searchMinLongValue);
    }

    private void checkParent(Article parent, int parentArticleId) 
        throws ArticleNotFoundException, CannotReplyArticleException {
        if (parent == null) {
            throw new ArticleNotFoundException("부모글이 존재하지 않음: " + parentArticleId);
        }
        
        int parentLevel = parent.getLevel();
        if (parentLevel == 3) {
            throw new CannotReplyArticleException("마지막 레벨 글에는 답글을 달 수 없습니다:" + parent.getId());
        }
    }
}
