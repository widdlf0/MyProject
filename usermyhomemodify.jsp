<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="bbs.db.*"%>
<%@ page import="user.db.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 게시판 웹 사이트</title>

<script>
	function logout() {
		alert("정상적으로 로그아웃 되셨습니다^^");
		location.href = "/logoutAction.us";
	}
</script>

<script>
	function check() {

		if (!document.usermodify.userPhone1.value) {
			alert("휴대전화를 입력 하세요");
			/* alert()는 알람창을 띄운다. */
			document.usermodify.userPhone1.focus();
			return false;
		}
		if (!document.usermodify.userPhone2.value) {
			alert("휴대전화를 입력 하세요");
			/* alert()는 알람창을 띄운다. */
			document.usermodify.userPhone1.focus();
			return false;
		}
		if (!document.usermodify.userPhone3.value) {
			alert("휴대전화를 입력 하세요");
			/* alert()는 알람창을 띄운다. */
			document.usermodify.userPhone1.focus();
			return false;
		}
		if (!document.usermodify.userEmail1.value) {
			alert("이메일을 입력 하세요");
			/* alert()는 알람창을 띄운다. */
			document.usermodify.userEmail1.focus();
			return false;
		}
		if (!document.usermodify.userEmail2.value) {
			alert("이메일을 입력 하세요");
			/* alert()는 알람창을 띄운다. */
			document.usermodify.userEmail2.focus();
			return false;
		}

		if (!document.usermodify.userAddress1.value) {
			alert("주소를 입력 하세요");
			/* alert()는 알람창을 띄운다. */
			document.usermodify.userAddress1.focus();
			return false;
		}

	}
