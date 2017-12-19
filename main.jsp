<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="user.db.UserBean"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300|Playfair+Display:400italic'
	rel='stylesheet' type='text/css' />
<!-- <link rel="stylesheet" href="css/costom.css"> -->
<title>JSP 게시판 웹 사이트</title>

<noscript>
	<link rel="stylesheet" type="text/css" href="css/noscript.css" />
</noscript>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.eislideshow.js"></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<script>
	function logout() {
		alert("정상적으로 로그아웃 되셨습니다^^");
		location.href = "/logoutAction.us";
	}
</script>

</head>
<script type="text/javascript">
	$(function() {
		$('#ei-slider').eislideshow({
			animation : 'center',
			autoplay : true,
			slideshow_interval : 3000,
			titlesFactor : 0
		});
	});
</script>

<body style="overflow-x: hidden; overflow-y: auto">
	<%
		String userID = null;
		String Nick = null;

		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
			Nick = (String) session.getAttribute("userNick");

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
				<li class="active"><a href="/main.us">메인</a></li>
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

	<div class="wrapper">
		<div id="ei-slider" class="ei-slider">
			<ul class="ei-slider-large">
				<li><img src="images/large/1.jpg" alt="image06" />
					<div class="ei-title">
						<h2>Passionate</h2>
						<h3>Seeker</h3>
					</div></li>
				<li><img src="images/large/2.png" alt="image01" />
					<div class="ei-title">
						<h2>Creative</h2>
						<h3>Duet</h3>
					</div></li>
				<li><img src="images/large/3.jpg" alt="image02" />
					<div class="ei-title">
						<h2>Friendly</h2>
						<h3>Devil</h3>
					</div></li>
				<li><img src="images/large/4.jpg" alt="image03" />
					<div class="ei-title">
						<h2>Tranquilent</h2>
						<h3>Compatriot</h3>
					</div></li>
				<li><img src="images/large/5.jpg" alt="image04" />
					<div class="ei-title">
						<h2>Insecure</h2>
						<h3>Hussler</h3>
					</div></li>
				<li><img src="images/large/6.jpg" alt="image05" />
					<div class="ei-title1">
						<h2>Loving</h2>
						<h3>Rebel</h3>
					</div></li>
				<li><img src="images/large/7.jpg" alt="image07" />
					<div class="ei-title1">
						<h2>Crazy</h2>
						<h3>Friend</h3>
					</div></li>
			</ul>
			<!-- ei-slider-large -->
			<ul class="ei-slider-thumbs">
				<li class="ei-slider-element">Current</li>
				<li><a href="#">Slide 1</a><img src="images/thumbs/1.jpg"
					alt="thumb06" /></li>
				<li><a href="#">Slide 2</a><img src="images/thumbs/2.jpg"
					alt="thumb01" /></li>
				<li><a href="#">Slide 3</a><img src="images/thumbs/3.jpg"
					alt="thumb02" /></li>
				<li><a href="#">Slide 4</a><img src="images/thumbs/4.jpg"
					alt="thumb03" /></li>
				<li><a href="#">Slide 5</a><img src="images/thumbs/5.jpg"
					alt="thumb04" /></li>
				<li><a href="#">Slide 6</a><img src="images/thumbs/6.jpg"
					alt="thumb05" /></li>
				<li><a href="#">Slide 7</a><img src="images/thumbs/7.jpg"
					alt="thumb07" /></li>
			</ul>
			<!-- ei-slider-thumbs -->
		</div>
		<!-- ei-slider -->
		<br> <br>
	</div>
	<!-- wrapper -->
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