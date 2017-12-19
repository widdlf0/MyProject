var answerclick;
var answer;
var loginNick;
var agreeans;


function imageanswer(){
	
	$(document).ready(function(){
//		console.log("ready!");
		$("#imgtheadanswer").empty();
		$("#imgtbodyanswer").empty();
		
		var imagenum = $("#numtestvalue").val();
		loginNick = $("#loginNick").val();
		
		$.ajax({
			url:'/ImgViewAnswerAction.img',
			type:"post",
			dataType:'json',
			data:{"imgNumCheck":imagenum},
			success:function(Answer1){
				
				var answercount = "<tr style='background-color: #eeeeee; text-align: left;'>"+
												"<th colspan='3' >댓글 현황&nbsp;(&nbsp;"+Answer1.answercount+"&nbsp;)</th></tr>";
				
				$("#imgtheadanswer").append(answercount);
				
				//베스트 공감 댓글
				for(var j = 0; j<Answer1.agreeans.length; j++){
					agreeans = Answer1.agreeans[j];
					console.log(agreeans);
					if(loginNick == agreeans.answerNick || loginNick == "세시입니다"){
						if(agreeans.replyNum == 0){
							var valueagree = "<tr style='border-top: 1px solid #ddd; background-color: #F7BE81;'><th style='width: 15%;'>"+agreeans.answerNick+"</th>" +
												"<td style='width: 15%;'>"+agreeans.answerDate+"</td>"+
												"<td style='width: 70%; text-align: right;'>"+
												"<a href='javascript:answeragree("+agreeans.answerNum+")'><img src='images/공감.png'>" +
												"</a>&nbsp;"+agreeans.answerAgree+"&nbsp;<a href='javascript:answernotagree("+agreeans.answerNum+")'>" +
												"<img src='images/비공감.png'></a>&nbsp;"+agreeans.answerNotAgree+"</td>"+
												"</tr><tr style='background-color: #F7BE81;'><td colspan='3'>"+agreeans.answerContent+"</td></tr>"+
												"<tr style='background-color: #F7BE81;'><td colspan='3' align='right'><a href='javascript:answerdelete("+agreeans.answerNum+")'>[삭제]</a>" +
														"&nbsp;<a href='javascript:replyans("+agreeans.answerNum+")'>[답글]</a></td></tr>";
						}else{
							var valueagree = "<tr style='border-top: 1px solid #ddd; background-color: #F7BE81;' rowspan='3'><th style='width: 15%;'></th>" +
												"<td style='width: 45%;'>"+agreeans.answerNick+"&nbsp;&nbsp;||&nbsp;&nbsp;"+agreeans.answerDate+"</td>"+
												"<td style='width: 40%; text-align: right;'>"+
												"<a href='javascript:answeragree("+agreeans.answerNum+")'><img src='images/공감.png'>" +
												"</a>&nbsp;"+agreeans.answerAgree+"&nbsp;<a href='javascript:answernotagree("+agreeans.answerNum+")'>" +
												"<img src='images/비공감.png'></a>&nbsp;"+agreeans.answerNotAgree+"</td>"+
												"</tr><tr style='background-color: #F7BE81;'><td></td><td colspan='3'>"+agreeans.answerContent+"</td></tr>"+
												"<tr style='background-color: #F7BE81;'><td colspan='3' align='right'><a href='javascript:answerdelete("+agreeans.answerNum+")'>[삭제]</a>" +
												"&nbsp;<a href='javascript:replyans("+agreeans.replyNum+")'>[답글]</a></td></tr>";
							
						}
					}else{
						if(agreeans.replyNum == 0){
							var valueagree = "<tr style='border-top: 1px solid #ddd; background-color: #F7BE81;'><th style='width: 15%;'>"+agreeans.answerNick+"</th>" +
												"<td style='width: 15%;'>"+agreeans.answerDate+"</td>"+
												"<td style='width: 70%; text-align: right;'>"+
												"<a href='javascript:answeragree("+agreeans.answerNum+")'><img src='images/공감.png'>" +
												"</a>&nbsp;"+agreeans.answerAgree+"&nbsp;<a href='javascript:answernotagree("+agreeans.answerNum+")'>" +
												"<img src='images/비공감.png'></a>&nbsp;"+agreeans.answerNotAgree+"</td>"+
												"</tr><tr style='background-color: #F7BE81;'><td colspan='3'>"+agreeans.answerContent+"</td></tr>"+
												"<tr style='background-color: #F7BE81;'><td colspan='3' align='right'><a href='javascript:replyans("+agreeans.answerNum+")'>[답글]</a></td></tr>";
						}else{
							var valueagree ="<tr style='border-top: 1px solid #ddd; background-color: #F7BE81;' rowspan='3'><th style='width: 15%;'></th>" +
												"<td style='width: 45%;'>"+agreeans.answerNick+"&nbsp;&nbsp;||&nbsp;&nbsp;"+agreeans.answerDate+"</td>"+
												"<td style='width: 40%; text-align: right;'>"+
												"<a href='javascript:answeragree("+agreeans.answerNum+")'><img src='images/공감.png'>" +
												"</a>&nbsp;"+agreeans.answerAgree+"&nbsp;<a href='javascript:answernotagree("+agreeans.answerNum+")'>" +
												"<img src='images/비공감.png'></a>&nbsp;"+agreeans.answerNotAgree+"</td>"+
												"</tr><tr style='background-color: #F7BE81;'><td></td><td colspan='3'>"+agreeans.answerContent+"</td></tr>"+
												"<tr style='background-color: #F7BE81;'><td colspan='3' align='right'><a href='javascript:replyans("+agreeans.reply+")'>[답글]</a></td></tr>";
						}
					}
					console.log(valueagree);
				$("#imgtbodyanswer").append(valueagree);
				
				}
				
				
				
				for(var i = 0; i<Answer1.imgans.length ; i++){
					answer = Answer1.imgans[i];
//					console.log(answer.reply);
					
					if(answer.replyNum == 0){
						if(loginNick == answer.answerNick || loginNick == "세시입니다"){
//							console.log(answer.answerNum);
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
								url:'/imgViewReplyAction.img',
								type:"post",
								dataType:'json',
								data:{"imgNumCheck":imagenum, "answerNum":answer.answerNum},
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
//										console.log(replylist.reply);
//										console.log(2+(t*3));
										$('table').find('.answernum'+(2+(t*3))+''+replylist.reply).html(replyans);
										$('table').find('.answernum'+(3+(t*3))+''+replylist.reply).html(replyans2);
										$('table').find('.answernum'+(4+(t*3))+''+replylist.reply).html(replyans3);
//										$("#answernum"+reply.replyNum+"+t").append(replytest);
									}
									
								}
							});
							$("#imgtbodyanswer").append(value);
							
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
								url:'/imgViewReplyAction.img',
								type:"post",
								dataType:'json',
								data:{"imgNumCheck":imagenum, "answerNum":answer.answerNum},
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
//										$("#answernum"+reply.replyNum+"+t").append(replytest);
									}
									
								}
							});
							$("#imgtbodyanswer").append(value);
						}
					
					}
