<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="user.db.UserBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>고객센터</title>
<link rel="stylesheet" href="../css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="faq.css" />
	<!-- 각자하면 다 적용되는데 css 2개를 적용시키면 뭔가가 겹쳐서
자주 묻는 질문이 안나오게 된다 -->
	<meta name="viewport" content="width=device-width" , initial-scale="1">
		<style>
#map {
	height: 350px;
	width: 70%;
	margin: 0 auto;
	text-align: center;
}
</style>
		<script type="text/javascript" src=".././js/jquery-1.6.4.min.js"></script>
		<script>
			function fnShowContent(idx) {
				var nowState = $('#' + idx + '_content').attr('style');
				if (nowState) {
					$('#' + idx + '_content').show();
					$('#' + idx + '_content').attr('style', "");
				} else {
					$('#' + idx + '_content').hide();

				}
			}
		</script>
</head>
<body style="overflow: auto;">

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
			src="/images/Logo.png"></a>
	</div>
	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		<ul class="nav navbar-nav">
			<li><a href="/main.us">메인</a></li>
			<li><a href="/NoticeListAction.no">공지사항</a></li>
			<li><a href="/maps.mp">맛집 검색</a></li>
			<li><a href="/BbsListAction.bo">자유 게시판</a></li>
			<li><a href="/ImgListAction.img">이미지 게시판</a></li>
			<li><a href="/FAQ/FAQ.jsp">FAQ</a></li>
			<li><a href="/sadari/index2.jsp">사다리게임</a></li>
			<li class="active"><a href="#">고객센터</a></li>
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


	<!-- <div class="faq">
	<div class="faqHeader">
		<h1>자주 묻는 질문</h1>
	</div>
	<ul class="faqBody">
	<button type="button" class="showAll">답변 모두 여닫기</button>
		<li class="article" id="a1">
			<p class="q"><a href="#a1">Q: 여기는 뭐하는 사이트인가요?</a></p>
			<p class="a">A: 관리자가 맛집을 등록하는 추천하는 것이 아닌 사용자가 직접 맛집을 등록하고 평점과 후기를 쓸수있는 사이트입니다.</p>
		</li>
		<li class="article" id="a2">
			<p class="q"><a href="#a2">Q: 맛집등록은 어떻게 하나요?</a></p>
			<p class="a">A: 맛집검색에 들어가셔서 맛집등록을 누르시면 됩니다</p>
		</li>
		<li class="article" id="a3">
			<p class="q"><a href="#a3">Q: 맛집후기나 평점은 어디서 보나요?</a></p>
			<p class="a">A: 맛집검색에 들어가시면 자기 주변의 맛집을 볼수가 있는데 클릭을 하시면 후기와 평점이 나오게 됩니다</p>
		</li>
		<li class="article" id="a4">
			<p class="q"><a href="#a4">Q: 사다리게임은 왜 있는건가요?</a></p>
			<p class="a">A: 간단한 내기로 밥을사거나 할때 쓰시라고 만들어 두었습니다</p>
		</li>
	</ul>
</div> -->

	&nbsp;&nbsp;
	<a href="/FAQ/FAQ.jsp" class="btn btn-primary" style="width: 24%;">FAQ
		자주묻는질문</a>&nbsp;&nbsp;
	<a href="inquiryemail.jsp" class="btn btn-primary" style="width: 24%;">관리자한테
		문의하기</a>&nbsp;&nbsp;
	<a href="#" class="btn btn-primary" style="width: 24%;">불량유저 신고</a>&nbsp;&nbsp;
	<a href="#" class="btn btn-primary" style="width: 24%;">기타</a>
	<!-- //TAB Object -->
	<!-- FAQ Object -->

	<br />
	<!-- <button type="button" onclick="$('link').attr('href','')">CSS(X)</button>
<button type="button" onclick="$('link').attr('href','faq.css')">CSS(O)</button> -->
	<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script> 
