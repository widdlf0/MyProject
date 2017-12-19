<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="/css/bootstrap.css">
<title>JSP 게시판 웹 사이트</title>

						<script>
								function logout(){
									alert("정상적으로 로그아웃 되셨습니다^^");
									location.href = "/logoutAction.us";
								}
						</script>
	

</head>


							<script type="text/javascript">
									function writeform(){
										var answer;
										answer = confirm("글을 등록 하시겠습니까?");
										
										if(answer == true){
											alert("정상적으로 글이 등록 되었습니다.");
											document.bbswrite.submit();
										}else{
											return;
										}
									}
									
									function backform(){
										var answer;
										answer = confirm("글쓰기를 취소하시겠습니까?")
										
										if(answer == true)
											history.back();
										else
											return;
									}
							</script>
							
							
<body style="overflow:auto;">
		<%
				String userID = null;
				String Nick = null;
				
				if(session.getAttribute("userID") != null){
					userID = (String) session.getAttribute("userID");
					Nick = (String) session.getAttribute("userNick");	
				}else{%>
				<script>
					alert("로그인 하세요^^");
					location.href="/login.us";
				</script>
					
		<%		
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
								<li><a href="/sadari/index2.jsp">사다리게임</a></li>
								<li class="active"><a href="#">고객센터</a></li>
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
		
		<div class="container">
				<div class="row">
						<form method="post" action="/email.us" name="a">
						<%-- <input type="hidden" name="BbsNick" value="<%=Nick %>"> --%>
								<table class="table table-stried" style="text-align: center; border: 1px solid #dddddd">
										<thead>
												<tr>
													<th colspan="2" style="background-color: #eeeeee; text-align: center;">관리자한테 메일 보내기</th>
												</tr>
										</thead>
										<tbody>
												<tr>
														<th><input type="text" class="form-control" placeholder="문의종류" name="name" maxlength="50"></th>
												</tr>
												<tr>
														<th><input type="text" class="form-control" placeholder="이메일" name="email" maxlength="50"></th>
												</tr>
												<tr>
														<th><input type="text" class="form-control" placeholder="연락처" name="number" maxlength="50"></th>
												</tr>
												<tr>
														<th><input type="text" class="form-control" placeholder="글 제목" name="subject" maxlength="50"></th>
												</tr>
												<tr>
														<td><textarea class="form-control" placeholder="글 내용" name="content" maxlength="3000" style="height: 500px;"></textarea></td>
												</tr>
												
										</tbody>
								</table>
<!-- 								<a class="btn btn-primary pull-right" onclick="writeform();">글쓰기</a> -->
								<input type="hidden" name="to" value="widdlf9@naver.com">
								<input type="hidden" name="from" value="widdlf9@naver.com">
								<input type="button" class="btn btn-primary pull-right" onclick="check()"value="메일보내기">
								<input type="button" class="btn btn-primary pull-left" onclick="backform()" value="뒤로">
								
						</form>
						
				</div>
		</div>
		<script src="/js/jquery-3.2.1.min.js"></script>
		<script src="/js/bootstrap.js"></script>
		<script> 
 function check() {
    document.a.submit();
}
</script>
</body>
</html>

<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="a" action="mailSend.jsp" method="post">
<!-- <label>업체명</label><input type="text" name="company"> -->
<label>문의종류</label><input type="text" name="name" style="width: 200px; height: 20px;"><br><br>
<label>이메일</label>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="email" style="width: 200px; height: 20px;"><br><br>
<label>연락처</label>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="number" style="width: 200px; height: 20px;"><br><br>
<label>제목</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="subject"><br><br>
<!-- <label>내용</label><input type="text" name="content" maxlength="3000" style="width: 500px; height: 500px;"><br><br> -->
<label>내용</label><br><textarea id="content" name="content" maxlength="3000" style="height: 300px; width: 400px;"></textarea><br><br>
<input type="button" value="메일발송" onclick="check()">
<input type="hidden" name="to" value="widdlf9@naver.com"> <!-- // 여기에 자신의 이메일 계정 -->
<input type="hidden" name="from" value="widdlf9@naver.com"><!-- // 여기에 자신의 이메일 계정 -->
</form>
<script> 
 function check() {
    document.a.submit();
}
</script>
</body>
</html> --%>