<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache"> 
 <meta http-equiv="Pragma" content="no-cache">
<title>Insert title here</title>
</head>
<%
String content2 = (String)session.getAttribute("content2");
// out.println(content2);
%>
<script type="text/javascript">

				<%-- sessionStorage.setItem('a100',document.Auth.AuthNum.value==<%=content2%>); --%>
				//var position = sessionStorage.getItem('a100');
				//alert(position);
				//var a = 1;
				//sessionStorage.setItem('a100',nana);
	function AuthNum1(){
			
		//자바스크립트에서 세션 사용하기
		//sessionStorage.setItem('저장할 이름 - 문자열', '저장할 객체');
        //var position = sessionStorage.getItem('저장된 이름');
			if(document.Auth.AuthNum.value==<%=content2%>){
				var a = 1;
				
				alert("인증완료!");
				a++;
				
				localStorage.setItem('gilbird', a);
// 				alert(a);
			    self.close(); 
				//location.href="javascript:history.back()";
			}else if(document.Auth.AuthNum.value==""){
				
				alert("인증번호를 입력해주세요.");
				
				
			}else{
				
				alert("인증번호가 일치하지 않습니다.");
				
			}
				
	}

</script>

<body>

<form method="post" name="Auth">

<input type="text" name="AuthNum" placeholder="인증번호" value="">
<input type="button" value="인증번호 확인" onclick="AuthNum1()">
<br>
<input type="hidden" name="a1234" />
<h3>인증확인이 완료되면 회원가입창으로 이동됩니다</h3>
<!-- <a href="javascript:history.go(-1)">돌아가기</a> -->
</form>
</body>
</html>