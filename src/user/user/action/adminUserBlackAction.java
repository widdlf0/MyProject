package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.db.UserDAO;

public class adminUserBlackAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward=new ActionForward();
	   	response.setContentType("text/html;charset=UTF-8");
	   	
	   	UserDAO userDAO = new UserDAO();
	   	
	   	boolean result = false;
	   	String userID = request.getParameter("userIDblack");
	   	result = userDAO.setUserBlackUpdate(userID);
	   	
	   	

	   	PrintWriter out=response.getWriter();
	   	if(result == true) {
	   		
	   		out.println("<script>");
	   		out.println("alert('블랙리스트로 등록했습니다.');");
	   		out.println("location.href='/adminUserListAction.us';");
	   		out.println("</script>");
	   		out.close();
	   	}
	   	
	   	
   		out.println("<script>");
   		out.println("alert('블랙리스트 등록 실패');");
   		out.println("location.href='/adminUserListAction.us';");
   		out.println("</script>");
   		out.close();
   		
//   		forward.setRedirect(false);
//   		forward.setPath("/adminUserListAction.us");
   		return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