//			 		console.log(answer);
				}
			}
		});
		
		
		
		$("#textanswerbtn").click(function(){
			
			$("#imgtheadanswer").empty();
			$("#imgtbodyanswer").empty();
			
			var answerNick = $("#answerNick").val();
			var textanswer = $("#textanswer").val();
			$("#textanswer").val('');

			$.ajax({
				url:'/ImgAnswerInsertAction.img',
				type:"post",
				dataType:'json',
				data:{"imageAnswerContent":textanswer, "imageNumCheck":imagenum, "imageAnswerNick":answerNick},
				success:function(result){
					
					$.ajax({
						url:'/ImgViewAnswerAction.img',
						type:"post",
						dataType:'json',
						data:{"imgNumCheck":result.imgNumCheck},
						success:function(Answer){
							
							location.reload();
						}
						
					});
					
				}
			});
			
		});
	});
	
}


function logincheck3(){
	alert("로그인 하세요^^");
	return null;
}

function imageanswerblank(imgNum){
	window.open("/ImgViewAction.img?num="+imgNum+"&imganswerCheck=yes", "", "width=900, height=500, status=1");
}

function imganswerview(imgNum){
//	location.href = '/BbsDetailAction.bo?num='+bbsNum;
	var imgNick = $("#loginNick").val();
	opener.location.href = "/ImgViewAction.img?num="+imgNum+"&userNick="+imgNick;
    opener.focus();

	self.close();

}


function answeragree(answernum){
	var agree = "yes";
	
	$.ajax({
		url:'/imgAnswerAgreeAction.img',
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
		url:'/imgAnswerAgreeAction.img',
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
			url:'/imgAnswerDeleteAction.img',
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
	var imgNum = $("#numtestvalue").val();
	
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
				url:'/imgInsertAnsReplyAction.img',
				type:"POST",
				dataType: 'json',
				data:{"replyContent":replytext, "replynum":answernum, "imgNum":imgNum, "replyNick":loginNick},
				success:function(result){
					location.reload();
				}
			});
		}
	});
}

function heartcount(){
	var imgNum = $("#numtestvalue").val();
	
	$.ajax({
		url:'/imgHeartCountAction.img',
		type:"POST",
		dataType: 'json',
		data:{"imgNum":imgNum},
		success:function(count){
			location.reload();
		}
	});
}