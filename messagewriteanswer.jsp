<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="msg.db.MsgBean"%>
<%@ page import="msg.db.MsgDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width-device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP 게시판 웹 사이트</title>

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

		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
			Nick = (String) session.getAttribute("userNick");
		}
		MsgDAO msgDAO = new MsgDAO();
		MsgBean msgBean = new MsgBean();
		int num = Integer.parseInt(request.getParameter("num"));
		msgBean = msgDAO.getMsgDetail(num);
	%>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapsed" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<!-- 아이콘 목록의 줄을 표시 -->
				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/main.us"><img src="images/Logo.png"></a>
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
								<li><a href="/inquiry/inquiry.jsp">고객센터</a></li>
						</ul>
			<%
				if (userID == null) { // 로그인이 안되었을 시 적용되는 화면
			%>

			<ul class="nav navbar-nav navbar-right">
				<!-- 오른쪽에 추가 -->
				<li class="dropdown">
					<!-- href="#"는 연결되는 곳이 없다는 것 --> <a href="#"
					class="dropdown-toggle" data-toggle="dropdown" role="button"
					aria-haspopup="true" aria-expanded="false">접속하기<span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/login.us">로그인</a></li>
						<li>----------------------</li>
						<!-- active는 현재 홈페이지를 의미 -->
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
												<li class="active"><a href="/msgNewListAction.mg">쪽지함</a></li>
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
												<li class="active"><a href="/msgNewListAction.mg">쪽지함</a></li>
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
	<div class="container">
		<div class="row">
		<form action="/msgWriteAction.mg" method="post">
		<input type="hidden" name="msgNick" value="<%=Nick %>">
		<input type="hidden" name="msgReceiveNick" value="<%=msgBean.getMsgNick() %>">
			<table class="table table-striped" style="text-align:center; border: 1px solid #dddddd">
			<!-- table-striped는 홀수 짝수 번갈아가며 구분지어주는 역할 -->
				<thead><!-- thead는 테이블의 제목 부분 -->
					<tr><!-- tr는 하나의 행 -->
						<th colspan="2" style="background-color: #eeeeee; text-align: center; ">메세지 보내기 양식</th>											
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="form-control"><%=msgBean.getMsgNick()%></td>
					</tr>
					<tr>
						<td><input type="text" class="form-control" placeholder="쪽지 제목" name="msgTitle" maxlength="50"></td>
					</tr>
					<tr>
						<td><textarea class="form-control" placeholder="쪽지 내용" name="msgContent" maxlength="2048" style="height:350px;"></textarea></td>
					</tr>
				</tbody>
			</table>
				<input type="button" class="btn btn-primary pull-left" onclick="history.back();" value="뒤로">
				<input type="submit" class="btn btn-primary pull-right" value="쪽지 보내기">
		</form>
		</div>
	</div>
	
	<!-- nav 하나의 웹사이트에서의 전반적인 구성을 보여줌. -->
	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/bootstrap.js"></script>

</body>
</html>