</script>
</head>
<body>
	<%
		String userID = null;
		String Nick = null;
		BbsBean bbsBean = (BbsBean) request.getAttribute("bbsBean");
		UserDAO userDAO = new UserDAO();
		UserBean userBean = new UserBean();

		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
			Nick = (String) session.getAttribute("userNick");

			userBean = userDAO.getUserDetail(userID);
			// 세션에 저장된 아이디를 가져와서
			// 그 아이디 해당하는 회원정보를 가져온다.

		} else {
	%>
	<script>
		alert("로그인 하세요^^");
		location.href = "/login.us";
	</script>

	<%
		}
	%>


	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/main.us"><img
				src="images/Logo.png"></a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="/main.us">메인</a></li>
				<li><a href="/NoticeListAction.no">공지사항</a></li>
				<li><a href="/maps.mp">맛집 검색</a></li>
				<li><a href="/BbsListAction.bo">자유 게시판</a></li>
				<li><a href="/ImgListAction.img">이미지 게시판</a></li>
				<li><a href="/FAQ/FAQ.jsp">FAQ</a></li>
				<li><a href="/sadari/index2.jsp">사다리게임</a></li>
				<li><a href="/inquiry/inquiry.jsp">고객센터</a></li>
			</ul>

			<%
				if (userID == null) {
			%>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/login.us">로그인</a></li>
						<li>----------------------</li>
						<li><a href="/join.us">회원가입</a></li>
					</ul></li>
			</ul>

			<%
				} else if (userID.equals("admin")) {
			%>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false"><span style="color: blue;"><%=Nick%></span>
						님 환영합니다<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="active"><a href="/usermyhome.us">내 정보</a></li>
						<li><a href="/msgNewListAction.mg">쪽지함</a></li>
						<li>----------------------</li>
						<li><a href="/adminUserListAction.us">회원 관리</a></li>
						<li>----------------------</li>
						<li><a href="javascript:logout()">로그아웃</a></li>
					</ul></li>
			</ul>


			<%
				} else {
			%>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false"><span style="color: blue;"><%=Nick%></span>
						님 환영합니다<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="active"><a href="/usermyhome.us">내 정보</a></li>
						<li><a href="/msgNewListAction.mg">쪽지함</a></li>
						<li>----------------------</li>
						<li><a href="javascript:logout()">로그아웃</a></li>
					</ul></li>
			</ul>


			<%
				}
			%>

		</div>
	</nav>
	<div class="container">
		<div class="row">
			<form method="post" action="/userModifyAction.us" name="usermodify"
				onsubmit="return check()">
				<input type="hidden" name="userID" value="<%=userID%>">
				<table class="table table-stried"
					style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="2"
								style="background-color: #eeeeee; text-align: center;">회원
								정보 수정</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th style="text-align: center;" class="form-phone">아이디</th>
							<td style="text-align: center;" class="form-phone"><%=userID%></td>
						</tr>
						<tr>
							<th style="text-align: center;" class="form-phone">이름</th>
							<td style="text-align: center;" class="form-phone"><%=userBean.getUserName()%></td>
						</tr>
						<tr>
							<th style="text-align: center;" class="form-phone">닉네임</th>
							<td style="text-align: center;" class="form-phone"><%=Nick%></td>
						</tr>
						<tr>
							<th style="text-align: center;" class="form-phone">성별</th>
							<td style="text-align: center;" class="form-phone"><%=userBean.getUserGender()%></td>
						</tr>
						<tr>
							<th style="text-align: center;" class="form-phone">휴대전화</th>
							<td style="text-align: center;" class="form-phone"><input
								type="text" class="form-phone" placeholder="010"
								name="userPhone1" maxlength="3" size="1">- <input
								type="text" class="form-phone" placeholder="1111"
								name="userPhone2" maxlength="4" size="2">- <input
								type="text" class="form-phone" placeholder="2222"
								name="userPhone3" maxlength="4" size="2"></td>
						</tr>
						<tr>
							<th style="text-align: center;" class="form-phone">생년월일</th>
							<td style="text-align: center;" class="form-phone"><%=userBean.getUserDate1()%></td>
						</tr>
						<tr>
							<th style="text-align: center;" class="form-phone">이메일</th>
							<td style="text-align: center;" class="form-phone"><input
								type="text" class="form-phone" placeholder="이메일"
								name="userEmail1" maxlength="50" style="width: 50%;"> <input
								type="text" class="form-phone" name="userEmail2" size="10"
								value="" disabled> <select name="email"
								onchange="email_change()" class="form-phone">
									<option value="0">주소 선택&nbsp;&nbsp;&nbsp;</option>
									<option value="naver.com">naver.com</option>
									<option value="daum.net">daum.net</option>
									<option value="gmail.com">gmail.com</option>
									<option value="nate.com">nate.com</option>
									<option value="9">직접입력</option>
							</select> <script>
								function email_change() {
									if (document.usermodify.email.options[document.usermodify.email.selectedIndex].value == '0') {
										document.usermodify.userEmail2.disabled = true;
										document.usermodifyform.userEmail2.value = "";
									}
									if (document.usermodify.email.options[document.usermodify.email.selectedIndex].value == '9') {
										document.usermodify.userEmail2.disabled = false;
										document.usermodify.userEmail2.value = "";
										document.usermodify.userEmail2.focus();
									} else {
										document.usermodify.userEmail2.disabled = false;
										document.usermodify.userEmail2.value = document.usermodify.email.options[document.usermodify.email.selectedIndex].value;
									}
								}
							</script></td>
						</tr>
						<tr>
							<th style="text-align: center;" class="form-phone">주소</th>
							<td style="text-align: center;" class="form-phone"><input
								type="text" name="userAddress1" class="form-phone"
								id="sample4_postcode" placeholder="우편번호" size="5"
								onclick="sample4_execDaumPostcode()" readonly> <input
								type="button" onclick="sample4_execDaumPostcode()"
								value="우편번호 찾기" class="btn btn-primary form-phone"><br>
								<input type="text" name="userAddress2" id="sample4_roadAddress"
								class="form-phone" style="width: 50%;" placeholder="도로명주소"
								onclick="sample4_execDaumPostcode()"><br> <input
								type="text" name="userAddress3" id="sample4_jibunAddress"
								class="form-phone" style="width: 50%;" placeholder="지번주소"
								onclick="sample4_execDaumPostcode()"> <span id="guide"></span>
							</td>
						</tr>

					</tbody>
					<tfoot>
						<tr>
							<td style="background-color: #eeeeee; text-align: center;">
								<input type="button" class="btn btn-primary pull-left"
								onclick="history.back()" value="뒤로">
							</td>
							<td style="background-color: #eeeeee; text-align: center;">
								<input type="submit" class="btn btn-primary pull-right"
								value="수정">
							</td>
						</tr>
					</tfoot>
				</table>

			</form>

		</div>
	</div>
	<div style="text-align: center; background-color: #FE9A2E;">

		<div id="footer-text">
			<span style="font-size: 20px;"><br> <image
					src="./images/Logo.png">㈜FinDish</span><span
				style="font-size: 17px;"> | 주소 : 인천 남구 학익동 663-1 태승빌딩
				4층㈜FinDish | 대표: DishGuys</span><br> <span style="font-size: 17px;">대표전화:
				032-876-3332</span><br>
			<p style="font-size: 17px;">Copyrights(c) ㈜FinDish 2017 all
				rights reserved.</p>
			<br> <br> <br>
		</div>
	</div>
	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
	<script>
		function sample4_execDaumPostcode() {
			new daum.Postcode(
					{
						oncomplete : function(data) {
							// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

							// 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
							// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
							var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
							var extraRoadAddr = ''; // 도로명 조합형 주소 변수

							// 법정동명이 있을 경우 추가한다. (법정리는 제외)
							// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
							if (data.bname !== ''
									&& /[동|로|가]$/g.test(data.bname)) {
								extraRoadAddr += data.bname;
							}
							// 건물명이 있고, 공동주택일 경우 추가한다.
							if (data.buildingName !== ''
									&& data.apartment === 'Y') {
								extraRoadAddr += (extraRoadAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							// 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
							if (extraRoadAddr !== '') {
								extraRoadAddr = ' (' + extraRoadAddr + ')';
							}
							// 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
							if (fullRoadAddr !== '') {
								fullRoadAddr += extraRoadAddr;
							}

							// 우편번호와 주소 정보를 해당 필드에 넣는다.
							document.getElementById('sample4_postcode').value = data.zonecode; //5자리 새우편번호 사용
							document.getElementById('sample4_roadAddress').value = fullRoadAddr;
							document.getElementById('sample4_jibunAddress').value = data.jibunAddress;

							// 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
							if (data.autoRoadAddress) {
								//예상되는 도로명 주소에 조합형 주소를 추가한다.
								var expRoadAddr = data.autoRoadAddress
										+ extraRoadAddr;
								document.getElementById('guide').innerHTML = '(예상 도로명 주소 : '
										+ expRoadAddr + ')';

							} else if (data.autoJibunAddress) {
								var expJibunAddr = data.autoJibunAddress;
								document.getElementById('guide').innerHTML = '(예상 지번 주소 : '
										+ expJibunAddr + ')';

							} else {
								document.getElementById('guide').innerHTML = '';
							}
						}
					}).open();
		}
	</script>
</body>
</html>