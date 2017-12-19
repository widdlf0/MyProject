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
<script>
	//아이디 체크
	$(document).ready(function() {
		console.log("ready!");
		$("#userID3").keyup(function() {

			var userID3 = $("#userID3").val();

			$.ajax({
				type : "POST",
				url : "/userIdCheck2.us",
				data : {
					"userID3" : userID3
				},
				dataType : "json",
				success : function(args) {

					/* if(args.result==true){
						$("#joinbtn2").prop("disabled", false);
						$("#joinbtn2").css("background-color", "blue");
						$("#userID3").css("background-color", "#D0F5A9");
						$("#userIDcheck2").css("color", "green");
						$("#userIDcheck2").text(args.userIDcheck);
						$("#userIDcheck2").show();
						
					}else if(args.result==false){
						$("#joinbtn2").prop("disabled", true);
						$("#joinbtn2").css("background-color", "red");
						$("#userID3").css("background-color", "red");
						$("#userIDcheck2").css("color", "red");
						$("#userIDcheck2").text(args.userIDcheck);
						$("#userIDcheck2").show();
						
					}  */

					if (args.result == false) {
						$("#joinbtn2").prop("disabled", false);
						$("#joinbtn2").css("background-color", "blue");
						$("#userID3").css("background-color", "#D0F5A9");
						$("#userIDcheck2").css("color", "green");
						$("#userIDcheck2").text(args.userIDcheck);
						$("#userIDcheck2").show();
					} else if (args.result == true) {
						$("#joinbtn2").prop("disabled", true);
						$("#joinbtn2").css("background-color", "red");
						$("#userID3").css("background-color", "red");
						$("#userIDcheck2").css("color", "red");
						$("#userIDcheck2").text(args.userIDcheck);
						$("#userIDcheck2").show();
					}

				}
			});
		});
	});
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
		/* alert("로그인 하세요^^");
		location.href="/login.us"; */
	</script>

	<%
		}
	%>
	<script>
		function email_change() {
			if (document.userpwfind.email.options[document.userpwfind.email.selectedIndex].value == '0') {
				document.userpwfind.userEmail2.disabled = true;
				document.userpwfind.userEmail2.value = "";
			}
			if (document.userpwfind.email.options[document.userpwfind.email.selectedIndex].value == '9') {
				document.userpwfind.userEmail2.disabled = false;
				document.userpwfind.userEmail2.value = "";
				document.userpwfind.userEmail2.focus();
			} else {
				document.userpwfind.userEmail2.disabled = false;
				document.userpwfind.userEmail2.value = document.userpwfind.email.options[document.userpwfind.email.selectedIndex].value;
			}
		}

		function mySubmit() {

			var f = document.userpwfind;
			f.method = "post";
			f.action = "/SendpwMail.us";
			f.target = "_self";
			f.submit();

		}
	</script>

	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">JSP 게시판 웹 사이트</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="/main.us">메인</a></li>
				<li><a href="#">공지사항</a></li>
				<li><a href="/maps.mp">맛집 검색</a></li>
				<li><a href="/BbsListAction.bo">자유 게시판</a></li>
				<li><a href="/ImgListAction.img">이미지 게시판</a></li>
				<li><a href="#">FAQ</a></li>
				<li><a href="/sadari/index2.jsp">사다리게임</a></li>
				<li><a href="#">고객센터</a></li>
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
						<li><a href="/usermyhome.us">내 정보</a></li>
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
						<li><a href="/usermyhome.us">내 정보</a></li>
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
			<form method="post" action="/SendpwMail.us" name="userpwfind">
				<input type="hidden" name="userID" value="<%=userID%>">
				<table class="table table-stried"
					style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="2"
								style="background-color: #eeeeee; text-align: center;">비밀
								번호 찾기</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>아이디 입력</td>
							<td><input type="text" name="userID2" id="userID3"
								class="form-phone" placeholder="아이디" autocomplete="off"
								style="width: 400px; text-align: center;"> <br>
							<br> <span id="userIDcheck2" class="form-phone"
								style="display: none"></span></td>
							<!-- autocomplete="off"는 자동입력 기능을 사용하지 않는다는 뜻이다 -->

						</tr>
						<tr>
							<td>이메일 입력</td>
							<td><input type="text" class="form-phone" placeholder="이메일"
								name="userEmail1" maxlength="50"> <input type="text"
								class="form-phone" name="userEmail2" size="7" value="" disabled>
								<select name="email" onchange="email_change()"
								class="form-phone">
									<option value="0">주소 선택&nbsp;&nbsp;&nbsp;</option>
									<option value="naver.com">naver.com</option>
									<option value="daum.net">daum.net</option>
									<option value="gmail.com">gmail.com</option>
									<option value="nate.com">nate.com</option>
									<option value="9">직접입력</option>
							</select> <!-- <a href="javascript:mySubmit()" ><button type="button" class="btn btn-primary form-phone">메일인증받기</button></a> -->
								<input type="submit" class="btn btn-primary form-phone"
								value="메일인증받기" disabled="disabled" id="joinbtn2" name="joinbtn2">

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