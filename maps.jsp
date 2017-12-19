<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="user.db.UserBean"%>
<%@ page import="java.util.List"%>
<%@ page import="map.db.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/googlemaps.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<!-- <script src="js/mapcontent.js"></script> -->
<script src="js/mapAll.js"></script>
<!-- <script src="js/testmap.js"></script> -->
<!-- jQuery -->
<!-- <script type="text/javascript" src="/js/jquery.js"></script> -->
<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
<script src="/SE2/js/HuskyEZCreator.js"></script>

<title>JSP 게시판 웹 사이트</title>

</head>
<body style="overflow-x: hidden; overflow: scroll;">
	<%
		String userID = null;
		String Nick = null;

		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
			Nick = (String) session.getAttribute("userNick");

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
				<li><a href="/NoticeListAction.no">공지사항</a></li>
				<li class="active"><a href="/maps.mp">맛집 검색</a></li>
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


	<style>
#map {
	height: 97%;
	width: 100%;
}

#map_wrap {
	position: fixed;
	top: 65px;
	left: 15px;
	bottom: 15px;
	width: 40%;
	width: calc(41.66666% - 20px);
	height: auto;
	z-index: 500;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.33);
	background-color: white;
	max-width: 900px;
}

#map_add_store {
	height: 200px;
}

ul {
	list-style: none;
	padding-left: 0px;
}

.star_rating {
	font-size: 0;
	letter-spacing: -4px;
}

.star_rating a {
	font-size: 22px;
	letter-spacing: 0;
	display: inline-block;
	margin-left: 5px;
	color: #ccc;
	text-decoration: none;
}

.star_rating a:first-child {
	margin-left: 0;
}