<script type="text/javascript" src="faq.js"></script> -->
	<script src="/js/jquery-3.2.1.min.js"></script>
	<script src="/js/bootstrap.js"></script>
	<br></br>

	<div class="jumbotron"
		style="width: 80%; margin: 0 auto; text-align: center;">


		<div class="card text-white bg-primary mb-3"
			style="float: left; width: 50%;">
			<div class="card-header">전화상담</div>
			<div class="card-body">
				<h4 class="card-title">1588-1588</h4>
				<p class="card-text">오전9시~오후6시</p>
			</div>
		</div>

		<div class="card text-white bg-success mb-3"
			style="float: left; width: 50%;">
			<div class="card-header">대면상담</div>
			<div class="card-body">
				<h4 class="card-title">인천지식산업협회</h4>
				<p class="card-text">오전11시~오후6시</p>
			</div>
		</div>





		<div id="map"></div>
		<script>
			function initMap() {

				var map = new google.maps.Map(document.getElementById('map'), {
					zoom : 15,
					center : {
						lat : 37.438869,
						lng : 126.675026
					}
				});

				// Create an array of alphabetical characters used to label the markers.
				var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

				// Add some markers to the map.
				// Note: The code uses the JavaScript Array.prototype.map() method to
				// create an array of markers based on a given "locations" array.
				// The map() method here has nothing to do with the Google Maps API.
				var markers = locations.map(function(location, i) {
					return new google.maps.Marker({
						position : location,
						label : labels[i % labels.length]
					});
				});

				// Add a marker clusterer to manage the markers.
				var markerCluster = new MarkerClusterer(
						map,
						markers,
						{
							imagePath : 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'
						});
			}
			var locations = [ {
				lat : 37.438869,
				lng : 126.675026
			}
			//         {lat: 37.49395, lng: 126.72431},
			//         {lat: 37.49408, lng: 126.72481},
			//         {lat: 37.49410, lng: 126.72531},
			//         {lat: 37.49415, lng: 126.72581},
			//         {lat: 37.49420, lng: 126.72371},
			//         {lat: 37.49425, lng: 126.72371},
			//         {lat: 37.49430, lng: 126.72371},
			//         {lat: 37.49435, lng: 126.72371},
			//         {lat: 37.49440, lng: 126.72371},
			//         {lat: 37.49445, lng: 126.72371},
			//         {lat: 37.49450, lng: 126.72371},
			//         {lat: 37.49455, lng: 126.72371},
			//         {lat: 37.49460, lng: 126.72371},
			//         {lat: 37.49465, lng: 126.72371},
			//         {lat: 37.49470, lng: 126.72371},
			//         {lat: 37.49475, lng: 126.72371},
			//         {lat: 37.49480, lng: 126.72371},
			//         {lat: 37.49485, lng: 126.72371},
			//         {lat: 37.49490, lng: 126.72371},
			//         {lat: 37.49495, lng: 126.72371},
			//         {lat: 37.49504, lng: 126.72371},
			//         {lat: 37.49514, lng: 126.72371}
			]
		</script>
	</div>
	<div style="text-align: center; background-color: #FE9A2E;">

		<div id="footer-text">
			<span style="font-size: 20px;"><br> <image
						src="./images/Logo.png">㈜FinDish</span><span
				style="font-size: 17px;"> | 주소 : 인천 남구 학익동 663-1 태승빌딩
				4층㈜FinDish | 대표: DishGuys</span><br> <span style="font-size: 17px;">대표전화:
					032-876-3332</span><br>
					<p style="font-size: 17px;">Copyrights(c) ㈜FinDish 2017 all
						rights reserved.</p> <br> <br> <br>
		</div>
	</div>
	<script
		src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js">
		
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB6A6hRz6eJ2rbIlapC5_ZAgsc7TkldQ8s&callback=initMap">
		
	</script>
</body>
</html>
