<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page isErrorPage="true" %>
<html>
<head>
	<title>서비스 에러</title>
</head>
<body>
일시적인 문제로 인해 서비스를 사용할 수 없습니다.<br/>
서비스 사용에 불편을 드려 죄송합니다.
<%
	exception.printStackTrace();
%>
</body>
</html>