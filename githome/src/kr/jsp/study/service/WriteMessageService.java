package kr.jsp.study.service;

import java.sql.Connection;
import java.sql.SQLException;

import kr.jsp.study.dao.MessageDao;
import kr.jsp.study.dao.MessageDaoProvider;
import kr.jsp.study.jdbc.ConnectionProvider;
import kr.jsp.study.jdbc.JDBCUtil;
import kr.jsp.study.vo.Message;

public class WriteMessageService {
	private static WriteMessageService instance =
			new WriteMessageService();

	public static WriteMessageService getInstance() {
		return instance;
	}

	private WriteMessageService() {
	}

	public void write(Message message) throws ServiceException {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			MessageDao messageDao =
					MessageDaoProvider.getInstnace().getMessageDao();
			messageDao.insert(conn, message);
		} catch (SQLException e) {
			throw new ServiceException(
					"메시지 등록 실패: " + e.getMessage(), e);
		} finally {
			JDBCUtil.close(conn);
		}
	}

}
