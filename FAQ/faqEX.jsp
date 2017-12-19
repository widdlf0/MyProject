<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="LemonMail" />
<link rel="stylesheet" type="text/css" href="../css/lemon.css" />
<script type="text/javascript" src=".././js/jquery-1.6.4.min.js"></script>
<script type="text/javascript">
/*
	jQuery('document').ready
	(
		function()
		{
			
				$("#29_content").hide();
			
				$("#27_content").hide();
			
				$("#26_content").hide();
			
				$("#25_content").hide();
			
				$("#24_content").hide();
			
				$("#23_content").hide();
			
				$("#22_content").hide();
			
		}
	);
*/

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
	function fnQNAmail()
	{
		location.href = "./qna_mail.jsp";
	}
	
	function fnSearch()
	{
		var searchOption = document.getElementById("searchOption");
		var keyword = document.getElementById("keyword");
		location.href = "./faq.jsp?search=" + encodeURIComponent(keyword.value) + "&searchOption=" + searchOption.value;
	}
	
	function fnSeeAll()
	{
		location.href = "./faq.jsp";
	}
	
	function fnEnterLogin()
	{
	    if (event.keyCode == 13)
	    {
	    	fnSearch();
	    }
	}
</script>


<script language="JavaScript">
<!--
//새창 열기 사용자메뉴얼페이지
function na_open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable)
{
  toolbar_str = toolbar ? 'yes' : 'no';
  menubar_str = menubar ? 'yes' : 'no';
  statusbar_str = statusbar ? 'yes' : 'no';
  scrollbar_str = scrollbar ? 'yes' : 'no';
  resizable_str = resizable ? 'yes' : 'no';

  cookie_str = document.cookie;
  cookie_str.toString();

  pos_start  = cookie_str.indexOf(name);
  pos_end    = cookie_str.indexOf('=', pos_start);

  cookie_name = cookie_str.substring(pos_start, pos_end);

  pos_start  = cookie_str.indexOf(name);
  pos_start  = cookie_str.indexOf('=', pos_start);
  pos_end    = cookie_str.indexOf(';', pos_start);
  
  if (pos_end <= 0) pos_end = cookie_str.length;
  cookie_val = cookie_str.substring(pos_start + 1, pos_end);
  if (cookie_name == name && cookie_val  == "done")
    return;

  window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str);
}

// -->
</script>

<title>LemonAsp</title>
</head>
<body>
<div id="wrapper">
  <!--[if !IE]> Gnb AREA Start <![endif]-->
  		









<script type="text/javascript">
	function goLogout()
	{
		location.href = "../member/member_logout_proc.jsp";
	}
	function fnGoPayment()
	{
		location.href = ".././payment/price.jsp";	
	}
	
	function goHelp()
	{
		location.href = ".././help/notice.jsp";
	}
	
		
	 	
	function MM_preloadImages() { //v3.0
		  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
		}

		function MM_swapImgRestore() { //v3.0
		  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
		}

		function MM_findObj(n, d) { //v4.01
		  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		  if(!x && d.getElementById) x=d.getElementById(n); return x;
		}

		function MM_swapImage() { //v3.0
		  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
		}
</script>
<!--[if !IE]> Gnb AREA Start <![endif]-->
<div id="header">
	<div class="myInfo">
		
			
			
				&nbsp;
			
		
	</div>
	<!--메뉴영역 시작 -->
	<div id="gnb">
		<h1>
			<a href="../index.jsp"><img src="../images/common/logo.gif" alt="레몬메일로고" /> </a>
		</h1>
		<ul class="cgnb">
			<li><a href="../index.jsp" onfocus="this.blur();"><img src="../images/bt_new/menu3_01.gif" border="0" id="Image1001" onmouseover="MM_swapImage('Image1001','','../images/bt_new/menu3_01-r.gif',1)" onmouseout="MM_swapImgRestore()" /></a>
			</li>
            <li style="padding-right:30px">
				<a href="/asp/asp.jsp"  onfocus="this.blur();">
					<img src="../images/bt_new/menu05.jpg" border="0" id="Image1006" onmouseover="MM_swapImage('Image1006','','../images/bt_new/menu05_over.jpg',1)" onmouseout="MM_swapImgRestore()" alt="LemonASP란" />
				</a>
			</li>
			<li><a href=".././mail/write_mail.jsp" onfocus="this.blur();"><img src="../images/bt_new/menu3_02.gif" border="0" id="Image1002" onmouseover="MM_swapImage('Image1002','','../images/bt_new/menu3_02-r.gif',1)" onmouseout="MM_swapImgRestore()" /></a>
			</li>
			<li><a href=".././address/group_list.jsp" onfocus="this.blur();"><img src="../images/bt_new/menu3_03.gif" border="0" id="Image1003" onmouseover="MM_swapImage('Image1003','','../images/bt_new/menu3_03-r.gif',1)" onmouseout="MM_swapImgRestore()" /></a>
			</li>
			<li><a href=".././result/result.jsp" onfocus="this.blur();"><img src="../images/bt_new/menu3_04.gif" border="0" id="Image1004" onmouseover="MM_swapImage('Image1004','','../images/bt_new/menu3_04-r.gif',1)" onmouseout="MM_swapImgRestore()" /></a>
			</li>
			<li class="last"><a href=".././payment/price.jsp" onfocus="this.blur();"><img src="../images/bt_new/menu3_05.gif" border="0" id="Image1005" onmouseover="MM_swapImage('Image1005','','../images/bt_new/menu3_05-r.gif',1)" onmouseout="MM_swapImgRestore()" /></a>
			</li>
		</ul>
	</div>
	<!--//메뉴영역 끝 -->
