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
			<table class="table table-stried"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="2"
							style="background-color: #eeeeee; text-align: center;">내 정보</th>
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
						<td style="text-align: center;" class="form-phone"><%=userBean.getUserPhone1()%></td>
					</tr>
					<tr>
						<th style="text-align: center;" class="form-phone">생년월일</th>
						<td style="text-align: center;" class="form-phone"><%=userBean.getUserDate1()%></td>
					</tr>
					<tr>
						<th style="text-align: center;" class="form-phone">이메일</th>
						<td style="text-align: center;" class="form-phone"><%=userBean.getUserEmail1()%></td>
					</tr>
					<tr>
						<th style="text-align: center;" class="form-phone">주소</th>
						<td style="text-align: center;" class="form-phone"><%=userBean.getUserAddress1()%></td>
					</tr>
					<tr>
						<th style="text-align: center;" class="form-phone">회원가입 날자</th>
						<td style="text-align: center;" class="form-phone"><%=userBean.getUserDate2()%></td>
					</tr>

				</tbody>
				<tfoot>
					<tr>
						<td style="background-color: #eeeeee; text-align: center;"><input
							type="button" class="btn btn-primary pull-left"
							onclick="history.back()" value="뒤로"></td>
						<td style="background-color: #eeeeee; text-align: center;"><a
							href="/usermyhomepwmodify.us" class="btn btn-primary pull">비밀번호
								변경</a>&nbsp;&nbsp; <a href="/usermyhomemodify.us"
							class="btn btn-primary pull">회원 정보 수정</a>&nbsp;&nbsp; <a
							href="/usermyhomedelete.us" class="btn btn-primary pull">&nbsp;&nbsp;&nbsp;회원
								&nbsp; 탈퇴&nbsp;&nbsp;&nbsp;</a>&nbsp;&nbsp; <!-- 																<a href="#" class="btn btn-primary pull">목록</a>&nbsp;&nbsp; -->
						</td>
					</tr>
				</tfoot>
			</table>

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
</body>
</html>