var answerclick;
var answer;
var loginNick;
var bbsansweragree;
var bbsansweragreeclick;
var replyvalue = [];

function answerready(){
$( document ).ready(function(){
	console.log("ready");
	
	console.log("중복중복");
	$("#bbstbodyanswer").empty();
	$("#bbstheadanswer").empty();
	var bbsnum = $("#numtestvalue").val();
	loginNick = $("#loginNick").val();
	//alert(value);
	
	$.ajax({
		url:'/BbsViewAnswerAction.bo',
		type:"post",
		dataType:'json',
		data:{"bbsNumCheck":bbsnum},
		success:function(result){
			
			var answercount = "<tr style='background-color: #eeeeee; text-align: left;'>"+
											"<th colspan='3' >댓글 현황&nbsp;(&nbsp;"+result.answercount+"&nbsp;)</th></tr>";
			
			$("#bbstheadanswer").append(answercount);
			
			//베스트 공감 댓글
			for(var j = 0; j<result.answeragree.length; j++){
				bbsansweragree = result.answeragree[j];
				
				if(loginNick == bbsansweragree.answerNick1 || loginNick == "세시입니다"){
					if(bbsansweragree.reply1 == 0){
						var valueagree = "<tr style='border-top: 1px solid #ddd; background-color: #F7BE81;'><th style='width: 15%;'>"+bbsansweragree.answerNick1+"</th>" +
											"<td style='width: 15%;'>"+bbsansweragree.answerDate1+"</td>"+
											"<td style='width: 70%; text-align: right;'>"+
											"<a href='javascript:answeragree("+bbsansweragree.answerNum1+")'><img src='images/공감.png'>" +
											"</a>&nbsp;"+bbsansweragree.answerAgree1+"&nbsp;<a href='javascript:answernotagree("+bbsansweragree.answerNum1+")'>" +
											"<img src='images/비공감.png'></a>&nbsp;"+bbsansweragree.answerNotAgree1+"</td>"+
											"</tr><tr style='background-color: #F7BE81;'><td colspan='3'>"+bbsansweragree.answerContent1+"</td></tr>"+
											"<tr style='background-color: #F7BE81;'><td colspan='3' align='right'><a href='javascript:answerdelete("+bbsansweragree.answerNum1+")'>[삭제]</a>" +
													"&nbsp;<a href='javascript:replyans("+bbsansweragree.answerNum1+")'>[답글]</a></td></tr>";
					}else{
						var valueagree = "<tr style='border-top: 1px solid #ddd; background-color: #F7BE81;' rowspan='3'><th style='width: 15%;'></th>" +
											"<td style='width: 45%;'>"+bbsansweragree.answerNick1+"&nbsp;&nbsp;||&nbsp;&nbsp;"+bbsansweragree.answerDate1+"</td>"+
											"<td style='width: 40%; text-align: right;'>"+
											"<a href='javascript:answeragree("+bbsansweragree.answerNum1+")'><img src='images/공감.png'>" +
											"</a>&nbsp;"+bbsansweragree.answerAgree1+"&nbsp;<a href='javascript:answernotagree("+bbsansweragree.answerNum1+")'>" +
											"<img src='images/비공감.png'></a>&nbsp;"+bbsansweragree.answerNotAgree1+"</td>"+
											"</tr><tr style='background-color: #F7BE81;'><td></td><td colspan='2'>"+bbsansweragree.answerContent1+"</td></tr>"+
											"<tr style='background-color: #F7BE81;'><td colspan='3' align='right'><a href='javascript:answerdelete("+bbsansweragree.answerNum1+")'>[삭제]</a>" +
											"&nbsp;<a href='javascript:replyans("+bbsansweragree.reply1+")'>[답글]</a></td></tr>";
						
					}
				}else{
					if(bbsansweragree.reply1 == 0){
						var valueagree = "<tr style='border-top: 1px solid #ddd; background-color: #F7BE81;'><th style='width: 15%;'>"+bbsansweragree.answerNick1+"</th>" +
											"<td style='width: 15%;'>"+bbsansweragree.answerDate1+"</td>"+
											"<td style='width: 70%; text-align: right;'>"+
											"<a href='javascript:answeragree("+bbsansweragree.answerNum1+")'><img src='images/공감.png'>" +
											"</a>&nbsp;"+bbsansweragree.answerAgree1+"&nbsp;<a href='javascript:answernotagree("+bbsansweragree.answerNum1+")'>" +
											"<img src='images/비공감.png'></a>&nbsp;"+bbsansweragree.answerNotAgree1+"</td>"+
											"</tr><tr style='background-color: #F7BE81;'><td colspan='3'>"+bbsansweragree.answerContent1+"</td></tr>"+
											"<tr style='background-color: #F7BE81;'><td colspan='3' align='right'><a href='javascript:replyans("+bbsansweragree.answerNum1+")'>[답글]</a></td></tr>";
					}else{
						var valueagree = "<tr style='border-top: 1px solid #ddd; background-color: #F7BE81;' rowspan='3'><th style='width: 15%;'></th>" +
											"<td style='width: 45%;'>"+bbsansweragree.answerNick1+"&nbsp;&nbsp;||&nbsp;&nbsp;"+bbsansweragree.answerDate1+"</td>"+
											"<td style='width: 40%; text-align: right;'>"+
											"<a href='javascript:answeragree("+bbsansweragree.answerNum1+")'><img src='images/공감.png'>" +
											"</a>&nbsp;"+bbsansweragree.answerAgree1+"&nbsp;<a href='javascript:answernotagree("+bbsansweragree.answerNum1+")'>" +
											"<img src='images/비공감.png'></a>&nbsp;"+bbsansweragree.answerNotAgree1+"</td>"+
											"</tr><tr style='background-color: #F7BE81;'><td></td><td colspan='2'>"+bbsansweragree.answerContent1+"</td></tr>"+
											"<tr style='background-color: #F7BE81;'><td colspan='3' align='right'>" +
											"&nbsp;<a href='javascript:replyans("+bbsansweragree.reply1+")'>[답글]</a></td></tr>";
					}
				}
				
			$("#bbstbodyanswer").append(valueagree);
			
			}
//			console.log(bbsansweragree);
			
			for(var i = 0; i<result.bbsans.length ; i++){
				answer = result.bbsans[i];
//				console.log(answer.reply);
				
				if(answer.reply == 0){
					if(loginNick == answer.answerNick || loginNick == "세시입니다"){
//						console.log(answer.answerNum);
						var value = "<tr style='border-top: 1px solid #ddd;'><th style='width: 15%;'>"+answer.answerNick+"</th>" +
											"<td style='width: 15%;'>"+answer.answerDate+"</td>"+
											"<td style='width: 70%; text-align: right;'>"+
											"<a href='javascript:answeragree("+answer.answerNum+")'><img src='images/공감.png'>" +
											"</a>&nbsp;"+answer.answerAgree+"&nbsp;<a href='javascript:answernotagree("+answer.answerNum+")'>" +
											"<img src='images/비공감.png'></a>&nbsp;"+answer.answerNotAgree+"</td>"+
											"</tr><tr><td colspan='3'>"+answer.answerContent+"</td></tr>"+
											"<tr><td colspan='3' align='right'><a href='javascript:answerdelete("+answer.answerNum+")'>[삭제]" +
											"</a>&nbsp;<a href='javascript:replyans("+answer.answerNum+")'>[답글]</a></td></tr>"+
											"<tr class='answernum"+answer.answerNum+"'></tr><tr class='answernum2"+answer.answerNum+"'></tr>" +
											"<tr class='answernum3"+answer.answerNum+"'></tr><tr class='answernum4"+answer.answerNum+"'></tr>"+
											"<tr class='answernum5"+answer.answerNum+"'></tr><tr class='answernum6"+answer.answerNum+"'></tr>"+
											"<tr class='answernum7"+answer.answerNum+"'></tr><tr class='answernum8"+answer.answerNum+"'></tr>"+
											"<tr class='answernum9"+answer.answerNum+"'></tr><tr class='answernum10"+answer.answerNum+"'></tr>"+
											"<tr class='answernum11"+answer.answerNum+"'></tr><tr class='answernum12"+answer.answerNum+"'></tr>"+
											"<tr class='answernum13"+answer.answerNum+"'></tr><tr class='answernum14"+answer.answerNum+"'></tr>"+
											"<tr class='answernum15"+answer.answerNum+"'></tr><tr class='answernum16"+answer.answerNum+"'></tr>"+
											"<tr class='answernum17"+answer.answerNum+"'></tr><tr class='answernum18"+answer.answerNum+"'></tr>"+
											"<tr class='answernum19"+answer.answerNum+"'></tr><tr class='answernum20"+answer.answerNum+"'></tr>"+
											"<tr class='answernum21"+answer.answerNum+"'></tr><tr class='answernum22"+answer.answerNum+"'></tr>"+
											"<tr class='answernum23"+answer.answerNum+"'></tr><tr class='answernum24"+answer.answerNum+"'></tr>"+
											"<tr class='answernum25"+answer.answerNum+"'></tr><tr class='answernum26"+answer.answerNum+"'></tr>"+
											"<tr class='answernum27"+answer.answerNum+"'></tr><tr class='answernum28"+answer.answerNum+"'></tr>"+
											"<tr class='answernum29"+answer.answerNum+"'></tr><tr class='answernum30"+answer.answerNum+"'></tr>";
						
						$.ajax({
							url:'/BbsViewReplyAction.bo',
							type:"post",
							dataType:'json',
							data:{"bbsNumCheck":bbsnum, "answerNum":answer.answerNum},
							success:function(reply){
								
								for(var t = 0; t<reply.replyans.length; t++){
									var replylist = reply.replyans[t];
									
									
									var replyans = "<th style='width: 15%; text-align: center;' rowspan='3'><img src='images/화살표3.png' /></th>"+
									"<th style='width: 45%; background-color: #eeeeee;'>"+replylist.replyNickcheck+"&nbsp;&nbsp;||&nbsp;&nbsp;"+replylist.replyDate+"</th>"+
									"<td style='width: 40%; text-align: right; background-color: #eeeeee;'>"+
									"<a href='javascript:answeragree("+replylist.replyNum+")'><img src='images/공감.png'>" +
									"</a>&nbsp;"+replylist.replyAgree+"&nbsp;<a href='javascript:answernotagree("+replylist.replyNum+")'>" +
									"<img src='images/비공감.png'></a>&nbsp;"+replylist.replyNotAgree+"</td>";
									
									
									
									var replyans2 = "<td colspan='2'  style='background-color: #eeeeee;'>"+replylist.replyContent+"</td>"
									
									if(loginNick == replylist.replyNickcheck || loginNick == "세시입니다"){
										var replyans3 = "<td colspan='2' align='right' style='background-color: #eeeeee;'>" +
										"<a href='javascript:answerdelete("+replylist.replyNum+")'>[삭제]</a>&nbsp;" +
												"<a href='javascript:replyans("+replylist.reply+")'>[답글]</a></td>";
									}else{
										var replyans3 = "<td colspan='2' align='right' style='background-color: #eeeeee;'>" +
												"<a href='javascript:replyans("+replylist.reply+")'>[답글]</a></td>";
									}
//									console.log(replylist.reply);
//									console.log(2+(t*3));
									$('table').find('.answernum'+(2+(t*3))+''+replylist.reply).html(replyans);
									$('table').find('.answernum'+(3+(t*3))+''+replylist.reply).html(replyans2);
									$('table').find('.answernum'+(4+(t*3))+''+replylist.reply).html(replyans3);
//									$("#answernum"+reply.replyNum+"+t").append(replytest);
								}
								
							}
						});
						$("#bbstbodyanswer").append(value);
						
					}else{
						var value = "<tr style='border-top: 1px solid #ddd;'><th style='width: 15%;'>"+answer.answerNick+"</th>" +
											"<td style='width: 15%;'>"+answer.answerDate+"</td>"+
											"<td style='width: 70%; text-align: right;'>"+
											"<a href='javascript:answeragree("+answer.answerNum+")'><img src='images/공감.png'>" +
											"</a>&nbsp;"+answer.answerAgree+"&nbsp;<a href='javascript:answernotagree("+answer.answerNum+")'>" +
											"<img src='images/비공감.png'></a>&nbsp;"+answer.answerNotAgree+"</td>"+
											"</tr><tr><td colspan='3'>"+answer.answerContent+"</td></tr>"+
											"<tr><td colspan='3' align='right'><a href='javascript:replyans("+answer.answerNum+")'>[답글]</a></td></tr>" +
											"<tr class='answernum"+answer.answerNum+"'></tr><tr class='answernum2"+answer.answerNum+"'></tr>" +
											"<tr class='answernum3"+answer.answerNum+"'></tr><tr class='answernum4"+answer.answerNum+"'></tr>"+
											"<tr class='answernum5"+answer.answerNum+"'></tr><tr class='answernum6"+answer.answerNum+"'></tr>"+
											"<tr class='answernum7"+answer.answerNum+"'></tr><tr class='answernum8"+answer.answerNum+"'></tr>"+
											"<tr class='answernum9"+answer.answerNum+"'></tr><tr class='answernum10"+answer.answerNum+"'></tr>"+
											"<tr class='answernum11"+answer.answerNum+"'></tr><tr class='answernum12"+answer.answerNum+"'></tr>"+
											"<tr class='answernum13"+answer.answerNum+"'></tr><tr class='answernum14"+answer.answerNum+"'></tr>"+
											"<tr class='answernum15"+answer.answerNum+"'></tr><tr class='answernum16"+answer.answerNum+"'></tr>"+
											"<tr class='answernum17"+answer.answerNum+"'></tr><tr class='answernum18"+answer.answerNum+"'></tr>"+
											"<tr class='answernum19"+answer.answerNum+"'></tr><tr class='answernum20"+answer.answerNum+"'></tr>"+
											"<tr class='answernum21"+answer.answerNum+"'></tr><tr class='answernum22"+answer.answerNum+"'></tr>"+
											"<tr class='answernum23"+answer.answerNum+"'></tr><tr class='answernum24"+answer.answerNum+"'></tr>"+
											"<tr class='answernum25"+answer.answerNum+"'></tr><tr class='answernum26"+answer.answerNum+"'></tr>"+
											"<tr class='answernum27"+answer.answerNum+"'></tr><tr class='answernum28"+answer.answerNum+"'></tr>"+
											"<tr class='answernum29"+answer.answerNum+"'></tr><tr class='answernum30"+answer.answerNum+"'></tr>";
						$.ajax({
							url:'/BbsViewReplyAction.bo',
							type:"post",
							dataType:'json',
							data:{"bbsNumCheck":bbsnum, "answerNum":answer.answerNum},
							success:function(reply){
								
								for(var t = 0; t<reply.replyans.length; t++){
									var replylist = reply.replyans[t];
									
									
									var replyans = "<th style='width: 15%; text-align: center;' rowspan='3'><img src='images/화살표3.png' /></th>"+
									"<th style='width: 45%; background-color: #eeeeee;'>"+replylist.replyNickcheck+"&nbsp;&nbsp;||&nbsp;&nbsp;"+replylist.replyDate+"</th>"+
									"<td style='width: 40%; text-align: right; background-color: #eeeeee;'>"+
									"<a href='javascript:answeragree("+replylist.replyNum+")'><img src='images/공감.png'>" +
									"</a>&nbsp;"+replylist.replyAgree+"&nbsp;<a href='javascript:answernotagree("+replylist.replyNum+")'>" +
									"<img src='images/비공감.png'></a>&nbsp;"+replylist.replyNotAgree+"</td>";
									
									
									
									var replyans2 = "<td colspan='2'  style='background-color: #eeeeee;'>"+replylist.replyContent+"</td>"
									
									if(loginNick == replylist.replyNickcheck || loginNick == "세시입니다"){
										var replyans3 = "<td colspan='2' align='right' style='background-color: #eeeeee;'>" +
										"<a href='javascript:answerdelete("+replylist.replyNum+")'>[삭제]</a>&nbsp;" +
												"<a href='javascript:replyans("+replylist.reply+")'>[답글]</a></td>";
									}else{
										var replyans3 = "<td colspan='2' align='right' style='background-color: #eeeeee;'>" +
												"<a href='javascript:replyans("+replylist.reply+")'>[답글]</a></td>";
									}
									
									$('table').find('.answernum'+(2+(t*3))+''+replylist.reply).html(replyans);
									$('table').find('.answernum'+(3+(t*3))+''+replylist.reply).html(replyans2);
									$('table').find('.answernum'+(4+(t*3))+''+replylist.reply).html(replyans3);
//									$("#answernum"+reply.replyNum+"+t").append(replytest);
								}
								
							}
						});
						$("#bbstbodyanswer").append(value);
					}
				
				}
//		 		console.log(answer);
			}
		}
	});
	
	
	
	$("#textanswerbtn").click(function(){
		$("#bbstbodyanswer").empty();
		$("#bbstheadanswer").empty();
		var textarea = $("#textanswer").val();
//		console.log(textarea);
		$("#textanswer").val('');
		var answerNick = $("#answerNick").val();
//		console.log(answerNick);
		
		if(textarea == ""){
			alert("내용을 입력해 주세요^^!");
			location.reload();
		}else{
		
		$.ajax({
			url:'/BbsInsertAnswerAction.bo',
			type:"POST",
			dataType: 'json',
			data:{"bbsAnswerContent":textarea, "bbsNumCheck":bbsnum, "bbsAnswerNick":answerNick},
			success:function(params){
//				console.log(params.bbsNumCheck);
				
				$.ajax({
					url:'/BbsViewAnswerAction.bo',
					type:"post",
					dataType:'json',
					data:{"bbsNumCheck":params.bbsNumCheck},
					success:function(result1){
						
						location.reload();
					}
				});
				
				
			}
		});
		}
	});
	
});
}
//answerready끝

