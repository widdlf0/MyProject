<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="msg.db.MsgDAO"%>
<%@ page import="msg.db.MsgBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width-device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<!-- <link rel="stylesheet" href="css/custom.css"> -->
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

		if (session.getAttribute("userID") != null) {
			Nick = (String) session.getAttribute("userNick");
			userID = (String) session.getAttribute("userID");
		}
		List msgList = (List) request.getAttribute("msglist");
		int listcount = ((Integer) request.getAttribute("listcount")).intValue();
		int nowpage = ((Integer) request.getAttribute("page")).intValue();
		int maxpage = ((Integer) request.getAttribute("maxpage")).intValue();
		int startpage = ((Integer) request.getAttribute("startpage")).intValue();
		int endpage = ((Integer) request.getAttribute("endpage")).intValue();
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
				} else if (userID.equals("admin")) {
			%>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false"><span style="color: blue;"><%=Nick%></span>
						님 환영합니다<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/usermyhome.us">내 정보</a></li>
						<li class="active"><a href="/msgNewListAction.mg">쪽지함</a></li>
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
						<li class="active"><a href="/msgNewListAction.mg">쪽지함</a></li>
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
			<a href="/msgNewListAction.mg" class="btn btn-primary active">받은
				편지함</a> <a href="/msgReadListAction.mg" class="btn btn-primary">읽은
				편지함</a> <a href=/msgSendListAction.mg class="btn btn-primary">보낸 편지함</a>
			<a href="/msgTrashBoxListAction.mg" class="btn btn-primary">휴지통</a>
			<form>
				<table class="table table-striped"
					style="text-align: center; border: 1px solid #dddddd">
					<!-- table-striped는 홀수 짝수 번갈아가며 구분지어주는 역할 -->
					<thead>
						<!-- thead는 테이블의 제목 부분 -->
						<tr>
							<!-- tr는 하나의 행 -->
							<th style="background-color: #eeeeee; text-align: center;">번호</th>
							<th style="background-color: #eeeeee; text-align: center;">제목</th>
							<th style="background-color: #eeeeee; text-align: center;">작성자</th>
							<th style="background-color: #eeeeee; text-align: center;">수신날짜</th>
						</tr>
					</thead>
					<tbody>
						<%
							for (int i = 0; i < msgList.size(); i++) {
								MsgBean msgBean = (MsgBean) msgList.get(i);
								if (msgBean.getMsgAvailable() == 0 && msgBean.getMsgReceiveNick().equals(Nick)) {
						%>
						<tr>
							<td><%=msgBean.getRnum()%></td>
							<td><a
								href="/msgDetailAction.mg?num=<%=msgBean.getMsgNum()%>"><%=msgBean.getMsgTitle()%></a></td>
							<td><%=msgBean.getMsgNick()%></td>
							<td><%=msgBean.getMsgDate()%></td>
							<!-- substring을 사용하면 문자를 잘라서 출력할 수 있다. -->
						</tr>
						<%
							}
							}
						%>
					</tbody>
					<tfoot>
						<tr>
							<td style="background-color: #eeeeee; text-align: center;"></td>
							<td style="background-color: #eeeeee; text-align: center;">
								<%
									if (nowpage <= 1) {
								%> [이전]&nbsp;&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/msgNewListAction.mg?page=<%=nowpage - 1%>">[이전]</a>&nbsp;&nbsp;&nbsp;
								<%
									}
								%> <%
 	for (int a = startpage; a <= endpage; a++) {
 		if (a == nowpage) {
 %> <%=a%>&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/msgNewListAction.mg?page=<%=a%>"><%=a%></a>&nbsp;&nbsp;
								<%
									}
								%> <%
 	}
 %> <%
 	if (nowpage >= maxpage) {
 %> &nbsp;&nbsp;[다음] <%
 	} else {
 %> <a
								href="/msgNewListAction.mg?page=<%=nowpage + 1%>">&nbsp;&nbsp;[다음]</a>
								<%
									}
								%> <br /> <!-- 검색 부분 --> <select name="opt">
									<option value="0">제목</option>
									<option value="1">내용</option>
									<option value="2">제목+내용</option>
									<option value="3">글쓴이</option>
							</select> <input type="text" size="20" name="condition" />&nbsp; <input
								type="submit" value="검색" />
							</td>
							<td style="background-color: #eeeeee; text-align: center;"></td>
							<td style="background-color: #eeeeee; text-align: center;"><a
								href="/messagewrite.mg" class="btn btn-primary pull-right">쪽지
									보내기</a></td>
						</tr>
					</tfoot>
				</table>
			</form>
		</div>
	</div>
	
	<!-- nav 하나의 웹사이트에서의 전반적인 구성을 보여줌. -->
	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/bootstrap.js"></script>

</body>
</html>