//console.log('This would be the main JS file.');

 /**
	 * 쿠키값 추출
	 * 
	 * @param cookieName
	 *            쿠키명
	 */
function getCookie(cookieName) {
	var search = cookieName + "=";
	var cookie = document.cookie;

	// 현재 쿠키가 존재할 경우
	if (cookie.length > 0) {
		// 해당 쿠키명이 존재하는지 검색한 후 존재하면 위치를 리턴.
		startIndex = cookie.indexOf(cookieName);

		// 만약 존재한다면
		if (startIndex != -1) {
			// 값을 얻어내기 위해 시작 인덱스 조절
			startIndex += cookieName.length;

			// 값을 얻어내기 위해 종료 인덱스 추출
			endIndex = cookie.indexOf(";", startIndex);

			// 만약 종료 인덱스를 못찾게 되면 쿠키 전체길이로 설정
			if (endIndex == -1)
				endIndex = cookie.length;

			// 쿠키값을 추출하여 리턴
			return unescape(cookie.substring(startIndex + 1, endIndex));
		} else {
			// 쿠키 내에 해당 쿠키가 존재하지 않을 경우
			return false;
		}
	} else {
		// 쿠키 자체가 없을 경우
		return false;
	}
}

// 이벤트 처리 함수
$(function(){
	// 로그인/로그아웃 버트 처리
	$("#login_button").on('click', function(){
		console.log('aaa:' + $(this).text());
		if ($(this).text() == "로그인") {
			location.href = "/githome/login/loginForm.do";
		} else if ($(this).text() == "로그아웃") {
			location.href = "/githome/login/logout.do";
		} else {
			alert("로그인 처리 에러!!!");
		}
	});
});

$(function(){
	// 로그인/로그아웃 글자 처리
	loginUserName = getCookie("loginUserName");
	console.log("loginUserName:", loginUserName);
	if (loginUserName != false) {
		$("#login_button").text('로그아웃');
	} else {
		$("#login_button").text('로그인');
	}
});