function bbsanswerblank(bbsNum){
	window.open("/BbsDetailAction.bo?num="+bbsNum+"&answerCheck=yes", "", "width=900, height=500, status=1");
}

function bbsanswerview(bbsNum){
	var bbsNick = $("#loginNick").val();
//	location.href = '/BbsDetailAction.bo?num='+bbsNum;
	opener.location.href = "/BbsDetailAction.bo?num="+bbsNum+"&userNick="+bbsNick;
    opener.focus();

	self.close();

}

function logincheck3(){
	alert("로그인 하세요^^");
	return null;
}


$(function(){
		$("#bbsviewcount").change(function(){
		var value = this.value;
		var opt = $("#bbsopt").val();
		var condition = $("#bbscondition").val();
		console.log(opt);
		console.log(condition);
//		alert(value);
		if(opt != null &condition != null)
			location.href = "/BbsListAction.bo?bbsviewcount="+value+"&opt="+opt+"&condition="+condition;
		else
			location.href = "/BbsListAction.bo?bbsviewcount="+value;
			
	});
});

function answeragree(answernum){
	var agree = "yes";
	
	$.ajax({
		url:'/BbsAnswerAgreeAction.bo',
		type:"post",
		dataType:'json',
		data:{"answernum":answernum, "agree":agree},
		success:function(result){
			
//			answerready();
			location.reload();
		}
	});
}

