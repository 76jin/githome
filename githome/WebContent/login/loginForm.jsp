<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그인 폼</title>
    <link href="../stylesheets/mycss.css" rel="stylesheet" media="screen">
    <!-- 부트스트랩 -->
    <link href="../stylesheets/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
    <!-- 로그인 폼  -->
    <div id="loginForm">
        <form action="<%=request.getContextPath()%>/login/login.do" method="post">
			<div class="form-group">
			  <label for="usr">아이디:</label>
			  <input type="text" class="form-control" id="usr" name="id">
			</div>
			<div class="form-group">
			  <label for="pwd">비밀번호:</label>
			  <input type="password" class="form-control" id="pwd" name="password">
			</div>
			<div class="checkbox hidden">
				<label> <input type="checkbox"> 아이디 저장
				</label>
			</div>
			<button type="submit" class="btn btn-default">로그인</button>
 			<a class="btn btn-default" href="<%=request.getContextPath()%>/user/new.do">회원가입</a>
        </form>
	</div>
    
    <!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요한) -->
    <script src="../javascripts/jquery-2.1.3.js"></script>
    <!-- 모든 합쳐진 플러그인을 포함하거나 (아래) 필요한 각각의 파일들을 포함하세요 -->
    <script src="../javascripts/bootstrap.min.js"></script>
    <script src="../javascripts/main.js"></script>
    
    <!-- 로그인 실패 팝업 -->
<%
    String errorMsg = (String)request.getAttribute("errorMsg");
    if (errorMsg != null && errorMsg.length() > 0) {
%>
    <script type="text/javascript">
      alert("<%=errorMsg%>");
    </script>
<%
    }
%>
</body>
</html>