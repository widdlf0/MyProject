package map.map.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MapFrontController extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.print("member");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;

		if (command.equals("/MapInsert.mp")) {
			action = new MapInsertAction();
			  try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 } else if (command.equals("/maps.mp")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("maps.jsp");
		} else if (command.equals("/mapsSearch.mp")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("mapsSearch.jsp");
		}
		 else if(command.equals("/MapList.mp")){
			   action = new MapListAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 }
		 else if(command.equals("/MapDetailViewAction.mp")){
			   action = new MapDetailViewAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 }
		 else if(command.equals("/MapAverageAnswerAction.mp")){
			   action = new MapAverageAnswerAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 }
		 else if(command.equals("/MapInsertAnswerAction.mp")){
			   action = new MapInsertAnswerAction();
			   try{
				   action.executeAjax(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		 }
		

		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("get");
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
}