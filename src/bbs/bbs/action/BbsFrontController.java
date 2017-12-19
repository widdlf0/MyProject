package bbs.bbs.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 public class BbsFrontController 
 	extends javax.servlet.http.HttpServlet 
 	implements javax.servlet.Servlet {
	 static final long serialVersionUID = 1L;
	 protected void doProcess(HttpServletRequest request, HttpServletResponse response) 
	 	throws ServletException, IOException {
		 //System.out.print("member");
		 String RequestURI=request.getRequestURI();
		 String contextPath=request.getContextPath();
		 String command=RequestURI.substring(contextPath.length());
		 ActionForward forward=null;
		 Action action=null;
		   
		   if(command.equals("/BbsListAction.bo")){
			   action = new BbsListAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/BbsWriteAction.bo")){
			   action = new BbsWriteAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/bbs.bo")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("bbs.jsp");
		   }
		   else if(command.equals("/write.bo")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("page/test/bbswrite.jsp");
		   }
		   else if(command.equals("/BbsDetailAction.bo")){
			   action = new BbsDetailAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/BbsModifyView.bo")){
			   action = new BbsModifyView();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/BbsModifyAction.bo")){
			   action = new BbsModifyAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/bbsmodify.bo")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("page/test/bbsmodify.jsp");
		   }
		   else if(command.equals("/bbsview.bo")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("bbsview.jsp");
		   }
		   else if(command.equals("/isBbsWriterfalse.bo")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("isBbsWriterfalse.jsp");
		   }
		   else if(command.equals("/BbsDeleteAction.bo")){
			   action = new BbsDeleteAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/deletealert.bo")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("deletealert.jsp");
		   }
		   else if(command.equals("/mapSerch.bo")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("mapSerch.jsp");
		   }
		   else if(command.equals("/BbsViewAnswerAction.bo")){
			   action = new BbsViewAnswerAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/BbsInsertAnswerAction.bo")){
			   action = new BbsInsertAnswerAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/bbsanswerview.bo")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("bbsanswerview.jsp");
		   }
		   else if(command.equals("/BbsAnswerAgreeAction.bo")){
			   action = new BbsAnswerAgreeAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/BbsAnswerDeleteAction.bo")){
			   action = new BbsAnswerDeleteAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/BbsInsertAnsReplyAction.bo")){
			   action = new BbsInsertAnsReplyAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/BbsViewReplyAction.bo")){
			   action = new BbsViewReplyAction();
			   try{
				   action.executeAjax(request, response);
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