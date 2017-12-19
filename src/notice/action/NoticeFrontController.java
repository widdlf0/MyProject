package notice.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 public class NoticeFrontController 
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
		   
		   if(command.equals("/NoticeListAction.no")){
			   action = new NoticeListAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/NoticeWriteAction.no")){
			   action = new NoticeWriteAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/notice.no")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("/notice/notice.jsp");
		   }
		   else if(command.equals("/write.no")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("/notice/noticewrite.jsp");
		   }
		   else if(command.equals("/NoticeDetailAction.no")){
			   action = new NoticeDetailAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/NoticeModifyView.no")){
			   action = new NoticeModifyView();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/NoticeModifyAction.no")){
			   action = new NoticeModifyAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/noticemodify.no")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("/notice/noticemodify.jsp");
		   }
		   else if(command.equals("/noticeview.no")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("/notice/noticeview.jsp");
		   }
		   else if(command.equals("/isBbsWriterfalse.no")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("isBbsWriterfalse.jsp");
		   }
		   else if(command.equals("/NoticeDeleteAction.no")){
			   action = new NoticeDeleteAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/noticedeletealert.no")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("/notice/noticedeletealert.jsp");
		   }
//		   else if(command.equals("/mapSerch.bo")){
//			   forward=new ActionForward();
//			   forward.setRedirect(false);
//			   forward.setPath("mapSerch.jsp");
//		   }
//		   else if(command.equals("/BbsViewAnswerAction.bo")){
//			   action = new BbsViewAnswerAction();
//			   try{
//				   action.executeAjax(request, response);
//			   }catch(Exception e){
//				   e.printStackTrace();
//			   }
//		   }
//		   else if(command.equals("/BbsInsertAnswerAction.bo")){
//			   action = new BbsInsertAnswerAction();
//			   try{
//				   action.executeAjax(request, response);
//			   }catch(Exception e){
//				   e.printStackTrace();
//			   }
//		   }
//		   else if(command.equals("/bbsanswerview.bo")){
//			   forward=new ActionForward();
//			   forward.setRedirect(false);
//			   forward.setPath("bbsanswerview.jsp");
//		   }
		   
		   
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