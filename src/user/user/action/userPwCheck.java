package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import user.db.UserBean;
import user.db.UserDAO;

public class userPwCheck implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		return null;		 	
	 }

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		UserDAO userDAO=new UserDAO();
		UserBean user=new UserBean();
		
		String userPassword = request.getParameter("userPassword");
		String userPasswordcheck = request.getParameter("userPasswordcheck");
		System.out.println("userPassword: " + userPassword);
		System.out.println("userPasswordcheck: " + userPasswordcheck);
		JSONObject json = new JSONObject();

		if(userPassword.equals(userPasswordcheck)){//비밀번호가 같음
			
			json.put("result2", "success");
			json.put("userPasswordcheck2", "비밀번호가 일치합니다!");

		}else if(!(userPassword.equals(userPasswordcheck))){
			json.put("result2", "fail");
			json.put("userPasswordcheck2", "비밀번호가 일치하지 않습니다!");
			
		}
		
	  out.print(json.toString());
		
	}
	
	
	
}
