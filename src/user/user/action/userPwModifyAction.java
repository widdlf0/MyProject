package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.db.UserBean;
import user.db.UserDAO;

public class userPwModifyAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
			 	ActionForward forward=new ActionForward();
			 	request.setCharacterEncoding("UTF-8");
			 	
			 	
			 	UserDAO userDAO=new UserDAO();
			   	UserBean userBean=new UserBean();
			   	
			   	boolean result = false;
			   	int result1 = -1;
			   	
			   	response.setContentType("text/html;charset=UTF-8");
			   	userBean.setUserPassword(request.getParameter("nowPassword"));
			   	userBean.setUserEmail2(request.getParameter("userPassword"));
			   	userBean.setUserID(request.getParameter("userID"));
		   	
			   	result1 = userDAO.isUser(userBean);
			   	
			   	if(result1 == 0){
		   			response.setContentType("text/html;charset=UTF-8");
			   		PrintWriter out=response.getWriter();
			   		out.println("<script>");
			   		out.println("alert('현재 비밀번호가 일치하지 않습니다.');");
			   		out.println("history.back();");
			   		out.println("</script>");
			   		out.close();
			   		return null;
		   		}
	   			result = userDAO.updateUserPw(userBean);
	   			
		   		if(result == false) {
		   			System.out.println("비밀 번호 수정 실패");
		   			return null;
		   		}
		   		
		   		System.out.println("비밀 번호 수정 완료");
		   		
		   		response.setContentType("text/html;charset=UTF-8");
		   		PrintWriter out=response.getWriter();
		   		out.println("<script>");
		   		out.println("alert('비밀번호가 정상적으로 수정되었습니다.');");
		   		out.println("location.href='/usermyhome.us';");
		   		out.println("</script>");
		   		out.close();
		   		
		   		forward.setRedirect(true);
		   		forward.setPath("/usermyhome.us");
		   		return forward;
		}  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
