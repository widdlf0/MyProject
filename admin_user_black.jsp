<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="user.db.UserBean" %>
<%@ page import="user.db.UserDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				
				List userList= (List)request.getAttribute("userlist");
				int listcount=((Integer)request.getAttribute("listcount")).intValue();
				int nowpage=((Integer)request.getAttribute("page")).intValue();
				int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
				int startpage=((Integer)request.getAttribute("startpage")).intValue();
				int endpage=((Integer)request.getAttribute("endpage")).intValue();
			
				
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
						<a class="navbar-brand" href="/main.us"><img src="images/Logo.png"></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav">
								<li><a href="/main.us">메인</a></li>
								<li><a href="#">공지사항</a></li>
								<li><a href="/maps.mp">맛집 검색</a></li>
								<li><a href="/BbsListAction.bo">자유 게시판</a></li>
								<li><a href="/ImgListAction.img">이미지 게시판</a></li>
								<li><a href="#">FAQ</a></li>
								<li><a href="/sadari/index2.jsp">사다리게임</a></li>
								<li><a href="#">고객센터</a></li>
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
												<li class="active"><a href="/adminUserListAction.us">회원 관리</a></li>
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
				<form>
						<table class="table table-stried" style="text-align: center; border: 1px solid #dddddd">
								<thead>
										<tr>
											<th style="background-color: #eeeeee; text-align: center; width: 25%; font-size: 20px;">유저 아이디</th>
											<th style="background-color: #eeeeee; text-align: center; width: 35%; font-size: 20px;">유저 닉네임</th>
											<th style="background-color: #eeeeee; text-align: center; width: 20%; font-size: 20px;">가입 일자</th>
											<th style="background-color: #eeeeee; text-align: center; width: 20%; font-size: 20px;">블랙리스트</th>
										</tr>
								</thead>
								<tbody>
								<%
										for(int i=0; i< userList.size(); i++){
											UserBean userBean = (UserBean)userList.get(i);
											
											if(userBean.getUserID() != null){
								%>
										<tr>
												<td style="font-size: 20px; vertical-align: middle;">
														<a href="/adminUserDetail.us?userIDdetail=<%=userBean.getUserID() %>"><%=userBean.getUserID() %></a>
												</td>
												<td style="font-size: 15px; vertical-align: middle;"><%=userBean.getUserNick() %></td>
												<td style="font-size: 15px; vertical-align: middle;"><%=userBean.getUserDate2() %></td>
												<td>
												<%if(userBean.getUserAvailable() == 0){ %>
														<a href="/adminUserBlackAction.us?userIDblack=<%=userBean.getUserID() %>" class="btn btn-primary pull">블랙리스트 등록</a>
												<%}else{ %>
														<a href="/adminUserBlackClearAction.us?userIDblackclear=<%=userBean.getUserID() %>" class="btn btn-primary pull-left">블랙리스트 해제</a>
														<a href=/adminUserDeleteAction.us?userIDdelete=<%=userBean.getUserID() %> class="btn btn-primary pull-right">회원 추방</a>
												<%} %>
												</td>
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
														<%if(nowpage<=1){ %>
														[이전]&nbsp;&nbsp;&nbsp;
														<%}else{ %>
														<a href="/adminUserListAction.us?page=<%=nowpage-1 %>">[이전]</a>&nbsp;&nbsp;&nbsp;
														<%} %>
														
														<%for(int a=startpage; a<=endpage; a++){
																if(a==nowpage){%>
																<%=a %>&nbsp;&nbsp;
																<%}else{ %>
																<a href="/adminUserListAction.us?page=<%=a %>"><%=a %></a>&nbsp;&nbsp;
																<%} %>
														<%} %>
														<%if(nowpage>=maxpage){ %>
														&nbsp;&nbsp;[다음]
														<%}else{ %>
														<a href="/adminUserListAction.us?page=<%=nowpage+1 %>">&nbsp;&nbsp;[다음]</a>
														<%} %>
														<br/>
														<!-- 검색 부분 -->
														<select name="opt">
																<option value="0">유저 아이디</option>
																<option value="1">유저 닉네임</option>
														</select>
												<input type="text" size="20" name="condition"/>&nbsp;
												<input type="submit" value="검색"/>
												</td>
												<td style="background-color: #eeeeee; text-align: center;"></td>
												<td style="background-color: #eeeeee; text-align: center;">	</td>
										</tr>
								</tfoot>
						</table>
				
						</form>
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