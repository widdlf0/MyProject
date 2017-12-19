<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/usercheck.js"></script>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<title>JSP 게시판 웹 사이트</title>

<script>
localStorage.clear();
</script>

<script>
function mySubmit() {
		var emailname = $("#emailname").val()+"@"+$("#emailvalue").val();
		console.log(emailname);
		
	   window.open("/SendMail.us?emailname="+emailname, "", "width=700, height=400, status=1");

	    }
	    
function aa(){
	
	var f=document.join;
	   f.method="post";
//	   method="post";
	   f.action="/userjoinAction.us";
	   f.target="_self";
	  // f.action=window.open("/SendMail.me","chkForm",
   //"width=600, height=600, resizable = no, scrollbars = no"); 
	  
	  
	 if (!document.join.userID.value) {
		//alert("아이디를 입력하시오.");
		/* alert()는 알람창을 띄운다. */
		document.join.userID.focus();
		return false;
	}
	
	if (!document.join.userPassword.value) {
		//alert("비밀번호를 입력하시오.");
		document.join.userPassword.focus();
		return false;
	}
	
	if (!document.join.userName.value) {
		//alert("이름을 입력하시오.");
		document.join.userName.focus();
		return false;
	}
	
	if (!document.join.userGender.value) {
		//alert("성별을 입력하시오.");
		document.join.userGender.focus();
		return false;
	}
	
	if (!document.join.userPhone1.value) {
		//alert("핸드폰 번호를 입력하시오");
		document.join.userPhone1.focus();
		return false;
	}
	
	if (!document.join.userPhone2.value) {
		//alert("핸드폰 번호를 입력하시오");
		document.join.userPhone2.focus();
		return false;
	}
	
	if (!document.join.userPhone3.value) {
		//alert("핸드폰 번호를 입력하시오");
		document.join.userPhone3.focus();
		return false;
	}
	
	if (!document.join.userEmail1.value) {
		//alert("이메일을 입력하시오.");
		document.join.userEmail1.focus();
		return false;
	}
	
	if (!document.join.userEmail2.value) {
		//alert("이메일을 입력하시오.");
		document.join.userEmail2.focus();
		return false;
	}
		//var a100b = sessionStorage.getItem("a100");
	
		var year = localStorage.getItem('gilbird');
		
		 if (year==null) {
			alert("이메일 인증을 해주세요.");
			//document.getElementById("userEmailck2").outerHTML = "이메일인증을 해주세요";
			//alert(year);
			document.join.userEmail1.focus();
			//localStorage.clear();
			return false;
		}
			
// 		if(year==2){
				
// 			 document.getElementById("userEmailck2").outerHTML = "인증완료";
				
// 	    }
		
		 
		//이메일 인증이 확인됬을시에 회원가입이 되야하는데
		//지금은 안되고있다
		//한번이상 클릭이 됬을시에 나오게 해야한다
		if (!document.join.userAddress1.value) {
		alert("주소를 입력하시오.");
		document.join.userAddress1.focus();
		return false;
		}else{
			//alert("축하합니다. 회원가입 되셨습니다.");
			localStorage.clear();
		}
	   f.submit();
	   //드디어 성공했다
	   //이메일 인증창만 타겟 블링크로 걸어서 새창으로 띄워주면 되는데
	   //근데 그렇게 하면 문제점이 회원가입을 눌러도 이메일 인증창으로 넘어가버린다
	   //그래서 원래 회원가입은 submit로 되있었는데
	   //그거를 버튼으로 만들어서 onclick를 걸어주고 aa()에서
	   //원래 가는곳으로 액션으로 해주고
	   //타겟을 셀프로 하니까 성공했다
	   //이제 이쪽은 다 끝났고
	   //이제는 이메일 인증을 누르지 않아도 회원가입이 되는데
	   //이메일이 인증됬을시에만 회원가입이 되도록 해야한다
    
}
	    

function check() {

	if (!document.join.userID.value) {
		alert("아이디를 입력하시오.");
		/* alert()는 알람창을 띄운다. */
		document.join.userID.focus();
		return false;
	}
	
	if (!document.join.userPassword.value) {
		alert("비밀번호를 입력하시오.");
		document.join.userPassword.focus();
		return false;
	}
	
	if (!document.join.userName.value) {
		alert("이름을 입력하시오.");
		document.join.userName.focus();
		return false;
	}
	
	if (!document.join.userGender.value) {
		alert("성별을 입력하시오.");
		document.join.userGender.focus();
		return false;
	}
	
	if (!document.join.userPhone1.value) {
		alert("핸드폰 번호를 입력하시오");
		document.join.userPhone1.focus();
		return false;
	}
	
	if (!document.join.userPhone2.value) {
		alert("핸드폰 번호를 입력하시오");
		document.join.userPhone2.focus();
		return false;
	}
	
	if (!document.join.userPhone3.value) {
		alert("핸드폰 번호를 입력하시오");
		document.join.userPhone3.focus();
		return false;
	}
	
	if (!document.join.userEmail1.value) {
		alert("이메일을 입력하시오.");
		document.join.userEmail1.focus();
		return false;
	}
	
	if (!document.join.userEmail2.value) {
		alert("이메일을 입력하시오.");
		document.join.userEmail2.focus();
		return false;
	}
	
	/* var year = localStorage.getItem('gilbird');
	 if (year==null) {
		alert("이메일 인증을 해주세요1.");
		alert(year);
		document.join.userEmail1.focus();
		//localStorage.clear();
		return false;
	} */
	
	if (!document.join.userAddress1.value) {
		//alert("주소를 입력하시오.");
		document.join.userAddress1.focus();
		return false;
	}
	
	

}

