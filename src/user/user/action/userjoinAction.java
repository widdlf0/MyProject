package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.user.action.ActionForward;
import user.db.UserBean;
import user.db.UserDAO;

public class userjoinAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	request.setCharacterEncoding("UTF-8");
		 	System.out.println("===2===");
		 	ActionForward forward = new ActionForward();
		 	
			UserDAO userDAO=new UserDAO();
	   		UserBean user=new UserBean();
	   		System.out.println("===2.1===");
	   		int result=-1;
	   		System.out.println("===2.2===");
	   		user.setUserID(request.getParameter("userID"));
	   		user.setUserPassword(request.getParameter("userPassword"));
	   		user.setUserName(request.getParameter("userName"));
	   		user.setUserNick(request.getParameter("userNick"));
	   		user.setUserPhone1(request.getParameter("userPhone1"));
	   		user.setUserPhone2(request.getParameter("userPhone2"));
	   		user.setUserPhone3(request.getParameter("userPhone3"));
	   		user.setUserGender(request.getParameter("userGender"));
	   		user.setUserDate1(request.getParameter("userDate1"));
	   		user.setUserDate2(request.getParameter("userDate2"));
	   		user.setUserDate3(request.getParameter("userDate3"));
	   		user.setUserEmail1(request.getParameter("userEmail1"));
	   		user.setUserEmail2(request.getParameter("userEmail2"));
	   		user.setUserAddress1(request.getParameter("userAddress1"));
	   		user.setUserAddress2(request.getParameter("userAddress2"));
	   		user.setUserAddress3(request.getParameter("userAddress3"));
	   		System.out.println("===2.3===");
	   		result=userDAO.userjoin(user);
	   		
	   		if(result==-1){
	   			System.out.println("===2.4===");
	   			System.out.println("회원가입 실패");
		   		return null;
		   	}
	   		
	   		response.setContentType("text/html;charset=UTF-8");
	   		System.out.println("===2.5===");
	   		PrintWriter out=response.getWriter();
	   		out.println("<script>");
	   		out.println("alert('축하합니다 회원가입 되셨습니다!!');");
	   		out.println("location.href='/main.us';");
	   		out.println("</script>");
	   		out.close();
	   		
	   		//회원가입 성공
	   		forward.setRedirect(true);
	   		forward.setPath("./main.us");
	   		return forward;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
