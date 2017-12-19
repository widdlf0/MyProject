<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="user.db.UserBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="bbs.image.db.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/component.css">
<script src="./js/modernizr.custom.js"></script>
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/imageanswer.js"></script>
<!-- <link rel="stylesheet" href="css/costom.css"> -->
<title>JSP 게시판 웹 사이트</title>

<script>
	function logout() {
		alert("정상적으로 로그아웃 되셨습니다^^");
		location.href = "/logoutAction.us";
	}
</script>
<style>
ul {
	list-style: none;
	padding-left: 0px;
}
</style>

</head>
<body style="overflow-x: hidden; overflow: scroll;">
	<%
		String userID = null;
		String Nick = null;

		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
			Nick = (String) session.getAttribute("userNick");

		}

		List imglist = (List) request.getAttribute("imglist");
		int listcount = ((Integer) request.getAttribute("listcount")).intValue();
		int nowpage = ((Integer) request.getAttribute("page")).intValue();
		int maxpage = ((Integer) request.getAttribute("maxpage")).intValue();
		int startpage = ((Integer) request.getAttribute("startpage")).intValue();
		int endpage = ((Integer) request.getAttribute("endpage")).intValue();
		String opt = (String) request.getAttribute("opt");
		String condition = (String) request.getAttribute("condition");
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
				<li class="active"><a href="/ImgListAction.img">이미지 게시판</a></li>
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
			<h3>이미지 게시판</h3>
			<br>

			<ul class="grid cs-style-3">
				<li>
					<%
						ImageDAO imgDAO = new ImageDAO();
						for (int i = 0; i < imglist.size(); i++) {
							ImageBean imgBean = (ImageBean) imglist.get(i);
					%>
					<div class="img-style"
						style="border: 1px soild black; float: left; width: 18%; margin: 0 10px;">
						<a
							href="/ImgViewAction.img?num=<%=imgBean.getImgNum()%>&userNick=<%=Nick%>"><img
							src="upload/<%=imgBean.getImgfile()%>"
							style="width: 100%; height: 200px;">

						<figcaption>
							<h3><%=imgBean.getImgSubject()%></h3>
							<span class="span-tag1"><%=imgBean.getImgNick()%><span
								class="span-tag2">♥&nbsp;<span class="span-tag3"><%=imgBean.getHeartcount()%></span></span></span>
						</figcaption></a>
						<div>&nbsp;</div>
						<div style="font-weight: bold;">
							<a
								href="/ImgViewAction.img?num=<%=imgBean.getImgNum()%>&userNick=<%=Nick%>"
								style="text-decoration: none; color: black;"><%=imgBean.getImgSubject()%></a>
							<%
								if (imgDAO.getImgAnswerCount(imgBean.getImgNum()) != 0) {
							%>
							&nbsp;<a
								href="javascript:imageanswerblank(<%=imgBean.getImgNum()%>);"
								style="text-decoration: none;">[<%=imgDAO.getImgAnswerCount(imgBean.getImgNum())%>]
							</a>
							<%
								}
							%>

						</div>
						<div style="font-size: 10px; font-weight: lighter;">
							조회수
							<%=imgBean.getImgReadcount()%>&nbsp; | &nbsp;<%=imgBean.getImgDate()%></div>
						<div style="font-size: 13px; font-weight: bold;">
							작성자
							<%=imgBean.getImgNick()%></div>
						<div>&nbsp;</div>
						<div>&nbsp;</div>
					</div> <%
 	}
 %>
				</li>
			</ul>
		</div>
		<br>
		<%
			if (opt == null && condition == null) {
		%>
		<%
			if (nowpage <= 1) {
		%>
		[이전]&nbsp;&nbsp;&nbsp;
		<%
			} else {
		%>
		<a href="/ImgListAction.img?page=<%=nowpage - 1%>">[이전]</a>&nbsp;&nbsp;&nbsp;
		<%
			}
		%>

		<%
			for (int a = startpage; a <= endpage; a++) {
					if (a == nowpage) {
		%>
		<%=a%>&nbsp;&nbsp;
		<%
			} else {
		%>
		<a href="/ImgListAction.img?page=<%=a%>"><%=a%></a>&nbsp;&nbsp;
		<%
			}
		%>
		<%
			}
		%>
		<%
			if (nowpage >= maxpage) {
		%>
		&nbsp;&nbsp;[다음]
		<%
			} else {
		%>
		<a href="/ImgListAction.img?page=<%=nowpage + 1%>">&nbsp;&nbsp;[다음]</a>
		<%
			}
		%>
		<%
			} else {
		%>
		<%
			if (nowpage <= 1) {
		%>
		[이전]&nbsp;&nbsp;&nbsp;
		<%
			} else {
		%>
		<a
			href="/ImgListAction.img?page=<%=nowpage - 1%>&opt=<%=opt%>&condition=<%=condition%>">[이전]</a>&nbsp;&nbsp;&nbsp;
		<%
			}
		%>

		<%
			for (int a = startpage; a <= endpage; a++) {
					if (a == nowpage) {
		%>
		<%=a%>&nbsp;&nbsp;
		<%
			} else {
		%>
		<a
			href="/ImgListAction.img?page=<%=a%>&opt=<%=opt%>&condition=<%=condition%>"><%=a%></a>&nbsp;&nbsp;
		<%
			}
		%>
		<%
			}
		%>
		<%
			if (nowpage >= maxpage) {
		%>
		&nbsp;&nbsp;[다음]
		<%
			} else {
		%>
		<a
			href="/ImgListAction.img?page=<%=nowpage + 1%>&opt=<%=opt%>&condition=<%=condition%>">&nbsp;&nbsp;[다음]</a>
		<%
			}
		%>
		<%
			}
		%>
		<br />

		<form action="/ImgListAction.img">
			<!-- 검색 부분 -->
			<select name="opt">
				<option value="0">제목</option>
				<option value="1">내용</option>
				<option value="2">제목+내용</option>
				<option value="3">글쓴이</option>
			</select> <input type="text" size="20" name="condition" />&nbsp; <input
				type="submit" value="검색" />
		</form>
		<a href="/Imagewrite.img" class="btn btn-primary pull-right">글 등록</a>
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