function email_change(){
	if(document.join.email.options[document.join.email.selectedIndex].value == '0'){
 		document.join.userEmail2.disabled = true;
 		document.joinform.userEmail2.value = "";
	}
	if(document.join.email.options[document.join.email.selectedIndex].value == '9'){
 		document.join.userEmail2.disabled = false;
 		document.join.userEmail2.value = "";
 		document.join.userEmail2.focus();
	}else{
 		document.join.userEmail2.disabled = false;
 		document.join.userEmail2.value = document.join.email.options[document.join.email.selectedIndex].value;
	}
}
function sample4_execDaumPostcode() {
	new daum.Postcode(
			{
				oncomplete : function(data) {
					// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

					// 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
					// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
					var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
					var extraRoadAddr = ''; // 도로명 조합형 주소 변수

					// 법정동명이 있을 경우 추가한다. (법정리는 제외)
					// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
					if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
						extraRoadAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if (data.buildingName !== '' && data.apartment === 'Y') {
						extraRoadAddr += (extraRoadAddr !== '' ? ', '
								+ data.buildingName : data.buildingName);
					}
					// 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
					if (extraRoadAddr !== '') {
						extraRoadAddr = ' (' + extraRoadAddr + ')';
					}
					// 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
					if (fullRoadAddr !== '') {
						fullRoadAddr += extraRoadAddr;
					}

					// 우편번호와 주소 정보를 해당 필드에 넣는다.
					document.getElementById('sample4_postcode').value = data.zonecode; //5자리 새우편번호 사용
					document.getElementById('sample4_roadAddress').value = fullRoadAddr;
					document.getElementById('sample4_jibunAddress').value = data.jibunAddress;

					// 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
					if (data.autoRoadAddress) {
						//예상되는 도로명 주소에 조합형 주소를 추가한다.
						var expRoadAddr = data.autoRoadAddress
								+ extraRoadAddr;
						document.getElementById('guide').innerHTML = '(예상 도로명 주소 : '
								+ expRoadAddr + ')';

					} else if (data.autoJibunAddress) {
						var expJibunAddr = data.autoJibunAddress;
						document.getElementById('guide').innerHTML = '(예상 지번 주소 : '
								+ expJibunAddr + ')';

					} else {
						document.getElementById('guide').innerHTML = '';
					}
				}
			}).open();
}
// onkeyup="checkpass()"
// function checkpass() {

// 	if (document.join.userPassword.value
// 			&& document.join.userPasswordcheck.value) {
// 		/* 비밀번호와 비밀번호 확인란을 둘다 입력하였을시 들어옴 */

// 		if (document.join.userPassword.value == document.join.userPasswordcheck.value) {
// 			/* 비밀번호의 값과 비밀번호 확인란의 값이 일치할 경우 들어옴 */

// 			document.join.strpass.value = "일치";

// 			var x = document.getElementById("strpass1");
// 			/* id가 strpass1인 것의 값을 불러와서 x에 저장한다. */

// 			x.style.fontSize = "15px";
// 			/* x의 글자 사이즈를 20px로 한다. */

// 			x.style.color = "yellowgreen";
// 			/* x의 글자색을 yellowgreen으로 한다. */

// 		} else {
// 			/* 비밀번호의 값과 비밀번호 확인란의 값이 불 일치할 경우 들어옴 */

// 			document.join.strpass.value = "불 일치";

// 			var x = document.getElementById("strpass1");
// 			/* id가 strpass1인 것의 값을 불러와서 x에 저장한다. */

// 			x.style.fontSize = "15px";
// 			/* x의 글자 사이즈를 20px로 한다. */

// 			x.style.color = "red";
// 			/* x의 글자색을 red로 한다. */

// 		}
// 	} else {
// 		/* 비밀번호와 비밀번호 확인란 둘중 하나가 비었을 경우 들어옴 */

// 		document.join.strpass.value = "       ";

// 	}

// }
</script>