.star_rating a.on {
	color: #777;
}
</style>
	<!-- 위치검색  -->
	<div class="row">
		<div class="col-md-5">
			<div id="map_wrap">
				<div id="map"></div>
				<p>
					<em>지도를 클릭해주세요!</em>
				</p>
			</div>
		</div>
		<div class="col-md-7">
			<input type="text" id="input_search_location" class="form-phone"
				placeholder="위치 검색" style="width: 80%;">
			<button type="button" style="padding: 8px 15px; margin-right: 8px;"
				class="btn btn-primary" id="btn_search_location"
				onclick="codeAddress()">위치 검색</button>
			<%
				if (userID != null) {
			%>
			<button type="button" style="padding: 8px 15px;"
				class="btn btn-primary" data-toggle="modal" data-target="#add_store"
				id="storebtn">맛집 등록</button>
			<%
				} else {
			%>
			<button type="button" style="padding: 8px 15px;"
				class="btn btn-primary" onclick="javascript:logincheck2()"
				id="storebtn">맛집 등록</button>
			<%
				}
			%>
			<br /> <br />

			<table class="table" id="">
				<thead id="mainMenu">
					<tr>
						<th style="width: 13%;">카테고리</th>
						<th style="width: 17%;">가게 이름</th>
						<th style="width: 25%;">인기 메뉴 & 가격</th>
						<th style="width: 17%;">전화번호</th>
						<th style="width: 14%;">배달여부</th>
						<th style="width: 15%;">평점</th>
					</tr>
				</thead>
				<tbody id="test007"></tbody>
			</table>

			<!-- 						<form name="maps" method="post" action="./MapInsert.mp"> -->
			<!-- 							<input type="text" name="foodstore" size="40" placeholder="맛집 이름"><br> -->
			<!-- 							<input type="text" name="clickLatlng" size="40" placeholder="위치"><br> -->
			<!-- 							<input type="text" name="lat" size="40" placeholder="위도"> -->
			<!-- 							&nbsp; <input type="text" name="lng" size="40" placeholder="경도"><br> -->
			<!-- 							<input type="submit" value="등록하기"> -->
			<!-- 						</form> -->
		</div>
	</div>


	<!-- 음식점 추가 modal -->
	<div class="modal fade" id="add_store" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-lg" role="document">
			<form id="saveDataForm" class="modal-content" action=""
				name="saveDataForm">
				<div class="modal-header">
					<h5 class="modal-title" id="add_store_label">
						맛집 등록
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
					</h5>

				</div>
				<div class="modal-body">

					<div style="margin-bottom: 15px; font-size: 13px;">
						<span class="required">*</span> : 필수 항목
					</div>
					<div class="form-group">
						<input type="hidden" name="mapNick" value="<%=Nick%>">
					</div>
					<div class="form-group">
						<label>카테고리<span class="required">*</span></label> <select
							class="form-control" id="mapcategori" name="mapCategori">
							<option value="한식">한식</option>
							<option value="양식">양식</option>
							<option value="중식">중식</option>
							<option value="일식 & 돈까스">일식 & 돈까스</option>
							<option value="치킨">치킨</option>
							<option value="피자">피자</option>
							<option value="분식 & 디저트">분식 & 디저트</option>
							<option value="족발 & 보쌈">족발 & 보쌈</option>
							<option value="찜 & 탕류">찜 & 탕류</option>
							<option value="야식">야식</option>
							<option value="기타">기타</option>
						</select> <small class="form-text text-muted">해당하는 메뉴가 없으면 관리자에게
							문의해주세요.</small>
					</div>
					<div class="form-group">
						<label>가게 이름<span class="required">*</span></label> <input
							type="text" class="form-control" id="mapRstName"
							placeholder="가게 이름" required="" maxlength="30" name="mapRstName">
					</div>

					<div class="form-group">
						<label>인기 메뉴 & 가격대</label> <input type="text" class="form-control"
							id="mapMenu" placeholder="인기 메뉴 & 가격대" required="" maxlength="30"
							name="mapMenu">
						<!-- 					<small class="form-text text-muted">&gt;가 붙으면 초과를, &lt;가 붙으면 미만을 뜻 합니다.</small> -->
					</div>

					<div class="form-group">
						<label>전화 번호<span class="required">*</span></label> <input
							type="text" class="form-control" id="mapTel" placeholder="연락처"
							required="" maxlength="30" name="mapTel"> <small
							class="form-text text-muted">배달 주문이 가능한 전화 번호를 적어 주세요</small>
					</div>

					<div class="form-group">
						<label>배달 여부</label>
						<table>
							<tr>
								<td>오픈 시간</td>
								<td><select class="form-control" id="mapMove" required=""
									name="mapMove1">
										<%
											for (int i = 1; i <= 24; i++) {
										%>

										<option value="<%=i%>:00"><%=i%>:00
										</option>

										<%
											}
										%>
										<option value="배달불가">배달불가</option>
								</select></td>
								<td>&nbsp;&nbsp;마감 시간</td>
								<td><select class="form-control" id="mapMove2" required=""
									name="mapMove2">
										<%
											for (int i = 1; i <= 24; i++) {
										%>

										<option value="<%=i%>:00"><%=i%>:00
										</option>

										<%
											}
										%>
										<option value="배달불가">배달불가</option>
								</select></td>
							</tr>
						</table>
					</div>

					<div class="form-group">
						<label>맛집 위치<span class="required">*</span></label> <input
							type="text" name="lat" id="input_add_map_lat" required=""
							style="width: 0; border: none; height: 0;"> <input
							type="text" name="lng" id="input_add_map_lng" required=""
							style="width: 0; border: none; height: 0;"> <input
							type="text" name="address" id="address" required=""
							style="width: 0; border: none; height: 0;"> <br /> <input
							type="text" class="form-phone" id="input_add_store_map"
							placeholder="위치 또는 장소 검색..." autocomplete="off"
							style="width: 90%;">
						<button class="btn btn-primary pull-right" id="btn_add_store_map"
							type="button">검색</button>

						<div id="map_add_store"></div>
						<small class="form-text text-muted">클릭하거나 검색으로 위치를 선택할 수
							있습니다.</small>
					</div>

					<div class="form-group">
						<label>추가 메뉴 정보</label>
						<textarea class="form-control" id="mapRstMenu" style="width: 90%;"
							name="mapRstMenu"></textarea>
						<small class="form-text text-muted">메뉴, 가격, 음식 등을 적어주세요</small>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary" id="saveData"
						data-dismiss="modal">등록하기</button>
				</div>
			</form>
		</div>
	</div>

	<!-- 상세보기 modal -->
	<div class="modal fade" id="modal_view_store" tabindex="-1"
		role="dialog" aria-labelledby="view_store_label" aria-hidden="true">
		<div class="modal-dialog  modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h3 class="modal-title" id="view_store_label">맛집라벨</h3>
				</div>
				<div class="modal-body">

					<h4 class="text-center">정보</h4>
					<table class="table">
						<tbody>
							<tr>
								<th>가게 이름</th>
								<td class="name"></td>
							</tr>
							<tr>
								<th>주소</th>
								<td class="location"></td>
							</tr>
							<tr>
								<th>배달여부</th>
								<td class="options"></td>
							</tr>
						</tbody>
					</table>

					<h4 class="text-center">메뉴 정보</h4>
					<table class="table table-sm">
						<tbody>
							<tr>
								<th style="width: 150px;">메뉴</th>
								<td class="gpu"></td>
							</tr>

						</tbody>
					</table>

					<h4 class="text-center" style="margin-top: 30px;">평가하기</h4>
					<form name="averageans" id="averageansform">
						<input type="hidden" name="mapNumAns" class="mapNumAns"> <input
							type="hidden" name="mapNickcheck" class="mapNickcheck"
							value="<%=Nick%>">
						<div style="text-align: left;">
							<span class="star-input"> <span class="input"> <input
									type="radio" name="star-input" value="0" id="p0" checked>
									<input type="radio" name="star-input" value="1" id="p1">
									<label for="p1">1</label> <input type="radio" name="star-input"
									value="2" id="p2"> <label for="p2">2</label> <input
									type="radio" name="star-input" value="3" id="p3"> <label
									for="p3">3</label> <input type="radio" name="star-input"
									value="4" id="p4"> <label for="p4">4</label> <input
									type="radio" name="star-input" value="5" id="p5"> <label
									for="p5">5</label>
							</span> <!-- 								  	<output for="star-input"><b>0</b>점</output>						 -->
							</span> <input type="text" class="form-phone" style="width: 60%;"
								name="mapContent" id="mapContent">&nbsp;&nbsp;&nbsp;
							<%
								if (userID != null) {
							%>
							<button type="button" class="btn btn-primary"
								style="padding: 8px 15px" id="averageanswersave">
								댓글 달기
								<%
								} else {
							%>
								<button type="button" class="btn btn-primary"
									style="padding: 8px 15px" onclick="logincheck2()">
									댓글 달기
									<%
									}
								%>
								
						</div>
					</form>

					<table class="table">
						<thead>
							<tr>
								<th style="width: 20%;">평가 댓글</th>
								<th style="width: 40%;"></th>
								<th style="width: 40%;"></th>
							</tr>
						</thead>
						<tbody id="averageanswer"></tbody>
					</table>

					<script src="js/star.js"></script>

				</div>
				<div class="modal-footer">
					<a href="inquiry/inquiryemail.jsp" target="_blank" class="btn btn-warning">수정&amp;삭제요청</a>
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBTRjy2t70QXgmYAV664R5x6AkZS_mIyXs&libraries=places&callback=initMap"
		async defer>
		
	</script>

</body>
</html>