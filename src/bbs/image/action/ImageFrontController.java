package bbs.image.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 public class ImageFrontController 
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
		   
		   if(command.equals("/ImgListAction.img")){
			   action = new ImgListAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/Imagebbs.img")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("imagebbs.jsp");
		   }
		   else if(command.equals("/Imagewrite.img")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("page/test/imagewrite.jsp");
		   }
		   else if(command.equals("/Imagetest.img")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("imagetest.jsp");
		   }
		   else if(command.equals("/ImgInsertAction.img")){
			   action = new ImgInsertAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/ImgViewAction.img")){
			   action = new ImgViewAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/bbsImageView.img")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("imageview.jsp");
		   }
		   else if(command.equals("/ImgDeleteAction.img")){
			   action = new ImgDeleteAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/ImageModify.img")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("page/test/imagemodify.jsp");
		   }
		   else if(command.equals("/ImgModifyView.img")){
			   action = new ImgModifyView();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/ImgModifyAction.img")){
			   action = new ImgModifyAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   else if(command.equals("/ImgAnswerInsertAction.img")){
			   action = new ImgAnswerInsertAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 }
		   else if(command.equals("/ImgViewAnswerAction.img")){
			   action = new ImgViewAnswerAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 }
		   else if(command.equals("/ImgViewAnswerOpen.img")){
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("imageanswerview.jsp");
		   }
		   else if(command.equals("/imgAnswerAgreeAction.img")){
			   action = new imgAnswerAgreeAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 }
		   else if(command.equals("/imgAnswerDeleteAction.img")){
			   action = new imgAnswerDeleteAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 }
		   else if(command.equals("/imgInsertAnsReplyAction.img")){
			   action = new imgInsertAnsReplyAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 }
		   else if(command.equals("/imgViewReplyAction.img")){
			   action = new imgViewReplyAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 }
		   else if(command.equals("/imgHeartCountAction.img")){
			   action = new imgHeartCountAction();
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