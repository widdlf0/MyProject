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
		<a class="btn btn-primary" href="javascript:bbsanswerview(<%=bbsBean.getBbsNum() %>);" style="width: 100%;">본문 보기</a>
								<table class="table1" style=" border: 1px solid #dddddd; ">
										<thead id="bbstheadanswer"></thead>
										<tbody id="bbstbodyanswer"></tbody>
										<tfoot>
												<tr>
														<td rowspan="2" colspan="3">
															<form name="answerform">
																<input type="hidden" id="answerNick" value="<%=Nick%>">
																<textarea rows="2" cols="3" style="width: 88%;" id="textanswer" name="textanswer"></textarea>
																<%if(userID != null){ %>
																<a class="btn btn-primary pull-right" style="padding: 10px 15px; width: 10%;" id="textanswerbtn">등록</a>
																<%}else{ %>
																<a class="btn btn-primary pull-right" style="padding: 10px 15px; width: 10%;" href="javascript:logincheck3();">등록</a>
																<%} %>
															</form>
														</td>
												</tr>
												
										</tfoot>
								</table>
						
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