</div>
<!--[if !IE]> Gnb AREA End <![endif]--> <!--[if !IE]> Gnb AREA End <![endif]-->
  <!--[if !IE]> Wrap AREA Start <![endif]-->
  <div id="container">
  <!-- snb -->
    <div class="sub_snb">
      <h2><img src="../images/common/ic_LeftCustomer.gif" alt="고객센터 아이콘" /> 고객센터</h2>
      <!-- UI Object -->
		<li><a href="../help/notice.jsp" onFocus="this.blur();"><img src="../images/bt_new/center_top.gif" border="0"></a><a href="../help/faq.jsp" onfocus="this.blur();"></a>
		<li><a href="../help/notice.jsp" onfocus="this.blur();"><img src="../images/bt_new/center_02.gif" border="0" id="Image006" onmouseover="MM_swapImage('Image006','','../images/bt_new/center_02-r.gif',1)" onmouseout="MM_swapImgRestore()" /></a></li>
		<li><a href="../help/faq.jsp" onfocus="this.blur();"><img src="../images/bt_new/center_01.gif" border="0" id="Image005" onmouseover="MM_swapImage('Image005','','../images/bt_new/center_01-r.gif',1)" onmouseout="MM_swapImgRestore()" /></a></li>
		<li><a href="javascript:na_open_window('win', 'http://lemonasp.weebly.com', 0, 0, 1000, 700, 0, 0, 0, 1, 1);"  target="_self""><img src="../images/bt_new/center_05.gif" border="0" id="Image008" onmouseover="MM_swapImage('Image008','','../images/bt_new/center_05-r.gif',1)" onmouseout="MM_swapImgRestore()" /></a></li>
		<li><a href="javascript:na_open_window('win', 'http://cheomhelpdesk.weebly.com', 0, 0, 1000, 700, 0, 0, 0, 1, 1);"  target="_self"><img src="../images/bt_new/center_03.gif" border="0" id="Image007" onmouseover="MM_swapImage('Image007','','../images/bt_new/center_03-r.gif',1)" onmouseout="MM_swapImgRestore()" /></a></li>

			<img src="../images/bt_new/use_last.gif" />		</li>

      <!-- //UI Object -->
    </div>
    <!-- //snb -->
    <!-- content -->
    <div id="sub_content">
      <div class="titArea">
        <h2>FAQ</h2>
        무엇을 도와드릴까요? </div>
      <!-- UI Object -->
      <div class="ser beigeBox margin_b20">
        <fieldset class="srch">
        <legend>검색영역</legend>
        <select id="searchOption">
        <option value=1 >제목</option>
    	      	<option value=2 >본문</option>
   	      </select>
        <input type="text" accesskey="s" title="검색어" class="keyword" id="keyword" value="" onkeypress="fnEnterLogin()"/>
        <span class="btn_pack darkbl">
        <button type="button" onclick="fnSearch()">검색</button>
        </span>
        <span class="btn_pack darkbl">
        <button type="button" onclick="fnSeeAll()">전체보기</button>
        </span>
        </fieldset>
      </div>
      <!-- //UI Object -->
      <!-- TAB Object -->
      <div class="tab">
        <ul>
        <li><a href="./faq.jsp?category=1&search=&searchOption=0">전체</a></li><li><a href="./faq.jsp?category=2&search=&searchOption=0">메일보내기</a></li><li><a href="./faq.jsp?category=3&search=&searchOption=0">결과보기</a></li><li class="on"><a href="./faq.jsp?category=4&search=&searchOption=0">자주묻는질문</a></li></ul>
      </div>
      <!-- //TAB Object -->
      <!-- FAQ Object -->
      <div class="faq">
        <ul>
        <li class="article">
            	<p class="q" onclick="fnShowContent('29')">
            	<a href="#" class="trigger">Q: [자주묻는질문] 수신받은 이메일 이미지가 분리되어 보입니다.</a></p>
            	<div id="29_content" style="display:none;">
	            	<p class="a">A: Q. 최근 네이버로 메일을 보낼 때 아래 그림과 같이 Table로 이어진 이미지 사이에 1~3px 정도의 공간이 생기는 현상이 발견되고있습니다. 


네이버에서 메일 기능을 개선하면서 DOCTYPE이 변경되었기 때문인데요.

현재 이미지 간격이 생기지 않게 하기 위해서는 아래와 같이 메일 본문에 포함된 img 태그의 display 속성에 block을 설정하여 HTML을 작성하시면 됩니다.

소스 자체 img style="display: block" src="http://www~"

해당부분 style="display: block" 를 입력 해주십시오.

지메일 이미지 분리현상도 style태그를 넣어주면 해결됩니다.

그외 아웃룩에서 동일한 문제 발생시 해당 아웃룩 솔루션자체에서 불러들이는 방식이

다르기때문에 해당부분은 개선이 어렵습니다.

고객이 아웃룩 사용자가 많은경우 이미지를 자르셨다면, 전체이미지로 발송하시기를 권장합니다.</p>
	            	</div>
          	</li>
        	<li class="article">
            	<p class="q" onclick="fnShowContent('27')">
            	<a href="#" class="trigger">Q: [자주묻는질문] 왜 메일을 스팸으로 인식하나요?</a></p>
            	<div id="27_content" style="display:none;">
	            	<p class="a">A: 대량 메일을 보내면 메일이 스팸메일함으로 들어간 경우,스팸메일에 어긋나기때문에 스팸으로 들어가는 경우가 있습니다.

또한 개인설정에 따라 스팸함으로 들어가거나, 수신되는 메일서버에서의 문제가 있는경우 스팸으로 분류 될 수 있습니다.

이같은경우 레몬ASP시스템의 문제가 아니며, 수신되는 수신자측에서 스팸으로 인지하여 일어나는 현상입니다.

불법스팸대응센터에서 정의한 광고 전송 시 표기사항에 따르면, 제목 앞에 '(@광고)' 혹은 '(@성인광고)' 문구 및 제목 끝에서 '@’를 표시해야 하며, 본문란의 주요 내용을 제목으로 명시해야 한다고 나와있습니다. 

하지만 대부분 포털사이트내의 제목에 (@광고) (@성인) 등이 들어갈 경우 무조건 스팸으로 처리하고 있으며, 네이버에서 스팸정책에서 수신자의 의사와 상관없이 일방적으로 전송되는 불필요한 광고성 메일로 정의하고 있습니다. 

저희 처음서비스는 KISA와 국내 포털사이트의 스팸정책을 준수하며, 수신의 동의된 옵트인(opt-in) 메일에 대해서만 메일발송이 가능합니다.

