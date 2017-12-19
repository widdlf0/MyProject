package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.db.UserDAO;

public class adminUserDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward=new ActionForward();
	   	response.setContentType("text/html;charset=UTF-8");
	   	
	   	UserDAO userDAO = new UserDAO();
	   	
	   	boolean result = false;
	   	String userID = request.getParameter("userIDdelete");
	   	result = userDAO.setAdminUserDeleteUpdate(userID);
	   	
	   	

	   	PrintWriter out=response.getWriter();
	   	if(result == true) {
	   		
	   		out.println("<script>");
	   		out.println("alert('회원을 정상적으로 추방하였습니다.');");
	   		out.println("location.href='/adminUserListAction.us';");
	   		out.println("</script>");
	   		out.close();
	   	}
	   	
	   	
   		out.println("<script>");
   		out.println("alert('회원 추방 실패');");
   		out.println("location.href='/adminUserListAction.us';");
   		out.println("</script>");
   		out.close();
   		
   		return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
