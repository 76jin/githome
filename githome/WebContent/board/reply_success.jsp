<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>답변글 작성</title>
</head>
<body>
    답변글을 등록했습니다. <br />
    <br />
    <a href='<c:url value="/board/list.jsp?p=${param.p}" />'>목록보기</a>
    <a href='<c:url value="/board/read.do?articleId=${postedArticle.id}&p=${param.p}" />'>게시글 읽기</a>
</body>
</html>