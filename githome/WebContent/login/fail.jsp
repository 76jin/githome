<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그인 실패</title>
    <link href="../stylesheets/stylesheet.css" rel="stylesheet" media="screen">
    <link href="../stylesheets/mycss.css" rel="stylesheet" media="screen">
    <!-- 부트스트랩 -->
    <link href="../stylesheets/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
    <!-- HEADER -->
    <div id="header_wrap" class="outer">
        <header class="inner">
             <jsp:include page="/template/header.jsp" flush="false" />
        </header>
    </div>
    
    <!-- MAIN CONTENT -->
    <div id="main_content_wrap" class="outer">
      <section id="main_content" class="inner">
        <h3>
            로그인에 실패하였습니다!!! <br>
            아이디와 패스워드가 일치하지 않습니다.<br>
            다시 로그인 하시기 바랍니다!!<br>
            <br>
        </h3>
      </section>
    </div>
    
    <!-- FOOTER  -->
    <div id="footer_wrap" class="outer">
      <footer class="inner">
        <jsp:include page="/template/footer.jsp" flush="false" />
      </footer>
    </div>
    
    <!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요한) -->
    <script src="../javascripts/jquery-2.1.3.js"></script>
    <!-- 모든 합쳐진 플러그인을 포함하거나 (아래) 필요한 각각의 파일들을 포함하세요 -->
    <script src="../javascripts/bootstrap.min.js"></script>
    <script src="../javascripts/main.js"></script>
</body>
</html>