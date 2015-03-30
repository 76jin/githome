package kr.jsp.study.service;

import java.sql.Connection;
import java.sql.SQLException;

import kr.jsp.study.dao.MessageDao;
import kr.jsp.study.dao.MessageDaoProvider;
import kr.jsp.study.jdbc.ConnectionProvider;
import kr.jsp.study.jdbc.JDBCUtil;
import kr.jsp.study.vo.Message;

public class DeleteMessageService {

	private static DeleteMessageService instance =
			new DeleteMessageService();

	public static DeleteMessageService getInstance() {
		return instance;
	}

	private DeleteMessageService() {
	}

	public void deleteMessage(int messageId, String password)
			throws ServiceException, InvalidMessagePassowrdException,
			MessageNotFoundException {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			MessageDao messageDao =
					MessageDaoProvider.getInstnace().getMessageDao();
			Message message = messageDao.select(conn, messageId);
			if (message == null) {
				throw new MessageNotFoundException("메시지가 없습니다:"
						+ messageId);
			}
			if (!message.hasPassword()) {
				throw new InvalidMessagePassowrdException();
			}
			if (!message.getPassword().equals(password)) {
				throw new InvalidMessagePassowrdException();
			}
			messageDao.delete(conn, messageId);

			conn.commit();
		} catch (SQLException ex) {
			JDBCUtil.rollback(conn);
			throw new ServiceException("삭제 처리 중 에러가 발생했습니다:"
					+ ex.getMessage(), ex);
		} catch (InvalidMessagePassowrdException ex) {
			JDBCUtil.rollback(conn);
			throw ex;
		} catch (MessageNotFoundException ex) {
			JDBCUtil.rollback(conn);
			throw ex;
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(false);
				} catch (SQLException e) {
				}
				JDBCUtil.close(conn);
			}
		}
	}
}
