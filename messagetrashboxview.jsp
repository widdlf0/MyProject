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

		MsgBean msgBean = (MsgBean) request.getAttribute("msgBean");
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

			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">
				<!-- table-striped는 홀수 짝수 번갈아가며 구분지어주는 역할 -->
				<thead>
					<!-- thead는 테이블의 제목 부분 -->
					<tr>
						<!-- tr는 하나의 행 -->
						<th colspan="2"
							style="background-color: #eeeeee; text-align: center;">메세지 글보기</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>받는 사람</td>
						<td><%=msgBean.getMsgReceiveNick()%></td>
					</tr>
					<tr>
						<td>작성자</td>
						<td><%=msgBean.getMsgNick()%></td>
					</tr>
					<tr>
						<td style="width: 20%">메세지 제목</td>
						<td><%=msgBean.getMsgTitle().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
					.replaceAll("\n", "<br>")%></td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td><%=msgBean.getMsgDate()%></td>
					</tr>
					<tr>
						
						<td class="form-phone" colspan="2" style="height: 500px; text-align: center;"><%=msgBean.getMsgContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
					.replaceAll("\n", "<br>")%></td>
						<!-- 글이 안 들어있을 경우 최소한의 칸을 만들어주기 위해 min-height적용 -->
						<!-- .replaceAll(" ","&nbsp;") 공백을 컴퓨터가 알아 듣게 표현 나머지도 동일
						특수문자 및 공백 처리
						.replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") -->
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2" style="background-color: #eeeeee; text-align: center;">
						 
 							 <a href="/msgLiveAction.mg?num=<%=msgBean.getMsgNum()%>">[읽은 편지함으로 보내기]</a>&nbsp;&nbsp;
 							 <a href="/msgDeleteAction.mg?num=<%=msgBean.getMsgNum()%>">[삭제]</a>&nbsp;&nbsp;
							
						</td>
					</tr>
				</tfoot>
			</table>
			<a href="/msgTrashBoxListAction.mg" class="btn btn-primary pull-right">목록</a>
								
			<input type="button" class="btn btn-primary pull-left" onclick="history.back()" value="뒤로">
		</div>
	</div>
	
	<!-- nav 하나의 웹사이트에서의 전반적인 구성을 보여줌. -->
	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/bootstrap.js"></script>

</body>
</html>