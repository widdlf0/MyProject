

//아이디 체크
$( document ).ready(function() {
    console.log( "ready!" );
   $("#userID").keyup(function(){
	   
	   var userID = $("#userID").val(); 
	  	
	    $.ajax({      
	        type:"POST",  
	        url:"/userIdCheck.us", 
	        data:{"userID": userID},  
	        dataType:"json",
	        success:function(args){   
	      	
	            if(args.result==true){
	            	$("#joinbtn").prop("disabled", false);
	            	$("#joinbtn").css("background-color", "blue");
	            	$("#userID").css("background-color", "#D0F5A9");
	            	$("#userIDcheck").css("color", "green");
	            	$("#userIDcheck").text(args.userIDcheck);
	            	$("#userIDcheck").show();
	            	
	            }else if(args.result==false){
	            	$("#joinbtn").prop("disabled", true);
	            	$("#joinbtn").css("background-color", "red");
	            	$("#userID").css("background-color", "red");
	            	$("#userIDcheck").css("color", "red");
	            	$("#userIDcheck").text(args.userIDcheck);
	            	$("#userIDcheck").show();
	            	
	            } 
	        	
	        }  
	    }); 
   });
});



//비밀번호 체크
$( document ).ready(function() {
//  console.log( "ready!" );
 $("#userPasswordcheck").keyup(function(){
	   
	   var userPassword = $("#userPassword").val(); 
	   var userPasswordcheck = $("#userPasswordcheck").val();
   
	    $.ajax({      
	        type:"POST",  
	        url:"/userPwCheck.us", 
	        data:{"userPassword": userPassword, "userPasswordcheck": userPasswordcheck},  
	        dataType:"json",
	        success:function(args){   
	      	
	            if(args.result2=="success"){
	            	$("#joinbtn").prop("disabled", false);
	            	$("#joinbtn").css("background-color", "blue");
	            	$("#userPasswordcheck").css("background-color", "#D0F5A9");
	            	$("#userPasswordcheck2").css("color", "green");
	            	$("#userPasswordcheck2").text(args.userPasswordcheck2);
	            	$("#userPasswordcheck2").show();
	            	
	            }else if(args.result2=="fail"){
	            	$("#joinbtn").prop("disabled", true);
	            	$("#joinbtn").css("background-color", "red");
	            	$("#userPasswordcheck").css("background-color", "red");
	            	$("#userPasswordcheck2").css("color", "red");
	            	$("#userPasswordcheck2").text(args.userPasswordcheck2);
	            	$("#userPasswordcheck2").show();
	            	
	            } 
	        	
	        }  
	    }); 
 });
});

//닉네임 체크
$( document ).ready(function() {
    console.log( "ready!" );
   $("#userNick").keyup(function(){
	   
	   var userNick = $("#userNick").val(); 
	  	
	    $.ajax({      
	        type:"POST",  
	        url:"/userNickCheck.us", 
	        data:{"userNick": userNick},  
	        dataType:"json",
	        success:function(args){   
	      	
	            if(args.result3==true){
	            	$("#joinbtn").prop("disabled", false);
	            	$("#joinbtn").css("background-color", "blue");
	            	$("#userNick").css("background-color", "#D0F5A9");
	            	$("#userNickcheck").css("color", "green");
	            	$("#userNickcheck").text(args.userNickcheck);
	            	$("#userNickcheck").show();
	            	
	            }else if(args.result3==false){
	            	$("#joinbtn").prop("disabled", true);
	            	$("#joinbtn").css("background-color", "red");
	            	$("#userNick").css("background-color", "red");
	            	$("#userNickcheck").css("color", "red");
	            	$("#userNickcheck").text(args.userNickcheck);
	            	$("#userNickcheck").show();
	            	
	            } 
	        	
	        }  
	    }); 
   });
});