function answernotagree(answernum){
	var agree = "no";
	
	$.ajax({
		url:'/BbsAnswerAgreeAction.bo',
		type:"post",
		dataType:'json',
		data:{"answernum":answernum, "agree":agree},
		success:function(result){
			
//			answerready();
			location.reload();
		}
	});
}

function answerdelete(answernum){
	
	var boolean = confirm('댓글을 삭제 하시겠습니까?');
	
	if(boolean == true){
		
		$.ajax({
			url:'/BbsAnswerDeleteAction.bo',
			type:"post",
			dataType:'json',
			data:{"answernum":answernum},
			success:function(result){
				
//				answerready();
				location.reload();
			}
		});
		
	}else{}
}


function replyans(answernum){
	loginNick = $("#loginNick").val();
	var bbsNum = $("#numtestvalue").val();
	
	var reply = "<th style='width: 15%; text-align: center; vertical-align: top;'><img src='images/화살표3.png' /></th><td colspan='3'><form name='replyform'>" +
		"<textarea rows='2' cols='3' style='width: 87%;' id='replytext' name='replytext'></textarea>"+
		"<a class='btn btn-primary pull-right' style='padding: 10px 15px; width: 12%;' id='replybtn'>답글 등록</a>"+
		"</form></td>"

//	var answerreply = $('table').find('.answernum'+answernum+'').css('background-color', 'red');
	$('table').find('.answernum'+answernum+'').html(reply);
	$('#replytext').focus();
	
	$("#replybtn").click(function(){
		var replytext = $("#replytext").val();
		
		if(replytext == ""){
			alert('내용을 입력해 주세요^^!');
		}else{
			
			$.ajax({
				url:'/BbsInsertAnsReplyAction.bo',
				type:"POST",
				dataType: 'json',
				data:{"replyContent":replytext, "replynum":answernum, "bbsNum":bbsNum, "replyNick":loginNick},
				success:function(result){
					location.reload();
				}
			});
		}
	});
}

