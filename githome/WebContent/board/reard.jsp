<%@page import="kr.jsp.study.board.service.ArticleNotFoundException"%>
<%@page import="kr.jsp.study.board.service.ReadArticleService"%>
<%@page import="kr.jsp.study.board.vo.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
int articleId = Integer.parseInt(request.getParameter("articleId"));
String viewPage = null;
try {
    Article article = ReadArticleService.getInstance().readArticle(articleId);
    request.setAttribute("article", article);
    viewPage = "/board/read_view.jsp";
} catch (ArticleNotFoundException ex) {
    viewPage = "/board/article_not_found.jsp";
}
%>
<jsp:forward page="<%= viewPage %>" />