</head>
<body style="background: url('../images/background.jpg'); background-repeat: no-repeat; background-size: 100% 100%; overflow:auto;">	
		
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
												<li><a href="/login.us">로그인</a></li>
												<li>----------------------</li>
												<li class="active"><a href="/join.us">회원가입</a></li>
										</ul>
								</li>
						</ul>
				</div>
		</nav>
		<div class="container" >
		
				<div class="col-lg-4"></div>
				<div class="col-lg-4">
						<div class="jumbotron" style="padding-top: 20px;">
								<form method="post" action="/userjoinAction.us" name="join" onsubmit="return check()">
										<h3 style="text-align: center;">회원가입</h3>
										<div class="form-group">
												<input type="text" class="form-control" placeholder="아이디" name="userID" maxlength="20"  id="userID" autocomplete="off">
												<span id="userIDcheck" style="display:none"></span>
										</div>
										<div class="form-group">
												<input type="password" class="form-control" placeholder="비밀번호" name="userPassword" maxlength="20" id="userPassword" autocomplete="off">
												<input type="password" class="form-control" placeholder="비밀번호 확인" name="userPasswordcheck" maxlength="20" id="userPasswordcheck" autocomplete="off">
<!-- 												<input type="text" name="strpass" id="strpass1" class="form-control"  style="border: none; " readonly> -->
												<span id="userPasswordcheck2" style="border: none; "></span>
										</div>
										<div class="form-group">
												<input type="text" class="form-control" placeholder="이름" name="userName" maxlength="20" autocomplete="off" id="test01">
										</div>
										<div class="form-group">
												<input type="text" class="form-control" placeholder="닉네임" name="userNick" maxlength="20" id="userNick" autocomplete="off">
												<span id="userNickcheck" style="display:none"></span>
										</div>
										<div class="form-group">휴대전화<br/>
										
												<input type="text" class="form-phone" placeholder="010" name="userPhone1" maxlength="3" size="1" >-
												<input type="text" class="form-phone" placeholder="1111" name="userPhone2" maxlength="4" size="2" >-
												<input type="text" class="form-phone" placeholder="2222" name="userPhone3" maxlength="4" size="2">
											
										</div>
										<div class="form-group" style="text-align: center;">
												<div class="btn-group" data-toggle="buttons">
														<label class="btn btn-primary active">
																<input type="radio" name="userGender" autocomplete="off" value="남자" checked />남자
														</label>
														<label class="btn btn-primary">
																<input type="radio" name="userGender" autocomplete="off" value="여자" />여자
														</label>
												</div>
										</div>
										<div class="form-group">생년월일<br/>
												<select name="userDate1" class="form-phone"  style="width: 60px; padding: 1px 2px;">
													<%
														for (int i = 2017; i >= 1910; i--) {
													%>
						
													<option value="<%=i%>"><%=i%></option>
						
													<%
														}
													%>
												</select>년&nbsp; 
												<select name="userDate2" class="form-phone" style="width: 50px; padding: 2px 4px;">
													<%
														for (int i = 12; i >= 1; i--) {
													%>
						
													<option value="<%=i%>"><%=i%></option>
						
													<%
														}
													%>
												</select>월&nbsp; 
												<select name="userDate3" class="form-phone" style="width: 50px; padding: 2px 4px;" >
													<%
														for (int i = 31; i >= 1; i--) {
													%>
						
													<option value="<%=i%>"><%=i%></option>
						
													<%
														}
													%>
											</select>일&nbsp;
										</div>
										<div class="form-group">
												<input type="text" class="form-phone" placeholder="이메일" name="userEmail1" maxlength="50" style="width: 99%;" id="emailname">
												<input type="text" class="form-phone" name="userEmail2" size="7" value=""  id="emailvalue" disabled >
												<select name="email" onchange="email_change()" class="form-phone">
							  						<option value="0" >주소 선택&nbsp;&nbsp;&nbsp;</option>
							   						<option value="naver.com">naver.com</option>
							   						<option value="daum.net">daum.net</option>
							   						<option value="gmail.com">gmail.com</option>
							   						<option value="nate.com">nate.com</option> 
							   						<option value="9">직접입력</option>
							   					</select>
							   					<input type="button" id="emailcheck" name="emailcheck" class="btn btn-primary form-phone" onclick="javascript:mySubmit()" value="메일인증받기">
										</div>
										<div class="form-group">
												<input type="text" name="userAddress1" class="form-phone"
													id="sample4_postcode" placeholder="우편번호" size="5"
													onclick="sample4_execDaumPostcode()"  readonly> 
												<input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"  class="btn btn-primary form-phone" ><br>
												<input type="text" name="userAddress2" id="sample4_roadAddress" class="form-phone" style="width: 100%;"
													placeholder="도로명주소" onclick="sample4_execDaumPostcode()"><br>
												<input type="text" name="userAddress3" id="sample4_jibunAddress" class="form-phone" style="width: 100%;"
													placeholder="지번주소" onclick="sample4_execDaumPostcode()"> <span
													id="guide"></span>
										</div>
										<input type="submit" class="btn btn-primary form-control" value="회원가입" onclick="javascript:aa()"  disabled="disabled" id="joinbtn" name="joinbtn">
										
								</form>
						</div>
				</div>
				<div class="col-lg-4"></div>
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