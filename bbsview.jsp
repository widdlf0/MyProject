<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs.db.*" %>


<%
		String userID = null;
		String Nick = null;
				
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
			Nick = (String) session.getAttribute("userNick");	
		}
				
		BbsBean bbsBean = (BbsBean)request.getAttribute("bbsBean");
		
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/bbsanswer.js"></script>

<title>JSP 게시판 웹 사이트</title>

						<script>
								function logout(){
									alert("정상적으로 로그아웃 되셨습니다^^");
									location.href = "/logoutAction.us";
								}
								
								answerready();
						</script>
						
</head>
<body style="overflow-x:hidden; overflow:scroll;">

		<input type="hidden" id="numtestvalue" value="<%=bbsBean.getBbsNum() %>">
		<input type="hidden" id="loginNick" value="<%=Nick %>">
				
				
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
								<li><a href="/NoticeListAction.no">공지사항</a></li>
								<li><a href="/maps.mp">맛집 검색</a></li>
								<li class="active"><a href="/BbsListAction.bo">자유 게시판</a></li>
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
						<form method="post" action="/BbsWriteAction.bo" enctype="multipart/form-data" name="bbswrite">
						<input type="hidden" name="BbsNick" value="<%=Nick %>">
								<table class="table table-stried" style=" border: 1px solid #dddddd">
										<thead>
												<tr>
													<th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 글 보기</th>
												</tr>
										</thead>
										<tbody>
												<tr>
														<th class="form-control"><%=bbsBean.getBbsSubject() %></th>
<!-- 														<th><input type="text" class="form-control" placeholder="글 제목" name="BbsSubject" maxlength="50"></th> -->
												</tr>
												<tr>
														<td class="form-control" style="height: 500px; overflow:scroll;"><%=bbsBean.getBbsContent() %></td>
<!-- 														<td><textarea class="form-control" placeholder="글 내용" name="BbsContent" maxlength="3000" style="height: 500px;"></textarea></td> -->
												</tr>
										</tbody>
										<tfoot>
												<tr>
														<td class="form-control">
																<%
																		if(!(bbsBean.getBbsFile() == null)){
																%>
																<a href="bbsupload/<%=bbsBean.getBbsFile() %>">
																첨부파일 <%=bbsBean.getBbsFile() %>
																</a>
																<%} %>
														</td>
<!-- 														<td><input name="BbsFile" type="file" class="btn btn-primary pull-right"/></td> -->
												</tr>
												<tr>
														<td style="background-color: #eeeeee; text-align: center;">
																<%int num = bbsBean.getBbsNum();
													   				String BbsNick = Nick;
													   				BbsDAO bbsDAO = new BbsDAO();
													   				boolean usercheck = bbsDAO.isBbsWriter(num, BbsNick); 
																	
																	if(userID == null || usercheck == false){ %>
																<a href="javascript:history.back();" class="btn btn-primary pull-right">목록</a>&nbsp;&nbsp;
																<%}else{ %>
																<a href="/BbsModifyView.bo?num=<%=bbsBean.getBbsNum() %>" class="btn btn-primary">
																수정
																</a>&nbsp;&nbsp;
																<a href="/BbsDeleteAction.bo?num=<%=bbsBean.getBbsNum() %>" class="btn btn-primary">
																삭제
																</a>&nbsp;&nbsp;
																<a href="javascript:history.back();" class="btn btn-primary">목록</a>&nbsp;&nbsp;
																<a href="/write.bo" class="btn btn-primary pull-right">글쓰기</a>
																<%} %>
														</td>
												</tr>
										</tfoot>
								</table>
						</form>
								<table class="table1" style=" border: 1px solid #dddddd; ">
										<thead id="bbstheadanswer">
<!-- 												<tr style="background-color: #eeeeee; text-align: left;"> -->
<!-- 														<th colspan="3" >댓글 현황(수)</th> -->
<!-- 												</tr> -->
										</thead>
										<!-- 댓글 현황(갯수)알려주는게 thead이다 -->
										<tbody id="bbstbodyanswer">
<!-- 												<tr> -->
<!-- 														<th style="width: 15%;">아이디</th> -->
<!-- 														<td style="width: 15%;">날자</td> -->
<!-- 														<td style="width: 70%; text-align: right;"><button type="button" onclick="#" style="background: url('images/공감.png'); width: 8%;">&nbsp;</button>&nbsp;<button type="button" onclick="#" style="background: url('images/비공감.png'); width: 8%;">&nbsp;</button></td> -->
<!-- 												</tr> -->
<!-- 												<tr> -->
<!-- 														<td colspan="3">내용</td> -->
<!-- 												</tr> -->
<!-- 												<tr> -->
<!-- 														<td colspan="3" align="right"><a href="#">[삭제]</a>&nbsp;<a href="#">[답글]</a></td> -->
<!-- 												</tr> -->
<!-- 												<tr> -->
<!-- 														<th style="width: 15%; text-align: center;" rowspan="3"><img src="images/화살표3.png" /></th> -->
<!-- 														<th style="width: 15%; background-color: #eeeeee;">아이디날자</th> -->
<!-- 														<td style="width: 70%; text-align: right; background-color: #eeeeee;"><button type="button" onclick="#" style="background: url('images/공감.png'); width: 8%;">&nbsp;</button>&nbsp;<button type="button" onclick="#" style="background: url('images/비공감.png'); width: 8%;">&nbsp;</button></td> -->
<!-- 												</tr> -->
<!-- 												<tr> -->
<!-- 														<td colspan="3"  style="background-color: #eeeeee;">내용</td> -->
<!-- 												</tr> -->
<!-- 												<tr> -->
<!-- 														<td colspan="3" align="right" style="background-color: #eeeeee;"><a href="#">[삭제]</a>&nbsp;<a href="#">[답글]</a></td> -->
<!-- 												</tr> -->
										</tbody>
										<tfoot>
												<tr>
														<td rowspan="2" colspan="3">
															<form name="answerform">
																<input type="hidden" id="answerNick" value="<%=Nick%>">
																<textarea rows="2" cols="3" style="width: 89%;" id="textanswer" name="textanswer"></textarea>
																<%if(userID != null){ %>
																<a class="btn btn-primary pull-right" style="padding: 10px 15px; width: 10%;" id="textanswerbtn">댓글 등록</a>
																<%}else{ %>
																<a class="btn btn-primary pull-right" style="padding: 10px 15px; width: 10%;" href="javascript:logincheck3();">댓글 등록</a>
																<%} %>
															</form>
														</td>
												</tr>
										</tfoot>
								</table>
								
<!-- 								<form> -->
<!-- 										<table> -->
												
<!-- 										</table>		 -->
<!-- 								</form> -->
<!-- 								<a href="/write.bo" class="btn btn-primary pull-right">글쓰기</a> -->
<!-- 								<input type="button" class="btn btn-primary pull-left" onclick="history.back()" value="뒤로"> -->
						
						
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
</body>
</html>
