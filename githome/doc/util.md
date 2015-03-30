# < 자주 사용하는 코드 정리 >

 * 웹애플리케이션 루트 경로 가져오기.
  - <%=request.getContextPath()%>

## 웹 애플리케이션 ##
* 홈경로: /githome

## ==동작 순서==
* 홈페이지 index.html
	- 회원가입 버튼: /user/new.do
		- JSPController -> [/user/new.jsp]()
	- 로그인 버튼: /githome/loginForm.do
		- JSPController -> [/loginForm.jsp]()
* 회원가입 폼 /user/new.jsp
	- /user/add.do
	- JSPController -> *dao.insert new user*
	-> [/user/add_success.jsp]() or [/user/add_fail.jsp]()
	- 회원가입 성공 화면
		-> /githome/loginForm.do
	- 회원가입 실패 화면
		-> /githome/loginForm.do
* 로그인 폼 loginFrom.jsp
	- 회원가입 버튼: /githome/user/new.do
		- JSPController -> [/login/success.jsp]()
	- 로그인 버튼: /githome/login.do
		- JSPController -> [login/fail.jsp]()
* 그 다음 ...