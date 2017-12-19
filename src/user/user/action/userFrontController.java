package user.user.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 public class userFrontController 
 	extends javax.servlet.http.HttpServlet 
 	implements javax.servlet.Servlet {
	 static final long serialVersionUID = 1L;
	 protected void doProcess(HttpServletRequest request, HttpServletResponse response) 
	 	throws ServletException, IOException {
		 System.out.print("member");
		 String RequestURI=request.getRequestURI();
		 String contextPath=request.getContextPath();
		 String command=RequestURI.substring(contextPath.length());
		 ActionForward forward=null;
		 Action action=null;
		   
		   if(command.equals("/loginAction.us")){
			   action = new loginAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		 	else if(command.equals("/login.us")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("login.jsp");
		   }else if(command.equals("/main.us")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("main.jsp");
		   }else if(command.equals("/index.us")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("index.jsp");
		   }
		   else if(command.equals("/join.us")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("join.jsp");
		   }
			   else if(command.equals("/logoutAction.us")){
			   action = new userLogoutAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
			   else if(command.equals("/userjoinAction.us")){
				   System.out.println("===1===");
				   action = new userjoinAction();
				   System.out.println("===1.1===");
				   try{
					   System.out.println("===1.2===");
					   forward=action.execute(request, response);
					   System.out.println("===1.3===");
				   }catch(Exception e){
					   e.printStackTrace();
					   System.out.println("===1.4===");
				   }
			   }
			   else if(command.equals("/usermyhome.us")){
				   forward=new ActionForward();
				   forward.setRedirect(false);
				   forward.setPath("usermyhome.jsp");
			   }
			   else if(command.equals("/userIdCheck.us")){
			   action = new userIdCheck();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   	}
			   }
			   else if(command.equals("/userPwCheck.us")){
				   action = new userPwCheck();
				   try{
					   action.executeAjax(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/userNickCheck.us")){
				   action = new userNickCheck();
				   try{
					   action.executeAjax(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/usermyhomemodify.us")){
				   forward=new ActionForward();
				   forward.setRedirect(false);
				   forward.setPath("usermyhomemodify.jsp");
			   }
			   else if(command.equals("/userModifyAction.us")){
				   action = new userModifyAction();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/usermyhomepwmodify.us")){
				   forward=new ActionForward();
				   forward.setRedirect(false);
				   forward.setPath("usermyhomepwmodify.jsp");
			   }
			   else if(command.equals("/userPwModifyAction.us")){
				   action = new userPwModifyAction();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/usermyhomedelete.us")){
				   forward=new ActionForward();
				   forward.setRedirect(false);
				   forward.setPath("usermyhomedelete.jsp");
			   }
			   else if(command.equals("/userMyHomeDeleteAction.us")){
				   action = new userMyHomeDeleteAction();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/admin_user_black.us")){
				   forward=new ActionForward();
				   forward.setRedirect(false);
				   forward.setPath("admin_user_black.jsp");
			   }
			   else if(command.equals("/adminUserListAction.us")){
				   action = new adminUserListAction();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/adminUserBlackAction.us")){
				   action = new adminUserBlackAction();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/adminUserBlackClearAction.us")){
				   action = new adminUserBlackClearAction();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/adminUserDeleteAction.us")){
				   action = new adminUserDeleteAction();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/passwordfind.us")){
				   forward=new ActionForward();
				   forward.setRedirect(false);
				   forward.setPath("userpwfind.jsp");
			   }
			   else if(command.equals("/userIdCheck2.us")){
				   action = new userIdCheck2();
				   try{
					   action.executeAjax(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   	}
				   }
			   else if(command.equals("/SendpwMail.us")){
				   action = new userSendpwMail();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/idfind.us")){
				   forward=new ActionForward();
				   forward.setRedirect(false);
				   forward.setPath("useridfind.jsp");
			   }
			   else if(command.equals("/userNameCheck.us")){
				   action = new userNameCheck();
				   try{
					   action.executeAjax(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/SendidMail.us")){
				   action = new userSendidMail();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/SendMail.us")){
				   action = new userSendMail();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/SendMailck.us")){
				   forward=new ActionForward();
				   forward.setRedirect(false);
				   forward.setPath("CheckMail.jsp");
			   }
			   else if(command.equals("/adminUserDetail.us")){
				   action = new adminUserDetail();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   else if(command.equals("/adminUserDetailView.us")){
				   forward=new ActionForward();
				   forward.setRedirect(false);
				   forward.setPath("admin_user_detail.jsp");
			   }
			   else if(command.equals("/email.us")){
				   action = new userinquiry();
				   try{
					   forward=action.execute(request, response);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }

		   if(forward != null){
		   if(forward.isRedirect()){
			   response.sendRedirect(forward.getPath());
		   }else{
			   RequestDispatcher dispatcher=
				   request.getRequestDispatcher(forward.getPath());
			   dispatcher.forward(request, response);
		   }
		   }
	 }
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		System.out.println("get");
		doProcess(request,response);
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doProcess(request,response);
	}   	  	      	    
}