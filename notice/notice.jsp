<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="notice.db.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">

<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/bbsanswer.js"></script>

<title>JSP 게시판 웹 사이트</title>

<script>
	function logout() {
		alert("정상적으로 로그아웃 되셨습니다^^");
		location.href = "/logoutAction.us";
	}
</script>
<script>
	$(function() {
		$("#noticeviewcount").change(
				function() {
					var value = this.value;
					var opt = $("#bbsopt").val();
					var condition = $("#bbscondition").val();
					console.log(opt);
					console.log(condition);
					//							alert(value);
					if (opt != null & condition != null)
						location.href = "/NoticeListAction.no?noticeviewcount="
								+ value + "&opt=" + opt + "&condition="
								+ condition;
					else
						location.href = "/NoticeListAction.no?noticeviewcount="
								+ value;

				});
	});
</script>
</head>
<body style="overflow-x: hidden; overflow: scroll;">
	<%
		String userID = null;
		String Nick = null;

		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
			Nick = (String) session.getAttribute("userNick");

		}

		List noticeList = (List) request.getAttribute("noticelist");
		int listcount = ((Integer) request.getAttribute("listcount")).intValue();
		int nowpage = ((Integer) request.getAttribute("page")).intValue();
		int maxpage = ((Integer) request.getAttribute("maxpage")).intValue();
		int startpage = ((Integer) request.getAttribute("startpage")).intValue();
		int endpage = ((Integer) request.getAttribute("endpage")).intValue();
		String opt = (String) request.getAttribute("opt");
		String condition = (String) request.getAttribute("condition");
		int noticeviewcount = ((Integer) request.getAttribute("noticeviewcount")).intValue();

		if (opt != null && condition != null) {
	%>
	<input type="hidden" value="<%=opt%>" id="bbsopt">
	<input type="hidden" value="<%=condition%>" id="bbscondition">
	<%
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
	<div class="container">
		<div class="row">
			<table class="table2">
				<tr style="width: 100%;">
					<th>글 개수 : ${listcount }</th>
					<td style="text-align: right;">페이지 글 수 <select
						name="noticeviewcount" id="noticeviewcount">
							<option value="0">선택</option>
							<option value="10">10개</option>
							<option value="20">20개</option>
							<option value="50">50개</option>
					</select></td>
				</tr>
			</table>
			<form>
				<table class="table table-stried"
					style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th
								style="background-color: #eeeeee; text-align: center; width: 8%;">번호</th>
							<th
								style="background-color: #eeeeee; text-align: center; width: 50%;">제목</th>
							<th
								style="background-color: #eeeeee; text-align: center; width: 14%;">작성자</th>
							<th
								style="background-color: #eeeeee; text-align: center; width: 17%;">작성일</th>
							<th
								style="background-color: #eeeeee; text-align: center; width: 11%;">조회수</th>
						</tr>
					</thead>
					<tbody>
						<%
							NoticeDAO noticeDAO = new NoticeDAO();
							for (int i = 0; i < noticeList.size(); i++) {
								NoticeBean noticeBean = (NoticeBean) noticeList.get(i);
						%>
						<tr>
							<td><%=noticeBean.getNoticeNum()%></td>
							<td>
								<%
									if (noticeBean.getNoticeReLev() != 0) {
								%> <%
 	for (int a = 0; a <= noticeBean.getNoticeReLev() * 2; a++) {
 %> &nbsp; <%
 	}
 %> <%
 	}
 %> <a
								href="/NoticeDetailAction.no?num=<%=noticeBean.getNoticeNum()%>&userNick=<%=Nick%>"
								style="color: black; text-decoration: none;"><%=noticeBean.getNoticeSubject()%></a>
								<%-- 	<%if(NoticeDAO.getAnswerCount(noticeBean.getNoticeNum()) != 0){ %>
															&nbsp;<a href="javascript:bbsanswerblank(<%=noticeBean.getNoticeNum() %>);">[<%=noticeDAO.getAnswerCount(noticeBean.getNoticeNum()) %>]</a>
															<%} %> --%> <!-- 위에 주석처리한게 댓글 몇개적혔는지 보여주는거다 -->
							</td>
							<td><%=noticeBean.getNoticeNick()%></td>
							<td><%=noticeBean.getNoticeDate()%></td>
							<td><%=noticeBean.getNoticeReadcount()%></td>
						</tr>
						<%
							}
						%>
					</tbody>
					<tfoot>
						<tr>
							<td style="background-color: #eeeeee; text-align: center;"></td>
							<td style="background-color: #eeeeee; text-align: center;">
								<%
									if (noticeviewcount == 10) {
								%> <%
 	if (opt == null && condition == null) {
 %> <%
 	if (nowpage <= 1) {
 %> [이전]&nbsp;&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage - 1%>&noticeviewcount=<%=noticeviewcount%>">[이전]</a>&nbsp;&nbsp;&nbsp;
								<%
									}
								%> <!-- ?를하고 page라는 임시변수를 만들어서 변수에 nowpage-1이라는 값과 bbsviewcount에 bbsviewcount라는 값을 넣어주었다
																이렇게 하면 BbsListAction.bo에 파라미터로 보낼수가 있다 --> <%
 	for (int a = startpage; a <= endpage; a++) {
 				if (a == nowpage) {
 %> <%=a%>&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=a%>&noticeviewcount=<%=noticeviewcount%>"><%=a%></a>&nbsp;&nbsp;
								<%
									}
								%> <%
 	}
 %> <%
 	if (nowpage >= maxpage) {
 %> &nbsp;&nbsp;[다음] <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage + 1%>&noticeviewcount=<%=noticeviewcount%>">&nbsp;&nbsp;[다음]</a>
								<%
									}
								%> <%
 	} else {
 %> <%
 	if (nowpage <= 1) {
 %> [이전]&nbsp;&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage - 1%>&opt=<%=opt%>&condition=<%=condition%>&noticeviewcount=<%=noticeviewcount%>">[이전]</a>&nbsp;&nbsp;&nbsp;
								<%
									}
								%> <%
 	for (int a = startpage; a <= endpage; a++) {
 				if (a == nowpage) {
 %> <%=a%>&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=a%>&opt=<%=opt%>&condition=<%=condition%>&noticeviewcount=<%=noticeviewcount%>"><%=a%></a>&nbsp;&nbsp;
								<%
									}
								%> <%
 	}
 %> <%
 	if (nowpage >= maxpage) {
 %> &nbsp;&nbsp;[다음] <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage + 1%>&opt=<%=opt%>&condition=<%=condition%>&noticeviewcount=<%=noticeviewcount%>">&nbsp;&nbsp;[다음]</a>
								<%
									}
								%> <%
 	}
 %> <%
 	} else if (noticeviewcount == 20) {
 %> <%
 	if (opt == null && condition == null) {
 %> <%
 	if (nowpage <= 1) {
 %> [이전]&nbsp;&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage - 1%>&noticeviewcount=<%=noticeviewcount%>">[이전]</a>&nbsp;&nbsp;&nbsp;
								<%
									}
								%> <%
 	for (int a = startpage; a <= endpage; a++) {
 				if (a == nowpage) {
 %> <%=a%>&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=a%>&noticeviewcount=<%=noticeviewcount%>"><%=a%></a>&nbsp;&nbsp;
								<%
									}
								%> <%
 	}
 %> <%
 	if (nowpage >= maxpage) {
 %> &nbsp;&nbsp;[다음] <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage + 1%>&noticeviewcount=<%=noticeviewcount%>">&nbsp;&nbsp;[다음]</a>
								<%
									}
								%> <%
 	} else {
 %> <%
 	if (nowpage <= 1) {
 %> [이전]&nbsp;&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage - 1%>&opt=<%=opt%>&condition=<%=condition%>&noticeviewcount=<%=noticeviewcount%>">[이전]</a>&nbsp;&nbsp;&nbsp;
								<%
									}
								%> <%
 	for (int a = startpage; a <= endpage; a++) {
 				if (a == nowpage) {
 %> <%=a%>&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=a%>&opt=<%=opt%>&condition=<%=condition%>&noticeviewcount=<%=noticeviewcount%>"><%=a%></a>&nbsp;&nbsp;
								<%
									}
								%> <%
 	}
 %> <%
 	if (nowpage >= maxpage) {
 %> &nbsp;&nbsp;[다음] <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage + 1%>&opt=<%=opt%>&condition=<%=condition%>&noticeviewcount=<%=noticeviewcount%>">&nbsp;&nbsp;[다음]</a>
								<%
									}
								%> <%
 	}
 %> <%
 	} else if (noticeviewcount == 50) {
 %> <%
 	if (opt == null && condition == null) {
 %> <%
 	if (nowpage <= 1) {
 %> [이전]&nbsp;&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage - 1%>&noticeviewcount=<%=noticeviewcount%>">[이전]</a>&nbsp;&nbsp;&nbsp;
								<%
									}
								%> <%
 	for (int a = startpage; a <= endpage; a++) {
 				if (a == nowpage) {
 %> <%=a%>&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=a%>&noticeviewcount=<%=noticeviewcount%>"><%=a%></a>&nbsp;&nbsp;
								<%
									}
								%> <%
 	}
 %> <%
 	if (nowpage >= maxpage) {
 %> &nbsp;&nbsp;[다음] <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage + 1%>&noticeviewcount=<%=noticeviewcount%>">&nbsp;&nbsp;[다음]</a>
								<%
									}
								%> <%
 	} else {
 %> <%
 	if (nowpage <= 1) {
 %> [이전]&nbsp;&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage - 1%>&opt=<%=opt%>&condition=<%=condition%>&noticeviewcount=<%=noticeviewcount%>">[이전]</a>&nbsp;&nbsp;&nbsp;
								<%
									}
								%> <%
 	for (int a = startpage; a <= endpage; a++) {
 				if (a == nowpage) {
 %> <%=a%>&nbsp;&nbsp; <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=a%>&opt=<%=opt%>&condition=<%=condition%>&noticeviewcount=<%=noticeviewcount%>"><%=a%></a>&nbsp;&nbsp;
								<%
									}
								%> <%
 	}
 %> <%
 	if (nowpage >= maxpage) {
 %> &nbsp;&nbsp;[다음] <%
 	} else {
 %> <a
								href="/NoticeListAction.no?page=<%=nowpage + 1%>&opt=<%=opt%>&condition=<%=condition%>&noticeviewcount=<%=noticeviewcount%>">&nbsp;&nbsp;[다음]</a>
								<%
									}
								%> <%
 	}
 %> <%
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
							<td style="background-color: #eeeeee; text-align: center;">
							</td>
							<%
								if (userID == null) {
							%>
							<td style="background-color: #eeeeee; text-align: center;">
							</td>
							<%
								} else if (userID.equals("admin")) {
							%>
							<td style="background-color: #eeeeee; text-align: center;"><a
								href="/write.no" class="btn btn-primary pull-right">글쓰기</a></td>
							<%
								} else {
							%>
							<td style="background-color: #eeeeee; text-align: center;">
							</td>
							<%
								}
							%>


						</tr>
					</tfoot>
				</table>

			</form>
		</div>
	</div>
	<div style="text-align: center; background-color: #FE9A2E; margin-top: 150px;">

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