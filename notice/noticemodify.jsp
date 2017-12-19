<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="notice.db.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
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
				
				if(session.getAttribute("userID") != null){
					userID = (String) session.getAttribute("userID");
					Nick = (String) session.getAttribute("userNick");	
				}
				
				NoticeBean noticeBean = (NoticeBean)request.getAttribute("noticeBean");
		%>
		<script>
		function check(){
			
			if(<%=noticeBean.getNoticeNum() %> != null){
				alert(<%=noticeBean.getNoticeNum() %>);
				bbsmodify.submit();
			}else{
				alert('ddd'); //저위 값이 널이 아니니까 안타징 그럼 서브밋을 어케날리냐 저 값을 확인할라고 이렇게 만듬. 그럼 날려봄?
				bbsmodify.submit(); //이건무냐  결과값 날리겟다는거  input 타입에서 submit 뺴고 버튼해서 온클릭해서 펑션 들어가게 만듬.
			}
			
			
		}
		</script>
				
		<nav  class="navbar navbar-default">
				<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
								data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
								aria-expanded="false">
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>		
						</button>
						<a class="navbar-brand" href="/main.us"><img src="images/Logo.png"></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav">
								<li><a href="/main.us">메인</a></li>
								<li class="active"><a href="/NoticeListAction.no">공지사항</a></li>
								<li><a href="/maps.mp">맛집 검색</a></li>
								<li><a href="/BbsListAction.bo">자유 게시판</a></li>
								<li><a href="/ImgListAction.img">이미지 게시판</a></li>
								<li><a href="/FAQ/FAQ.jsp">FAQ</a></li>
								<li><a href="/sadari/index2.jsp">사다리게임</a></li>
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
		<div class="container">
				<div class="row">
						<form method="post" action="/NoticeModifyAction.no" enctype="multipart/form-data" name="bbsmodify">
						<input type="hidden" name="NoticeNum" value="<%=noticeBean.getNoticeNum() %>"/>
						<input type="hidden" name="NoticeNick" value="<%=Nick %>"/>
								<table class="table table-stried" style="text-align: center; border: 1px solid #dddddd">
										<thead>
												<tr>
													<th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 글 보기</th>
												</tr>
										</thead>
										<tbody>
												<tr>
<%-- 														<th class="form-control" style="text-align: center;" value="<%=bbsBean.getBbsSubject() %>"><%=bbsBean.getBbsSubject() %></th> --%>
														<th style="text-align: center;"><input type="hidden" class="form-control" placeholder="글 제목"  style="text-align: center;"
														name="NoticeSubject" maxlength="50" value="<%=noticeBean.getNoticeSubject() %>"><%=noticeBean.getNoticeSubject() %></th>
												</tr>
												<tr>
<%-- 														<td class="form-control" style="height: 500px; text-align: center;"><%=bbsBean.getBbsContent() %></td> --%>
														<td><textarea class="form-control" placeholder="글 내용" name="NoticeContent" maxlength="3000" style="height: 500px; text-align: left;" value="<%=noticeBean.getNoticeContent() %>"><%=noticeBean.getNoticeContent() %></textarea></td>
												</tr>
												
										</tbody>
										<tfoot>
												<tr>
														<td>
																<%
																		if(!(noticeBean.getNoticeFile() == null)){
																%>
																<input name="NoticeFile" type="file" class="btn btn-primary pull-right" value="<%=noticeBean.getNoticeFile() %>"/>
																<%}else{ %>
																<input name="NoticeFile" type="file" class="btn btn-primary pull-right" />
																<%} %>
														</td>
<!-- 														<td><input name="BbsFile" type="file" class="btn btn-primary pull-right"/></td> -->
												</tr>
										</tfoot>
								</table>
								<input type="submit" class="btn btn-primary pull-right" value="글 수정" >
								<input type="button" class="btn btn-primary pull-left" onclick="history.back()" value="뒤로">
						</form>
						
				</div>
		</div>
		<script src="js/jquery-3.2.1.min.js"></script>
		<script src="js/bootstrap.js"></script>
</body>
</html>