위의 스팸 정책들을 준수하였음에도 메일이 스팸메일함으로 들어오는경우 이렇게 처리하시기 바랍니다.



하기내용 참고


네이버 및 국내 포털사이트의 문의하기 (고객센터 직접문의)

[예]
안녕하세요 처음서비스입니다.

최근 메일을 발송하였음에도, 메일이 스팸함으로 이동하는 문제가 발생하고있습니다.

옵트인 메일인데도 불구하고 스팸함으로 들어가는 원인에 대해 문의드립니다.

1.메일제목: 처음서비스입니다.
2.발송자 : 처음서비스
3.발신메일주소: help@cheom.net
4.발송메일발송시간: 2012-11-09 16:45:22
⑤ 수신메일: help@naver.com
⑥ ip :발송아이피는 처음서비스로 별도 문의 부탁드립니다
⑦ 실패로그 : Spam mail rejected (리포트 실패원인 내 자세히 보기 클릭)

스팸에 대한 해지 요청 부탁 드립니다.
감사합니다


해당내용을 해당업체에 문의할 경우 짧게는 3일~ 길게는 7일정도 후 답변을 받을 있습니다.

좀 더 빠른 처리를 원하는 고객분들은 고객센터에 직접 전화하는경우 좀 더 빨리 처리가 가능합니다.

여기서 잠깐!!!! 꼭 옵트인 메일에 대해서만 문의가 가능하니, 참고부탁드립니다.

참고로, 업체별 고객센터 URL와 스팸정책은 다음과 같습니다.

[네이버] 1588-3820
고객센터 : http://help.naver.com/customer/index.nhn
스팸정책 : http://www.naver.com/rules/spamcheck.html


[다음] 1577-3321
고객센터 : http://cs.daum.net/
스팸정책 : http://www.daum.net/doc/nospam.html

[네이트] 1599-7983
고객센터 : http://helpdesk.nate.com/
스팸정책 : http://antispam.nate.com/spam_policy01.html


[야후] 1544-2500
고객센터 : http://kr.howto.yahoo.com/default.asp
스팸정책 : http://kr.antispam.yahoo.com/
(야후의경우 2012년 12월부로 국내사업의 철수로 별도 미국 야후로 문의하셔야합니다.)


그외 개별 사내메일을 사용하시는경우 회사내의 웹메일 담당자에게 해당내용을 전달해 주시면 확인이 가능힙니다.</p>
	            	</div>
          	</li>
        	<li class="article">
            	<p class="q" onclick="fnShowContent('26')">
            	<a href="#" class="trigger">Q: [자주묻는질문] 메일발송이 취소된경우</a></p>
            	<div id="26_content" style="display:none;">
	            	<p class="a">A: 메일발송요청이후 메일발송이 취소된경우 고객님께서 

하단의 수신거부 및 발신자정보 기입을 제대로 하였는지 확인부탁드립니다.

메일발송이 취소된경우 하단의 수신거부 및 발신출처가 불분명하여 취소된 경우가 

90%이상이오니 발송시 이점 꼭 유의하시기 바랍니다.

또한 수신동의되지않은 메일링에 대해서는 메일발송이 불가하오니, 수신동의된 고객에 한하여 메일 발송을 하시기 바랍니다.

차후 네이버및 KISA에서 스팸으로 인지할경우 계정이 영구정지 되며,

환불불가 합니다.</p>
	            	</div>
          	</li>
        	<li class="article">
            	<p class="q" onclick="fnShowContent('25')">
            	<a href="#" class="trigger">Q: [자주묻는질문] 한국인터넷 진흥원의 스팸가이드입니다.</a></p>
            	<div id="25_content" style="display:none;">
	            	<p class="a">A: 레몬ASP는 한국인터넷 진흥원의 스팸정책을 준수합니다.

KISA에서 권장하는 대량메일발송 스팸메일 가이드 입니다.

고객님들꼐서는 메일발송시 해당부분을 인지하시기 바랍니다.

http://www.kisa.or.kr/public/major/policyView.jsp?b_No=49&d_No=38&searchType=total&searchKeyword</p>
	            	</div>
          	</li>
        	<li class="article">
            	<p class="q" onclick="fnShowContent('24')">
            	<a href="#" class="trigger">Q: [자주묻는질문] 회원가입 시 인증메일이 오지않습니다?</a></p>
            	<div id="24_content" style="display:none;">
	            	<p class="a">A: 레몬ASP 회원가입시 인증메일이 오지않는경우가 있습니다.

원인은 메일서버에서 해당 승인 메일을 메일주소가 잘못되었거나, 스팸메일 또는 필터가 되어 메일수신이 되지않는경우 입니다.

이런경우 고객님의 사내메일이 아닌 포털사이트의 메일로 메일을 받아 승인하시면 회원가입이 원활하게 이루어집니다.


또한 정크메일이나 스팸메일함으로 메일이 들어올수 있음으로 해당메일함도 함께 확인하셔야합니다.

해당문제가 해결이 되지않는다면, 승인메일이 오지않거나 문제가 있는경우 처음서비스로 연락부탁드립니다.

고객센터 02-1566-9520</p>
	            	</div>
          	</li>
        	<li class="article">
            	<p class="q" onclick="fnShowContent('23')">
            	<a href="#" class="trigger">Q: [자주묻는질문] 네이버 스팸정책 변경에 따른 공지사항</a></p>
            	<div id="23_content" style="display:none;">
	            	<p class="a">A: 안녕하세요 처음서비스입니다.
최근 네이버의 스팸정책 강화로 인하여, 메일발송시 실패가 잦은 경우가 발생하고 있습니다.
이에, 스팸메일을 주기적으로 발송하는 업체의 경우, 해당서비스에서 메일발송을 
하지못하도록 조치하고 있습니다.

네이버 스팸이 차단되는경우는 다음과 같습니다.

- 스팸 신고 또는 항의가 일정 수준 이상에 달한 메일 
- 짧은 시간 동안에 많은 양의 메일을 발송하거나 발송을 시도한 메일 
- 불특정 다수에게 발송되는 무분별한 광고 메일 

