<%--------------------------------------------------------------------------------
	* 화면명 : Smart Editor 2.8 에디터 연동 페이지
	* 파일명 : /page/test/index.jsp
--------------------------------------------------------------------------------%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs.image.db.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">

<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.js"></script>

<title>JSP 게시판 웹 사이트</title>

<!-- Favicon -->
<!-- <link rel="shortcut icon" href="favicon.ico" /> -->

<!-- jQuery -->
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
<script src="/SE2/js/HuskyEZCreator.js"></script>
<script type="text/javascript">
var oEditors = []; // 개발되어 있는 소스에 맞추느라, 전역변수로 사용하였지만, 지역변수로 사용해도 전혀 무관 함.

$(document).ready(function() {
	// Editor Setting
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors, // 전역변수 명과 동일해야 함.
		elPlaceHolder : "imageContent", // 에디터가 그려질 textarea ID 값과 동일 해야 함.
		sSkinURI : "/SE2/SmartEditor2Skin.html", // Editor HTML
		fCreator : "createSEditor2", // SE2BasicCreator.js 메소드명이니 변경 금지 X
		htParams : {
			// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseToolbar : true,
			// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,
			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true, 
		}
	});

	// 전송버튼 클릭이벤트
	$("#savebutton").click(function(){
		//if(confirm("저장하시겠습니까?")) {
			// id가 smarteditor인 textarea에 에디터에서 대입
			oEditors.getById["imageContent"].exec("UPDATE_CONTENTS_FIELD", []);

			var answer;
			answer = confirm("글을 수정 하시겠습니까?");
			
			if(answer == true){
				alert("정상적으로 글이 수정 되었습니다.");
				// 이부분에 에디터 validation 검증
				if(validation()) {
					$("#imageModify").submit();
				}
			}else{
				return;
			}
			
		//}
	})
});

// 필수값 Check
function validation(){
	var contents = $.trim(oEditors[0].getContents());
	if(contents === '<p>&nbsp;</p>' || contents === ''){ // 기본적으로 아무것도 입력하지 않아도 <p>&nbsp;</p> 값이 입력되어 있음. 
		alert("내용을 입력하세요.");
		oEditors.getById['imageContent'].exec('FOCUS');
		return false;
	}

	return true;
}

// 글쓰기 저장 & 수정
// function fWrite() {
// 	if(validation()){
// 		if(confirm("저장하시겠습니까?")) {
// 			oEditors[0].exec("UPDATE_CONTENTS_FIELD", []); // Editor내용을 DB에 가져가기 위해 필수로 작성!
// 														   // oEditors << 전역변수로 선언한 변수명과 동일해야 함.
// 			$("#boardForm").attr('action','${pageContext.request.contextPath}/save').submit();
// 		}
// 	}
// }
</script>
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
// 											document.bbswrite.submit();
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
							
							
<body>
		<%
				String imagename = null;
				if(session.getAttribute("imagename") != null){
					imagename = (String) session.getAttribute("imagename");
					
					System.out.println("세션 이미지 넘오옴" + imagename);
				}
		
				ImageBean imgBean = (ImageBean)request.getAttribute("imgBean");
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
						<a class="navbar-brand" href="/main.us"><img src="images/Logo.png"></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav">
								<li><a href="/main.us">메인</a></li>
								<li><a href="/NoticeListAction.no">공지사항</a></li>
								<li><a href="/maps.mp">맛집 검색</a></li>
								<li><a href="/BbsListAction.bo">자유 게시판</a></li>
								<li class="active"><a href="/ImgListAction.img">이미지 게시판</a></li>
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
						<form method="post" action="/ImgModifyAction.img"  name="imageModify" id="imageModify">
						<input type="hidden" name="imgNick" value="<%=Nick %>">
						<input type="hidden" name="imgNum" value="<%=imgBean.getImgNum() %>">
								<table class="table table-stried" style="text-align: center; border: 1px solid #dddddd">
										<thead>
												<tr>
													<th style="background-color: #eeeeee; text-align: center;">이미지 게시판 글 수정 양식</th>
												</tr>
										</thead>
										<tbody>
												<tr>
														<th><input type="text" class="form-control" placeholder="글 제목" name="imgSubject" maxlength="50" value="<%=imgBean.getImgSubject() %>"></th>
												</tr>
												<tr>
														<td><textarea class="form-control" placeholder="글 내용" name="imageContent"  id="imageContent" maxlength="3000" style="height: 600px;">
														<%=imgBean.getImgContent() %>
														</textarea>
														</td>
												</tr>
												
										</tbody>
								</table>
<!-- 								<a class="btn btn-primary pull-right" onclick="writeform();">글쓰기</a> -->
								<a class="btn btn-primary pull-right"  id="savebutton">글 수정</a>
								<input type="button" class="btn btn-primary pull-left" onclick="backform()" value="뒤로">
								
						</form>
								
						
				</div>
		</div>
</body>
</html>