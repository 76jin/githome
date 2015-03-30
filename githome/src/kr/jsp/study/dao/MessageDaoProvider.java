package kr.jsp.study.dao;

//import kr.jsp.study.dao.mssql.MSSQLMessageDao;
import kr.jsp.study.dao.mysql.MySQLMessageDao;
import kr.jsp.study.dao.oracle.OracleMessageDao;

public class MessageDaoProvider {
	private static MessageDaoProvider instance = new MessageDaoProvider();
	public static MessageDaoProvider getInstnace() {
		return instance;
	}
	private MessageDaoProvider() {}
	
	private MySQLMessageDao mysqlDao = new MySQLMessageDao();
	private OracleMessageDao oracleDao = new OracleMessageDao();
//	private MSSQLMessageDao mssqlDao = new MSSQLMessageDao();
	private String dbms;
	
	void setDbms(String dbms) {
		this.dbms = dbms;
	}
	
	public MessageDao getMessageDao() {
		if ("oracle".equals(dbms)) {
			return oracleDao;
		} else if ("mysql".equals(dbms)) {
			return mysqlDao;
//		} else if ("mssql".equals(dbms)) {
//			return mssqlDao;
		}
		return null;
	}
}