위와 같은 사항과 관련하여 지속적인 스팸으로 인지되는 고객의 경우
메일발송이 불가하오니, 수신동의가 된 메일에 대해서만 메일 발송을 권고드립니다.

또한 실제 수신자가 존재하지않거나, 휴먼계정인경우 메일이 발송되지않음으로
지속적인 DB관리를 부탁드립니다.

감사합니다.

네이버스팸정책 : http://mail.naver.com/policy/spam_1.html</p>
	            	</div>
          	</li>
        	<li class="article">
            	<p class="q" onclick="fnShowContent('22')">
            	<a href="#" class="trigger">Q: [자주묻는질문] 레몬 ASP 환불 정책</a></p>
            	<div id="22_content" style="display:none;">
	            	<p class="a">A: 1. 회원 탈퇴 시에 회원의 서비스 point를 정산하여 사용하지 않은 잔여 point에 대하여
환불하여 드립니다. 

2. 직접 구매하지 않은 보너스 point에 대하여는 환불이 되지 않습니다. 

3. 환불 방법은 약관에 따라 산정된 금액을 회원에게 통지 후 회원 본인명의 계좌에 익월 15일까지 입금처리 합니다. 

4. 환불은 이용하고 남은 Cash 잔액에서 재정경제부에서 공시한 <인터넷이용관련소비자피해보상>에서 규정한 위약금(총 이용료의10%) 및 보너스충전, PG수수료, 송금비용을 차감한 이용료 잔액을 환불하여 드립니다. 

단, 위약금 및 PG수수료, 송금비용의 합계는 최소 5,000원입니다. 따라서 5,000원 미만의 금액은 해지시 자동 소멸되며 환불되지 않습니다. 

5. 아래와 같은 사유 일 때에는 환불거부 또는 불가합니다. 

1. 스팸메일 발송 등의 회원의 귀책사유로 인하여 계약을 해지하거나 회원 탈퇴한 경우 
2. 회원가입시 이름 또는 상호와 받는사람의 이름 또는 상호가 맞지않는경우 
3. 정액제 회원의 경우 결제 후 14일 이후 환불 불가

6. 결제일 기준 6개월이 지난 포인트에 대한 환불은 불가합니다.</p>
	            	</div>
          	</li>
        	</ul>
      </div>
      <!-- //FAQ Object -->
      <!-- 페이징 Start -->
      <div class="paginate"> <strong>1</strong><div class="infoBox margin_t20"><span class="info_txt">* 해결되지 않은 궁금증은 메일문의 하시면, 신속하게 답변을 받으실 수 있습니다.</span> <span class="btn_pack large rbt">
        <button type="button" onclick="javascript:na_open_window('win', 'http://cheomhelpdesk.weebly.com', 0, 0, 1000, 700, 0, 0, 0, 1, 1);">메일문의</button>
        </span></div>

	  </div>
      <!-- 페이징 End -->

    </div>
    <!-- //content -->
    <!-- aside -->
				


<div class="aside">
	<ul>
		<li class="margin_b5">
		<a href='http://cheomservice.com/contactus/new_lemonplus.jsp' target="_blank">
			<img src="../images/common/quick_banner_01.gif" alt="레몬메일플러스" />
		</a>
		</li>
		<li>
		<a href='http://ezh.kr/lemonmail/' target="_blank">
			<img src="../images/common/quick_banner_02.gif" alt="원격지원서비스" />
		</a>
		</li>
		<li>
		<a href='http://cheomservice.com/agent/email.jsp' target="_blank">
			<img src="../images/common/quick_banner_03.gif" alt="발송대행신청하기" />
		</a>
		</li>
		<li>
		<a href='http://cheomservice.com/contactus/pop_apply2.chm' target="_blank">
			<img src="../images/common/quick_banner_04.gif" alt="세금계산서신청하기" />
		</a>
		</li>
		<li>
		<a href='#' onclick='window.open("http://cheomservice.com/agency/new_popup/new_popup_apply_email_pay.jsp", "bill", "left=10,top=10,width=750,height=600,toolbar=no,menubar=no,status=no,scrollbars=no,resizable=no")'>
			<img src="../images/common/quick_banner_05.gif" alt="온라인결재" />
		</a>
		</li>
	</ul>
</div><!-- //aside -->
  </div>
  <!--[if !IE]> Wrap AREA End <![endif]-->
  	


