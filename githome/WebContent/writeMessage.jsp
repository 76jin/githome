<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page errorPage="errorView.jsp" %>
<%@ page import="kr.jsp.study.vo.Message" %>
<%@ page import="kr.jsp.study.service.WriteMessageService" %>
<%
	request.setCharacterEncoding("euc-kr");
%>
<jsp:useBean id="message" class="kr.jsp.study.vo.Message">
	<jsp:setProperty name="message" property="*" />
</jsp:useBean>
<%
	WriteMessageService writeService = WriteMessageService.getInstance();
	writeService.write(message);
%>
<html>
<head>
	<title>���� �޽��� ����</title>
</head>
<body>
���Ͽ� �޽����� ������ϴ�.
<br/>
<a href="list.jsp">[��� ����]</a>
</body>
</html>