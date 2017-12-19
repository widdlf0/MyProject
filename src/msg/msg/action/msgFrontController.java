package msg.msg.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 public class msgFrontController 
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
		   
		   if(command.equals("/message.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("message.jsp");
		   }
		   else if(command.equals("/messagewrite.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("messagewrite.jsp");
		   }
		   else if(command.equals("/msgWriteAction.mg")){
			   action = new msgWriteAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/msgNewListAction.mg")){
			   action = new msgNewListAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/messageview.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("messageview.jsp");
		   }
		   else if(command.equals("/msgDetailAction.mg")){
			   action = new msgDetailAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/messageOk.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("messageOk.jsp");
		   }
		   else if(command.equals("/msgDetailOkAction.mg")){
			   action = new msgDetailOkAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/msgReadListAction.mg")){
			   action = new msgReadListAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/messagereadview.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("messagereadview.jsp");
		   }
		   else if(command.equals("/messagesend.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("messageSend.jsp");
		   }
		   else if(command.equals("/msgSendListAction.mg")){
			   action = new msgSendListAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/msgDetailSendAction.mg")){
			   action = new msgDetailSendAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/messagesendview.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("messagesendview.jsp");
		   }
		   else if(command.equals("/messagetrashbox.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("messagetrashbox.jsp");
		   }
		   else if(command.equals("/msgTrashBoxListAction.mg")){
			   action = new msgTrashBoxListAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/msgDetailTrashBoxAction.mg")){
			   action = new msgDetailTrashBoxAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/messagetrashboxview.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("messagetrashboxview.jsp");
		   }
		   else if(command.equals("/msgdeletealert.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("msgdeletealert.jsp");
		   }
		   else if(command.equals("/msgDeleteAction.mg")){
			   action = new msgDeleteAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/msgLiveAction.mg")){
			   action = new msgLiveAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/msgSendTrashBoxUpdate.mg")){
			   action = new msgSendTrashBoxUpdate();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/msgwriteanswer.mg")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("messagewriteanswer.jsp");
		   }
		   else if(command.equals("/msgTrashBoxAllDeleteAction.mg")){
			   action = new msgTrashBoxAllDeleteAction();
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