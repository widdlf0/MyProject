<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="user.db.UserBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>사다리게임 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=1024, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
<link rel="stylesheet" type="text/css" href="/css/sadari.css/app.css" />
<link rel="stylesheet" type="text/css" href="/css/sadari.css/sadari.css" />
<script type="text/javascript" src="/js/sadari.js/animation.js"></script>
<script type="text/javascript" src="/js/sadari.js/fakescroll.js"></script>
<script type="text/javascript" src="/js/sadari.js/app.js"></script>
<script type="text/javascript" src="/js/sadari.js/sadari.js"></script>
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="/css/bootstrap.css">

						<script>
								function logout(){
									alert("정상적으로 로그아웃 되셨습니다^^");
									location.href = "/logoutAction.us";
								}
						</script>

	
</head>

<body>
<%
				String userID = null;
 				String Nick = null;
 				
				if(session.getAttribute("userID") != null){
					userID = (String) session.getAttribute("userID");
 					Nick = (String) session.getAttribute("userNick");
					
							
				}
				
		
		%>
		<nav  class="navbar navbar-default">
				<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
								data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
								aria-expanded="false">
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>		
						</button>
						<a class="navbar-brand" href="/main.us"><img src="/images/Logo.png"></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav">
								<li><a href="/main.us">메인</a></li>
								<li><a href="/NoticeListAction.no">공지사항</a></li>
								<li><a href="/maps.mp">맛집 검색</a></li>
								<li><a href="/BbsListAction.bo">자유 게시판</a></li>
								<li><a href="/ImgListAction.img">이미지 게시판</a></li>
								<li><a href="/FAQ/FAQ.jsp">FAQ</a></li>
								<li class="active"><a href="/sadari/index2.jsp">사다리게임</a></li>
								<li><a href="/inquiry/inquiry.jsp">고객센터</a></li>
						</ul>
						
						<%
								if(userID == null){
						%>
						
						<ul class="nav navbar-nav navbar-right">
								<li class="dropdown">
										<a href="#" class="dropdown-toggle"
												data-toggle="dropdown" role="button" aria-haspopup="true"
												aria-expanded="false">접속하기<span class="caret"></span></a>
										<ul class="dropdown-menu">
												<li><a href="/login.us">로그인</a></li>
												<li>----------------------</li>
												<li><a href="/join.us">회원가입</a></li>
										</ul>
								</li>
						</ul>
						
						<%
								}else if(userID.equals("admin")){
						%>
						
						<ul class="nav navbar-nav navbar-right">
								<li class="dropdown">
										<a href="#" class="dropdown-toggle"
												data-toggle="dropdown" role="button" aria-haspopup="true"
												aria-expanded="false"><span style="color: blue;"><%=Nick %></span> 님 환영합니다<span class="caret"></span></a>
										<ul class="dropdown-menu">
												<li><a href="/usermyhome.us">내 정보</a></li>
												<li><a href="/msgNewListAction.mg">쪽지함</a></li>
												<li>----------------------</li>
												<li><a href="/adminUserListAction.us">회원 관리</a></li>
												<li>----------------------</li>
												<li><a href="javascript:logout()">로그아웃</a></li>
										</ul>
								</li>
						</ul>
						
						
						<%
								}else{
								
						%>
						
						<ul class="nav navbar-nav navbar-right">
								<li class="dropdown">
										<a href="#" class="dropdown-toggle"
												data-toggle="dropdown" role="button" aria-haspopup="true"
												aria-expanded="false"><span style="color: blue;"><%=Nick %></span> 님 환영합니다<span class="caret"></span></a>
										<ul class="dropdown-menu">
												<li><a href="/usermyhome.us">내 정보</a></li>
												<li><a href="/msgNewListAction.mg">쪽지함</a></li>
												<li>----------------------</li>
												<li><a href="javascript:logout()">로그아웃</a></li>
										</ul>
								</li>
						</ul>
						
						
						<%			
								}
						%>
						
				</div>
		</nav>
		
		<script src="/js/jquery-3.2.1.min.js"></script>
		<script src="/js/bootstrap.js"></script>

<div id="zone" style="background: url('../images/슬라이드 이미지.jpg'); background-repeat: no-repeat; background-size: 100% 100%;">
	<h1>사다리게임</h1>
	<div class="util">
		<em id="checkbox" class="checkbox on"></em>
		<input type="text" id="numinput" value="5" maxlength="2" readonly="readonly" />
		<span class="updown">
			<em id="btnup"><span>Up</span></em>
			<em id="btndown"><span>Down</span></em>
		</span>
		<button id="btnreset">Reset</button>
		<button id="btngoall">Go All</button>
	</div>
	<div id="footer">
		<p> <a href="http://www.psyonline.kr/" id="aboutbutton" target="_blank"></a></p>
		
	</div>
	
</div>


<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-9380794-1");
pageTracker._trackPageview();
} catch(err) {}</script>

</body>
</html>