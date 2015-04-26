<%@page import="kr.jsp.study.board.service.ReplyArticleService"%>
<%@page import="kr.jsp.study.board.vo.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<jsp:useBean id="replyingRequest" class="kr.jsp.study.board.service.ReplyingRequest" />
<jsp:setProperty name="replyingRequest" property="*"/>

<%
    String viewPage = null;
    try {
        Article postedArticle = 
                ReplyArticleService.getInstance().reply(replyingRequest);
        request.setAttribute("postedArticle", postedArticle);
        viewPage = "/board/reply_success.jsp";
    } catch(Exception ex) {
        viewPage ="/board/reply_error.jsp";
        request.setAttribute("replyException", ex);
    }
%>
<jsp:forward page="<%= viewPage %>" />