package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import user.db.UserBean;
import user.db.UserDAO;

public class userNameCheck implements Action{
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
		
		boolean result = false;
		
		user.setUserName(request.getParameter("userName2"));
		
		result = userDAO.userNameCheck(user.getUserName());
		JSONObject json = new JSONObject();


	  
		if(result == true){//아이디 중복아님
			
			json.put("result4", true);
			json.put("userNamecheck2", "등록되지 않은 이름입니다!");

		}else if(result == false){
			json.put("result4", false);
			json.put("userNamecheck2", "등록되어 있는 이름입니다");
			
		}
		
	  out.print(json.toString());
		
	}
	
	
	
}
