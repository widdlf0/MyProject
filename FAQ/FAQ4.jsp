<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="user.db.UserBean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>OUIF : FAQ</title>
<link rel="stylesheet" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="faq.css"/>
<!-- 각자하면 다 적용되는데 css 2개를 적용시키면 뭔가가 겹쳐서
자주 묻는 질문이 안나오게 된다 -->
<meta name="viewport" content="width=device-width", initial-scale="1">
<script type="text/javascript" src=".././js/jquery-1.6.4.min.js"></script>
<script>
function fnShowContent(idx)
{
	var nowState = $('#' + idx + '_content').attr('style');
	if (nowState)
	{
		$('#' + idx + '_content').show();
		$('#' + idx + '_content').attr('style', "");
	}	
	else
	{
		$('#' + idx + '_content').hide();
		
	}
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
								<li class="active"><a href="/FAQ/FAQ.jsp">FAQ</a></li>
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
<a href="FAQ.jsp"  class="btn btn-primary" style="width: 24%;">자주묻는질문</a>&nbsp;&nbsp;
<a href="FAQ2.jsp"  class="btn btn-primary" style="width: 24%;">맛집검색 및 사용법</a>&nbsp;&nbsp;
<a href="FAQ3.jsp"  class="btn btn-primary" style="width: 24%;">이 사이트의 용도</a>&nbsp;&nbsp;
<a href="FAQ4.jsp"  class="btn btn-primary" style="width: 24%;">기타</a>
      <!-- //TAB Object -->
      <!-- FAQ Object -->
      <div class="faq">
        <ul>
        <li class="article">
            	<p class="q" onclick="fnShowContent('50')">
            	<a href="#" class="trigger">Q: [기타] 여기는 뭐하는 사이트인가요?</a></p>
            	<div id="50_content" style="display:none;">
	            	<p class="a">A: Q. 관리자가 맛집을 등록하는 추천하는 것이 아닌 사용자가 직접 맛집을 등록하고 평점과 후기를 쓸수있는 사이트입니다.</p>
	            	</div>
          	</li>
          	<li class="article">
            	<p class="q" onclick="fnShowContent('49')">
            	<a href="#" class="trigger">Q: [기타] 맛집등록은 어떻게 하나요?</a></p>
            	<div id="49_content" style="display:none;">
	            	<p class="a">A: 맛집검색에 들어가셔서 맛집등록을 누르시면 됩니다</p>
	            	</div>
          	</li>
        	<li class="article">
            	<p class="q" onclick="fnShowContent('48')">
            	<a href="#" class="trigger">Q: [기타] 맛집후기나 평점은 어디서 보나요?</a></p>
            	<div id="48_content" style="display:none;">
	            	<p class="a">A: 맛집검색에 들어가시면 자기 주변의 맛집을 볼수가 있는데 클릭을 하시면 후기와 평점이 나오게 됩니다</p>
	            	</div>
          	</li>
          	<li class="article">
            	<p class="q" onclick="fnShowContent('47')">
            	<a href="#" class="trigger">Q: [기타] 사다리게임은 왜 있는건가요?</a></p>
            	<div id="47_content" style="display:none;">
	            	<p class="a">A: 간단한 내기로 밥을사거나 할때 쓰시라고 만들어 두었습니다</p>
	            	</div>
          	</li>
          	<li class="article">
            	<p class="q" onclick="fnShowContent('46')">
            	<a href="#" class="trigger">Q: [기타] 고객센터는 무슨 용도인가요?</a></p>
            	<div id="46_content" style="display:none;">
	            	<p class="a">A: 관리자한테 하고싶은말을 이메일로 보낼수있게 만들어 두었습니다</p>
	            	</div>
          	</li>
          	<li class="article">
            	<p class="q" onclick="fnShowContent('45')">
            	<a href="#" class="trigger">Q: [기타] 평점을 매기면 여러사람들이 입력할텐데 평균이 매겨지는 건가요?</a></p>
            	<div id="45_content" style="display:none;">
	            	<p class="a">A: 네 평균을 계산하여서 입력되게 만들어 두었습니다</p>
	            	</div>
          	</li>
          	<li class="article">
            	<p class="q" onclick="fnShowContent('44')">
            	<a href="#" class="trigger">Q: [기타] 추천해주실만한 맛집 있나요?</a></p>
            	<div id="44_content" style="display:none;">
	            	<p class="a">A: 인천 남구 학익동 육심 추천합니다</p>
	            	</div>
          	</li>
          	<li class="article">
            	<p class="q" onclick="fnShowContent('43')">
            	<a href="#" class="trigger">Q: [기타] 이 기능좀 추가시켜주세요</a></p>
            	<div id="43_content" style="display:none;">
	            	<p class="a">A: 추후에 업데이트 예정입니다</p>
	            	</div>
          	</li>
		</ul>          	
	</div>
<br />
<!-- <button type="button" onclick="$('link').attr('href','')">CSS(X)</button>
<button type="button" onclick="$('link').attr('href','faq.css')">CSS(O)</button> -->
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script> 
<script type="text/javascript" src="faq.js"></script> -->
<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/bootstrap.js"></script>
</body>
</html>
