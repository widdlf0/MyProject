package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import user.db.UserBean;
import user.db.UserDAO;

public class userIdCheck2 implements Action{
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
		
		user.setUserID(request.getParameter("userID3"));
		
		result = userDAO.userIdCheck(user.getUserID());
		JSONObject json = new JSONObject();

  
	  
		if(result == true){//아이디 중복아님
			
			json.put("result", true);
			json.put("userIDcheck", "존재하지 않는 아이디입니다");

		}else if(result == false){
			json.put("result", false);
			json.put("userIDcheck", "존재하는 아이디입니다");
			
		}
		
	  out.print(json.toString());
		
	}
	
	
	
}
