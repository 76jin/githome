<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>새로운 회원</title>
    <!-- 부트스트랩 -->
    <link href="stylesheets/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="stylesheets/mycss.css" rel="stylesheet" media="screen">
</head>
<body>
    <h1>새로 오신 것을 환영합니다.</h1>
    <hr><br>

    <div id="registrationForm">
        <form action="/githome/registration.do" method="post">
            <div class="form-group">
              <label for="usr">아이디</label>
              <input type="text" class="form-control" id="usr" name="id">
            </div>
            <div class="form-group">
              <label for="pwd">비밀번호</label>
              <input type="password" class="form-control" id="pwd" name="password">
            </div>
            <div class="form-group">
              <label for="email">이메일</label>
              <input type="text" class="form-control" id="email" name="email">
            </div>
            <button type="submit" class="btn btn-default">회원가입</button>
        </form>
    </div>

    <!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요한) -->
    <script src="javascripts/jquery-2.1.3.js"></script>
    <!-- 모든 합쳐진 플러그인을 포함하거나 (아래) 필요한 각각의 파일들을 포함하세요 -->
    <script src="javascripts/bootstrap.min.js"></script>
    
</body>
</html>