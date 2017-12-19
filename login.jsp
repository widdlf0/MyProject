<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
<!-- <script src="http://code.jquery.com/jquery-3.1.1.min.js"></script> -->
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/usercheck.js"></script>
<title>JSP 게시판 웹 사이트</title>
<script>
//로그인 체크
//var test;
$( document ).ready(function() {
  console.log( "ready!" );
 $("#loginBtn").click(function(){
	   console.log( "click!" );
	   
//	   var url="/loginAction.us";
	   var id = $("#userIDlogin").val();
	   var pw = $("#userPassword").val();
	   
	   if(id==""){
		   alert("ID를 입력해주세요!");
		   return;
	   }
	   if(pw==""){
		   alert("패스워드를 입력해주세요!");
		   return;
	   }
	   
//	    var params="userID="+id+"&userPassword="+pw;  
	  	
	    $.ajax({      
	        type:"POST",  
	        url:"/loginAction.us", 
	        data:{"userID":id, "userPassword":pw},
//	        data:params,  
	        dataType:"json",
	        success:function(args){   
	        	
//	        	test = args;
	        	
	     
	        	
//	        	for(var i=0;i<args.array.length;i++){
//	        			alert(args.array[i].action);	
//	        	}
	        	
	            if(args.result=="success"){
	            	location.href="/main.us";
	            	
	            }else if(args.result=="black"){
	            	$("#reason").text(alert(args.reason));
	            	$("#reason").show()
	            	
	            }else{
	            	$("#reason").text(alert(args.reason));
//	            	$("#reason").text(args.reason);
	            	$("#reason").show();
	            	
	            } 
	        	
	        }  
	    });  
 });
});
</script>
</head>
<body>
		
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
								<li><a href="/ImgListAction.img">이미지 게시판</a></li>
								<li><a href="/FAQ/FAQ.jsp">FAQ</a></li>
								<li><a href="/sadari/index2.jsp">사다리게임</a></li>
								<li><a href="/inquiry/inquiry.jsp">고객센터</a></li>
						</ul>
						<ul class="nav navbar-nav navbar-right">
								<li class="dropdown">
										<a href="#" class="dropdown-toggle"
												data-toggle="dropdown" role="button" aria-haspopup="true"
												aria-expanded="false">접속하기<span class="caret"></span></a>
										<ul class="dropdown-menu">
												<li class="active"><a href="/login.us">로그인</a></li>
												<li>----------------------</li>
												<li><a href="/join.us">회원가입</a></li>
										</ul>
								</li>
						</ul>
				</div>
		</nav>
		<div class="container">
				<div class="col-lg-4"></div>
				<div class="col-lg-4" style="margin-top: 100px;">
						<div class="jumbotron" style="padding-top: 20px;">
<!-- 								<form id="loginForm"  method="post"  name="login" action="/loginAction.us"> -->
										<h3 style="text-align: center;">로그인 화면</h3>
										<div class="form-group">
												<input id="userIDlogin" type="text" class="form-control" placeholder="아이디" name="userID" maxlength="20">
										</div>
										<div class="form-group">
												<input id="userPassword" type="password" class="form-control" placeholder="비밀번호" name="userPassword" maxlength="20">
												<span id="reason" style="display:none"></span>
										</div>
										<input id="loginBtn" type="button" class="btn btn-primary form-control" value="로그인"><br><br>
										
										<a href="/idfind.us"  class="btn btn-primary" style="width: 49%;">아이디 찾기</a>
										<a id="aid" href="passwordfind.us"  class="btn btn-primary" style="width: 49%;">비밀번호 찾기</a>
<!-- 								</form> -->
						</div>
				</div>
				<div class="col-lg-4"></div>
		</div>
		
</body>
</html>