<!-- AceCounter Log Gathering Script V.70.2012031601 -->
<script language='javascript'>
if( typeof HL_GUL == 'undefined' ){

var HL_GUL = 'ngc12.nsm-corp.com';var HL_GPT='80'; var _AIMG = new Image(); var _bn=navigator.appName; var _PR = location.protocol=="https:"?"https://"+HL_GUL:"http://"+HL_GUL+":"+HL_GPT;if( _bn.indexOf("Netscape") > -1 || _bn=="Mozilla"){ setTimeout("_AIMG.src = _PR+'/?cookie';",1); } else{ _AIMG.src = _PR+'/?cookie'; };
var _JV="AMZ2008090201";//script Version
var HL_GCD = 'CN3B36893411291'; // gcode
var _UD='undefined';var _UN='unknown';
function _IX(s,t){return s.indexOf(t)}
function _GV(b,a,c,d){ var f = b.split(c);for(var i=0;i<f.length; i++){ if( _IX(f[i],(a+d))==0) return f[i].substring(_IX(f[i],(a+d))+(a.length+d.length),f[i].length); }	return ''; }
function _XV(b,a,c,d,e){ var f = b.split(c);var g='';for(var i=0;i<f.length; i++){ if( _IX(f[i],(a+d))==0){ try{eval(e+"=f[i].substring(_IX(f[i],(a+d))+(a.length+d.length),f[i].length);");}catch(_e){}; continue;}else{ if(g) g+= '&'; g+= f[i];}; } return g;};
function _NOB(a){return (a!=_UD&&a>0)?new Object(a):new Object()}
function _NIM(){return new Image()}
function _IL(a){return a!=_UD?a.length:0}
function _ILF(a){ var b = 0; try{eval("b = a.length");}catch(_e){b=0;}; return b; }
function _VF(a,b){return a!=_UD&&(typeof a==b)?1:0}
function _LST(a,b){if(_IX(a,b)>0){ a=a.substring(0,_IX(a,b));}; return a;}
function _CST(a,b){if(_IX(a,b)>0) a=a.substring(_IX(a,b)+_IL(b),_IL(a));return a}
function _UL(a){a=_LST(a,'#');a=_CST(a,'://');return a}
function _AA(a){return new Array(a?a:0)}
function _IDV(a){return (typeof a!=_UD)?1:0}
if(!_IDV(HL_GUL)) var HL_GUL ='ngc12.nsm-corp.com'; 
if(!_IDV(HL_GPT)) var HL_GPT ='80';
_DC = document.cookie ;
function _AGC(nm) { var cn = nm + "="; var nl = cn.length; var cl = _DC.length; var i = 0; while ( i < cl ) { var j = i + nl; if ( _DC.substring( i, j ) == cn ){ var val = _DC.indexOf(";", j ); if ( val == -1 ) val = _DC.length; return unescape(_DC.substring(j, val)); }; i = _DC.indexOf(" ", i ) + 1; if ( i == 0 ) break; } return ''; }
function _ASC( nm, val, exp ){var expd = new Date(); if ( exp ){ expd.setTime( expd.getTime() + ( exp * 1000 )); document.cookie = nm+"="+ escape(val) + "; expires="+ expd.toGMTString() +"; path="; }else{ document.cookie = nm + "=" + escape(val);};}
function SetUID() {     var newid = ''; var d = new Date(); var t = Math.floor(d.getTime()/1000); newid = 'UID-' + t.toString(16).toUpperCase(); for ( var i = 0; i < 16; i++ ){ var n = Math.floor(Math.random() * 16).toString(16).toUpperCase(); newid += n; }       return newid; }
var _FCV = _AGC("ACEFCID"); if ( !_FCV ) { _FCV = SetUID(); _ASC( "ACEFCID", _FCV , 86400 * 30 * 12 ); _FCV=_AGC("ACEFCID");}
var _AIO = _NIM(); var _AIU = _NIM();  var _AIW = _NIM();  var _AIX = _NIM();  var _AIB = _NIM();  var __hdki_xit = _NIM();
var _gX='/?xuid='+HL_GCD+'&sv='+_JV,_gF='/?fuid='+HL_GCD+'&sv='+_JV,_gU='/?uid='+HL_GCD+'&sv='+_JV+"&FCV="+_FCV,_gE='/?euid='+HL_GCD+'&sv='+_JV,_gW='/?wuid='+HL_GCD+'&sv='+_JV,_gO='/?ouid='+HL_GCD+'&sv='+_JV,_gB='/?buid='+HL_GCD+'&sv='+_JV;

var _d=_rf=_end=_fwd=_arg=_xrg=_av=_bv=_rl=_ak=_xrl=_cd=_cu=_bz='',_sv=11,_tz=20,_ja=_sc=_ul=_ua=_UA=_os=_vs=_UN,_je='n',_bR='blockedReferrer';
if(!_IDV(_CODE)) var _CODE = '' ;
_tz = Math.floor((new Date()).getTimezoneOffset()/60) + 29 ;if( _tz > 24 ) _tz = _tz - 24 ;
// Javascript Variables
if(!_IDV(_amt)) var _amt=0 ;if(!_IDV(_pk)) var _pk='' ;if(!_IDV(_pd)) var _pd='';if(!_IDV(_ct)) var _ct='';
if(!_IDV(_ll)) var _ll='';if(!_IDV(_ag)) var _ag=0;	if(!_IDV(_id)) var _id='' ;if(!_IDV(_mr)) var _mr = _UN;
if(!_IDV(_gd)) var _gd=_UN;if(!_IDV(_jn)) var _jn='';if(!_IDV(_jid)) var _jid='';if(!_IDV(_skey)) var _skey='';
if(!_IDV(_ud1)) var _ud1='';if(!_IDV(_ud2)) var _ud2='';if(!_IDV(_ud3)) var _ud3='';
if( !_ag ){ _ag = 0 ; }else{ _ag = parseInt(_ag); }
if( _ag < 0 || _ag > 150 ){ _ag = 0; }
if( _gd != 'man' && _gd != 'woman' ){ _gd =_UN;};if( _mr != 'married' && _mr != 'single' ){ _mr =_UN;};if( _jn != 'join' && _jn != 'withdraw' ){ _jn ='';};
if( _ag > 0 || _gd == 'man' || _gd == 'woman'){ _id = 'undefined_member';}
if( _jid != '' ){ _jid = 'undefined_member'; }
_je = (navigator.javaEnabled()==true)?'1':'0';_bn=navigator.appName;
if(_bn.substring(0,9)=="Microsoft") _bn="MSIE";
_bN=(_bn=="Netscape"),_bI=(_bn=="MSIE"),_bO=(_IX(navigator.userAgent,"Opera")>-1);if(_bO)_bI='';
_bz=navigator.appName; _pf=navigator.platform; _av=navigator.appVersion; _bv=parseFloat(_av) ;
if(_bI){_cu=navigator.cpuClass;}else{_cu=navigator.oscpu;};
if((_bn=="MSIE")&&(parseInt(_bv)==2)) _bv=3.01;_rf=document.referrer;var _prl='';var _frm=false;
function _WO(a,b,c){window.open(a,b,c)}
function ACEF_Tracking(a,b,c,d,e,f){ if(!_IDV(b)){var b = 'FLASH';}; if(!_IDV(e)){ var e = '0';};if(!_IDV(c)){ var c = '';};if(!_IDV(d)){ var d = '';}; var a_org=a; b = b.toUpperCase(); var b_org=b;	if(b_org=='FLASH_S'){ b='FLASH'; }; if( typeof CU_rl == 'undefined' ) var CU_rl = _PT(); if(_IDV(HL_GCD)){ var _AF_rl = document.URL; if(a.indexOf('://') < 0  && b_org != 'FLASH_S' ){ var _AT_rl  = ''; if( _AF_rl.indexOf('?') > 0 ){ _AF_rl = _AF_rl.substring(0,_AF_rl.indexOf('?'));}; var spurl = _AF_rl.split('/') ;	for(var ti=0;ti < spurl.length ; ti ++ ){ if( ti == spurl.length-1 ){ break ;}; if( _AT_rl  == '' ){ _AT_rl  = spurl[ti]; }else{ _AT_rl  += '/'+spurl[ti];}; }; var _AU_arg = ''; if( a.indexOf('?') > 0 ){ _AU_arg = a.substring(a.indexOf('?'),a.length); a = a.substring(0,a.indexOf('?')); }; var spurlt = a.split('/') ; if( spurlt.length > 0 ){ a = spurlt[spurlt.length-1];}; a = _AT_rl +'/'+a+_AU_arg;	_AF_rl=document.URL;}; _AF_rl = _AF_rl.substring(_AF_rl.indexOf('//')+2,_AF_rl.length); if( typeof f == 'undefined' ){ var f = a }else{f='http://'+_AF_rl.substring(0,_AF_rl.indexOf('/')+1)+f}; var _AS_rl = CU_rl+'/?xuid='+HL_GCD+'&url='+escape(_AF_rl)+'&xlnk='+escape(f)+'&fdv='+b+'&idx='+e+'&'; var _AF_img = new Image(); _AF_img.src = _AS_rl; if( b_org == 'FLASH' && a_org != '' ){ if(c==''){ window.location.href = a_org; }else{ if(d==''){ window.open(a_org,c);}else{ window.open(a_org,c,d); };};	};} ; }
function _PT(){return location.protocol=="https:"?"https://"+HL_GUL:"http://"+HL_GUL+":"+HL_GPT}
function _EL(a,b,c){if(a.addEventListener){a.addEventListener(b,c,false)}else if(a.attachEvent){a.attachEvent("on"+b,c)} }
function _NA(a){return new Array(a?a:0)}
function HL_ER(a,b,c,d){_xrg=_PT()+_gW+"&url="+escape(_UL(document.URL))+"&err="+((typeof a=="string")?a:"Unknown")+"&ern="+c+"&bz="+_bz+"&bv="+_vs+"&RID="+Math.random()+"&";
if(_IX(_bn,"Netscape") > -1 || _bn == "Mozilla"){ setTimeout("_AIW.src=_xrg;",1); } else{ _AIW.src=_xrg; } }
function HL_PL(a){if(!_IL(a))a=_UL(document.URL);
_arg = _PT()+_gU;
if( typeof HL_ERR !=_UD && HL_ERR == 'err'){ _arg = _PT()+_gE;};
if( _ll.length > 0 ) _arg += "&md=b";
_AIU.src = _arg+"&url="+escape(a)+"&ref="+escape(_rf)+"&cpu="+_cu+"&bz="+_bz+"&bv="+_vs+"&os="+_os+"&dim="+_d+"&cd="+_cd+"&je="+_je+"&jv="+_sv+"&tz="+_tz+"&ul="+_ul+"&ad_key="+escape(_ak)+"&skey="+escape(_skey)+"&age="+_ag+"&gender="+_gd+"&marry="+_mr+"&join="+_jn+"&member_key="+_id+"&jid="+_jid+"&udf1="+_ud1+"&udf2="+_ud2+"&udf3="+_ud3+"&amt="+_amt+"&frwd="+_fwd+"&pd="+escape(_pd)+"&ct="+escape(_ct)+"&ll="+escape(_ll)+"&RID="+Math.random()+"&";
setTimeout("",300);
}
_EL(window,"error",HL_ER); //window Error
if( typeof window.screen == 'object'){_sv=12;_d=screen.width+'*'+screen.height;_sc=_bI?screen.colorDepth:screen.pixelDepth;if(_sc==_UD)_sc=_UN;}
_ro=_NA();if(_ro.toSource||(_bI&&_ro.shift))_sv=13;
if( top && typeof top == 'object' &&_ILF(top.frames)){eval("try{_rl=top.document.URL;}catch(_e){_rl='';};"); if( _rl != document.URL ) _frm = true;};
if(_frm){ eval("try{_prl = top.document.URL;}catch(_e){_prl=_bR;};"); if(_prl == '') eval("try{_prl=parent.document.URL;}catch(_e){_prl='';};"); 
if( _IX(_prl,'#') > 0 ) _prl=_prl.substring(0,_IX(_prl,'#')); 
_prl=_LST(_prl,'#');
if( _IX(_rf,'#') > 0 ) _rf=_rf.substring(0,_IX(_rf,'#')); 
if( _IX(_prl,'/') > 0 && _prl.substring(_prl.length-1,1) == '/' ) _prl =_prl.substring(0,_prl.length-1);
if( _IX(_rf,'/') > 0 && _rf.substring(_rf.length-1,1) == '/' ) _rf =_rf.substring(0,_rf.length-1);
if( _rf == '' ) eval("try{_rf=parent.document.URL;}catch(_e){_rf=_bR;}"); 
if(_rf==_bR||_prl==_bR){ _rf='',_prl='';}; if( _rf == _prl ){ eval("try{_rf=top.document.referrer;}catch(_e){_rf='';}"); 
if( _rf == ''){ _rf = 'bookmark';};if( _IX(document.cookie,'ACEN_CK='+escape(_rf)) > -1 ){ _rf = _prl;} 
else{ 
if(_IX(_prl,'?') > 0){ _ak = _prl.substring(_IX(_prl,'?')+1,_prl.length); _prl = _ak; }
if( _IX(_prl.toUpperCase(),'OVRAW=') >= 0 ){ _ak = 'src=overture&kw='+_GV(_prl.toUpperCase(),'OVRAW','&','=')+'&OVRAW='+_GV(_prl.toUpperCase(),'OVRAW','&','=')+'&OVKEY='+_GV(_prl.toUpperCase(),'OVKEY','&','=')+'&OVMTC='+_GV(_prl.toUpperCase(),'OVMTC','&','=').toLowerCase() }; 
if(_IX(_prl,'gclid=') >= 0 ){ _ak='src=adwords'; }; if(_IX(_prl,'DWIT=') >= 0 ){_ak='src=dnet_cb';}; 
if( _IX(_prl,"rcsite=") >= 0 &&  _IX(_prl,"rctype=") >= 0){ _prl += '&'; _ak = _prl.substring(_IX(_prl,'rcsite='),_prl.indexOf('&',_IX(_prl,'rcsite=')+7))+'-'+_prl.substring(_IX(_prl,'rctype=')+7,_prl.indexOf('&',_IX(_prl,'rctype=')+7))+'&'; };
if( _GV(_prl,'src','&','=') ) _ak += '&src='+_GV(_prl,'src','&','='); if( _GV(_prl,'kw','&','=') ) _ak += '&kw='+_GV(_prl,'kw','&','='); if(_prl.length>0){ _prl = _XV(_prl,'FWDRL','&','=','_rf'); _rf = unescape(_rf); _ak = _XV(_ak,'FWDRL','&','=','_prl'); }; if( typeof FD_ref=='string' && FD_ref != '' ) _rf = FD_ref;
_fwd = _GV(_prl,'FWDIDX','&','=');
document.cookie='ACEN_CK='+escape(_rf)+';path=/;'; 
}; 
if(document.URL.indexOf('?')>0 && ( _IX(_ak,'rcsite=') < 0 && _IX(_ak,'NVAR=') < 0 && _IX(_ak,'src=') < 0 && _IX(_ak,'source=') < 0 ) ) _ak =document.URL.substring(document.URL.indexOf('?')+1,document.URL.length); }; 
}
else{ 
_rf=_LST(_rf,'#');_ak=_CST(document.URL,'?');
if( _IX(_ak,"rcsite=") > 0 &&  _IX(_ak,"rctype=") > 0){
    _ak += '&';
    _ak = _ak.substring(_IX(_ak,'rcsite='),_ak.indexOf('&',_IX(_ak,'rcsite=')+7))+'-'+_ak.substring(_IX(_ak,'rctype=')+7,_ak.indexOf('&',_IX(_ak,'rctype=')+7))+'&';
}
}
_rl=document.URL;
var _trl = _rl.split('?'); if(_trl.length>1){ _trl[1] = _XV(_trl[1],'FWDRL','&','=','_rf'); _rf = unescape(_rf); _fwd = _GV(_trl[1],'FWDIDX','&','='); _rl=_trl.join('?'); 
_ak = _XV(_ak,'FWDRL','&','=','_prl');
}; if( typeof FD_ref=='string' && FD_ref != '' ) _rf = FD_ref;
if( _rf.indexOf('googlesyndication.com') > 0 ){ 
var _rf_idx = _rf.indexOf('&url=');  if( _rf_idx > 0 ){ var _rf_t = unescape(_rf.substring(_rf_idx+5,_rf.indexOf('&',_rf_idx+5)));  if( _rf_t.length > 0 ){ _rf = _rf_t ;};  };  };
_rl = _UL(_rl); _rf = _UL(_rf);

if( typeof _rf_t != 'undefined' && _rf_t != '' ) _rf = _rf_t ;
if( typeof _ak_t != 'undefined' && _ak_t != '' ) _ak = _ak_t ;

if( typeof _rf==_UD||( _rf == '' )) _rf = 'bookmark' ;_cd=(_bI)?screen.colorDepth:screen.pixelDepth;
_UA = navigator.userAgent;_ua = navigator.userAgent.toLowerCase();
if (navigator.language){  _ul = navigator.language.toLowerCase();}else if(navigator.userLanguage){  _ul = navigator.userLanguage.toLowerCase();};

_st = _IX(_UA,'(') + 1;_end = _IX(_UA,')');_str = _UA.substring(_st, _end);_if = _str.split('; ');_cmp = _UN ;

if(_bI){ _cmp = navigator.appName; _str = _if[1].substring(5, _if[1].length); _vs = (parseFloat(_str)).toString();} 
else if ( (_st = _IX(_ua,"opera")) > 0){ _cmp = "Opera" ;_vs = _ua.substring(_st+6, _ua.indexOf('.',_st+6)); } 
else if ((_st = _IX(_ua,"firefox")) > 0){_cmp = "Firefox"; _vs = _ua.substring(_st+8, _ua.indexOf('.',_st+8)); } 
else if ((_st = _IX(_ua,"netscape6")) > 0){ _cmp = "Netscape"; _vs = _ua.substring(_st+10, _ua.length);  
if ((_st = _IX(_vs,"b")) > 0 ) { _str = _vs.substring(0,_IX(_vs,"b")); _vs = _str ;  };}
else if ((_st = _IX(_ua,"netscape/7")) > 0){  _cmp = "Netscape";  _vs = _ua.substring(_st+9, _ua.length);  if ((_st = _IX(_vs,"b")) > 0 ){ _str = _vs.substring(0,_IX(_vs,"b")); _vs = _str;};
}
else{
if (_IX(_ua,"gecko") > 0){ if(_IX(_ua,"safari")>0){ _cmp = "Safari";_ut = _ua.split('/');for( var ii=0;ii<_ut.length;ii++) if(_IX(_ut[ii],'safari')>0){ _vst = _ut[ii].split(' '); _vs = _vst[0];} }else{ _cmp = navigator.vendor;  } } else if (_IX(_ua,"nav") > 0){ _cmp = "Netscape Navigator";}else{ _cmp = navigator.appName;}; _av = _UA ; 
}
if (_IX(_vs,'.')<0){  _vs = _vs + '.0'}
_bz = _cmp; 


var nhn_ssn={uid:HL_GCD,g:HL_GUL,p:HL_GPT,s:_JV,rl:_rl,m:[],run:nhn_ssn?nhn_ssn.uid:this.uid};
function CN3B36893411291(){var f={e:function(s,t){return s.indexOf(t);},d:function(s){var r=String(s); return r.toUpperCase();},f:function(o){var a;a=o;if(f.d(a.tagName)=='A' || f.d(a.tagName)=='AREA'){return a;}else if(f.d(a.tagName)=='BODY'){return 0;}else{return f.f(a.parentNode);} },n:function(s){var str=s+"";var ret="";for(i = 0; i < str.length; i++){	var at = str.charCodeAt(i);var ch=String. fromCharCode(at);	if(at==10 || at==32){ret+=''+ch.replace(ch,'');}else if (at==34||at==39|at==35){ret+=''+ch.replace(ch,' ');	}else{ret+=''+ch;}  } return ret;},ea:function(c,f){var wd;if(c=='click'){wd=window.document;}else{wd=window;}if(wd.addEventListener){wd.addEventListener(c,f,false)}else if(wd.attachEvent){wd.attachEvent("on"+c,f)} } };
var p={h:location.host,p:(location.protocol=='https:')?"https://"+nhn_ssn.g:"http://"+nhn_ssn.g+":"+nhn_ssn.p,s:'/?xuid='+nhn_ssn.uid+'&sv='+nhn_ssn.s,u:function(){var r='';r=String(nhn_ssn.rl);var sh=r.indexOf('#'); if(sh!=-1){r=r.substring(0,sh);}return r+'';},ol:new Image(0,0),xL:function(x){if(typeof(Amz_T_e)==s.u){ p.ol.src=p.p+p.s+'&url='+escape(p.u())+'&xlnk='+escape(x)+'&xidx=0'+'&crn='+Math.random()+'&';nhn_ssn.m.push(p.ol);} } };
var s={Lp:'a.tagName=="B" || a.tagName=="I" || a.tagName== "U" || a.tagName== "FONT" || a.tagName=="I" || a.tagName=="STRONG"'  ,f:'function',	j:'javascript:',u:'undefined'};var c={Run:function(){f.ea('click',this.ec);},ec:function(e){var ok='';var m = document.all ? event.srcElement : e.target;var a=m;var o=m.tagName;if(o=="A" || o=="AREA" || o=="IMG" || eval(s.Lp)){ok=c.lc(m);if(ok.length != 0){p.xL(unescape(ok));};	};},lc:function(o){ try{var ar='';var obj;obj=f.f(o);if(typeof obj==s.u){return '';};if(typeof(obj.href)==s.u){return '';};ar = String(obj.href);if(ar.length == 0){return '';};ar=f.n(ar);if(f.e(ar,'void(') == -1 && f.e(ar,'void0') == -1){	return ar;}else{return s.j + 'void(0)';};return '';}catch(er){return '';} } };
if(p.u().charAt(1) != ':'){if(nhn_ssn.uid!=nhn_ssn.run){c.Run(); } };
};eval(nhn_ssn.uid + '();');


if( _IX(_pf,_UD) >= 0 || _pf ==  '' ){ _os = _UN ;}else{ _os = _pf ; };
if( _IX(_os,'Win32') >= 0 ){if( _IX(_av,'98')>=0){ _os = 'Windows 98';}else if( _IX(_av,'95')>=0 ){ _os = 'Windows 95';}else if( _IX(_av,'Me')>=0 ){ _os = 'Windows Me';}else if( _IX(_av,'NT')>=0 ){ _os = 'Windows NT';}else{ _os = 'Windows';};if( _IX(_ua,'nt 5.0')>=0){ _os = 'Windows 2000';};if( _IX(_ua,'nt 5.1')>=0){_os = 'Windows XP';if( _IX(_ua,'sv1') > 0 ){_os = 'Windows XP SP2';};};if( _IX(_ua,'nt 5.2')>=0){_os ='Windows Server 2003';};if( _IX(_ua,'nt 6.0')>=0){_os ='Windows Vista';};if( _IX(_ua,'nt 6.1')>=0){_os ='Windows 7';};};
_pf_s = _pf.substring(0,4);if( _pf_s == 'Wind'){if( _pf_s == 'Win1'){_os = 'Windows 3.1';}else if( _pf_s == 'Mac6' ){ _os = 'Mac';}else if( _pf_s == 'MacO' ){ _os ='Mac';}else if( _pf_s == 'MacP' ){_os='Mac';}else if(_pf_s == 'Linu'){_os='Linux';}else if( _pf_s == 'WebT' ){ _os='WebTV';}else if(  _pf_s =='OSF1' ){ _os ='Compaq Open VMS';}else if(_pf_s == 'HP-U' ){ _os='HP Unix';}else if(  _pf_s == 'OS/2' ){ _os = 'OS/2' ;}else if( _pf_s == 'AIX4' ){ _os = 'AIX';}else if( _pf_s == 'Free' ){ _os = 'FreeBSD';}else if( _pf_s == 'SunO' ){ _os = 'SunO';}else if( _pf_s == 'Drea' ){ _os = 'Drea'; }else if( _pf_s == 'Plan' ){ _os = 'Plan'; }else{ _os = _UN; };};
if( _cu == 'x86' ){ _cu = 'Intel x86';}else if( _cu == 'PPC' ){ _cu = 'Power PC';}else if( _cu == '68k' ){ _cu = 'Motorola 680x';}else if( _cu == 'Alpha' ){ _cu = 'Compaq Alpa';}else if( _cu == 'Arm' ){ _cu = 'ARM';}else{ _cu = _UN;};if( _d == '' || typeof _d==_UD ){ _d = '0*0';}

HL_PL(_rl); // Site Logging
}
</script>
<!-- AceCounter Log Gathering Script End -->

<div id="footer">
	<div class="info">
		<address>
			<span class="logo"><a	href="http://www.cheomservice.com" target="_blank"> <img src="../images/common/logo_bt.gif" alt="처음소프트로고" /></span>
		</address></a>
		<span class="bn_img"><img src="../images/common/footer_img.gif"
			alt="" /> </span>
		<p class="info2">
			처음소프트(주) 사업자등록번호:220-87-51332 주소: 서울시 서초구 강남대로 341, 삼원빌딩 8층<br />
			TEL: 1670-1445 / FAX: 02-6008-4671 개인정보관리책임:유현상 통신판매업신고:제02391호<br />
			대표이사:안유석 문의:help@cheom.net COPYRIGHT BY CHEOMSOFT. ALL RIGHT RESERVED
		</p>
	</div>
</div>
</div>
</form>

</body>
</html>