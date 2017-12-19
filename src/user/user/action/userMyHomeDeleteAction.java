package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.db.UserBean;
import user.db.UserDAO;

public class userMyHomeDeleteAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
			 	ActionForward forward=new ActionForward();
			 	request.setCharacterEncoding("UTF-8");
			 	
			 	
			 	UserDAO userDAO=new UserDAO();
			   	UserBean userBean=new UserBean();
			   	
			   	boolean result = false;
			   	int result1 = -1;
			   	
			   	response.setContentType("text/html;charset=UTF-8");
			   	userBean.setUserPassword(request.getParameter("userPassword"));
			   	userBean.setUserNick(request.getParameter("userNick"));
			   	
			   	System.out.println("회원 탈퇴 확인 : " +request.getParameter("userPassword"));
			   	System.out.println("회원 탈퇴 닉네임 확인 : " +request.getParameter("userNick"));
			   	
			   	result1 = userDAO.isNick(userBean);
			   	System.out.println("리턴 값 확인 : " + result1);
			   	if(result1 == 0){
		   			response.setContentType("text/html;charset=UTF-8");
			   		PrintWriter out=response.getWriter();
			   		out.println("<script>");
			   		out.println("alert('비밀번호가 일치하지 않습니다.');");
			   		out.println("history.back();");
			   		out.println("</script>");
			   		out.close();
			   		return null;
		   		}else if(result1 == 1) {
		   			String Nick = request.getParameter("userNick");
		   			result = userDAO.userDelete(Nick);
		   			
			   		if(result == false) {
			   			System.out.println("회원 탈퇴 실패");
			   			return null;
			   		}
			   		
			   		System.out.println("회원 탈퇴 완료");
			   		
			   		response.setContentType("text/html;charset=UTF-8");
			   		PrintWriter out=response.getWriter();
			   		out.println("<script>");
			   		out.println("alert('정상적으로 탈퇴 되었습니다^!');");
			   		out.println("location.href='/logoutAction.us';");
			   		out.println("</script>");
			   		out.close();
		   		}
			   	
		   		forward.setRedirect(false);
		   		forward.setPath("/logoutAction.us");
		   		return forward;
		}  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
