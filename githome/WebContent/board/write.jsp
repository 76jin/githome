<%@page import="kr.jsp.study.board.vo.Article"%>
<%@page import="kr.jsp.study.board.service.WriteArticleService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="writingRequest"
    class="kr.jsp.study.board.service.WritingRequest" />
<jsp:setProperty name="writingRequest" property="*" />

<%
Article postedArticle = WriteArticleService.getInstance().write(writingRequest);
request.setAttribute("postedArticle", postedArticle);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 작성</title>
</head>
<body>
    게시글을 작성했습니다.
    <br />
    <br />
    <a href='<c:url value="/board/list.jsp" />'>목록 보기</a>
    <a href='<c:url value="/board/read.jsp?articleId=${postedArticle.id}" />'>게시글 읽기</a>
</body>
</html>