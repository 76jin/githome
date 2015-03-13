<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
  request.setCharacterEncoding("utf-8");

  String memberID = request.getParameter("memberID");
  String name = request.getParameter("name");
  
  int updateCount = 0;
  
  Class.forName("com.mysql.jdbc.Driver");
  Connection conn = null;
  Statement stmt = null;
  
  try {
      String jdbcDriver = "jdbc:mysql://localhost:3306/jspstudydb?" +
              "useUnicode=true&characterEncoding=utf8";
      String dbUser = "jspstudy01";
      String dbPassword = "jspstudy01";
      String query = "update MEMBER set NAME='" + name + "' " +
              "where MEMBERID='" + memberID + "'";
      conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPassword);
      stmt = conn.createStatement();
      updateCount = stmt.executeUpdate(query);
  } catch (SQLException ex) {
      out.println(ex.getMessage());
      ex.printStackTrace();
  } finally {
      if (stmt != null) try { stmt.close(); } catch (SQLException ex) {}
      if (conn != null) try { conn.close(); } catch (SQLException ex) {}
  }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>이름 변경</title>
</head>
<body>

<%
  if (updateCount > 0) {
%>
      <%=memberID%>의 이름을 <%=name%>으로 변경!
<%
  } else {
%>
      <%=memberID%>의 아이디가 존재하지 않음!
<%    
  }
%>
</body>
</html>