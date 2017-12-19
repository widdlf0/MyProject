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
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<title>JSP 게시판 웹 사이트</title>

<script>
	function logout() {
		alert("정상적으로 로그아웃 되셨습니다^^");
		location.href = "/logoutAction.us";
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
			<form method="post" action="/userPwModifyAction.us"
				name="userpwmodify">
				<input type="hidden" name="userID" value="<%=userID%>">
				<table class="table table-stried"
					style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="2"
								style="background-color: #eeeeee; text-align: center;">비밀
								번호 변경</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>현재 비밀번호 입력</td>
							<td><input type="password" name="nowPassword"
								class="form-control" placeholder="현재 비밀번호" autocomplete="off"></td>
						</tr>
						<tr>
							<td>변경할 비밀번호</td>
							<td><input type="password" class="form-control"
								placeholder="비밀번호" name="userPassword" maxlength="20"
								id="userPassword" autocomplete="off"> <input
								type="password" class="form-control" placeholder="비밀번호 확인"
								name="userPasswordcheck" maxlength="20" id="userPasswordcheck"
								autocomplete="off"> <span id="userPasswordcheck2"
								style="border: none;"></span> <script>
									function check() {

										if (!document.userpwmodify.nowPassword.value) {
											alert("현재 비밀번호를 입력하시오.");
											/* alert()는 알람창을 띄운다. */
											document.userpwmodify.nowPassword
													.focus();
											return false;
										}

										if (!document.userpwmodify.userPassword.value) {
											alert("변경할 비밀번호를 입력하시오.");
											/* alert()는 알람창을 띄운다. */
											document.userpwmodify.userPassword
													.focus();
											return false;
										}
									}

									//비밀번호 체크
									$(document)
											.ready(
													function() {
														//  console.log( "ready!" );
														$("#userPasswordcheck")
																.keyup(
																		function() {

																			var userPassword = $(
																					"#userPassword")
																					.val();
																			var userPasswordcheck = $(
																					"#userPasswordcheck")
																					.val();

																			$
																					.ajax({
																						type : "POST",
																						url : "/userPwCheck.us",
																						data : {
																							"userPassword" : userPassword,
																							"userPasswordcheck" : userPasswordcheck
																						},
																						dataType : "json",
																						success : function(
																								args) {

																							if (args.result2 == "success") {
																								$(
																										"#joinbtn")
																										.prop(
																												"disabled",
																												false);
																								$(
																										"#joinbtn")
																										.css(
																												"background-color",
																												"blue");
																								$(
																										"#userPasswordcheck")
																										.css(
																												"background-color",
																												"#D0F5A9");
																								$(
																										"#userPasswordcheck2")
																										.css(
																												"color",
																												"green");
																								$(
																										"#userPasswordcheck2")
																										.text(
																												args.userPasswordcheck2);
																								$(
																										"#userPasswordcheck2")
																										.show();

																							} else if (args.result2 == "fail") {
																								$(
																										"#joinbtn")
																										.prop(
																												"disabled",
																												true);
																								$(
																										"#joinbtn")
																										.css(
																												"background-color",
																												"red");
																								$(
																										"#userPasswordcheck")
																										.css(
																												"background-color",
																												"red");
																								$(
																										"#userPasswordcheck2")
																										.css(
																												"color",
																												"red");
																								$(
																										"#userPasswordcheck2")
																										.text(
																												args.userPasswordcheck2);
																								$(
																										"#userPasswordcheck2")
																										.show();

																							}

																						}
																					});
																		});
													});
								</script></td>
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
								value="수정" disabled="disabled" id="joinbtn">
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

</body>
</html>