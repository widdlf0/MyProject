package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import user.db.UserBean;
import user.db.UserDAO;

public class userNickCheck implements Action{
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
		
		user.setUserNick(request.getParameter("userNick"));
		
		result = userDAO.userNickCheck(user.getUserNick());
		JSONObject json = new JSONObject();


	  
		if(result == true){//아이디 중복아님
			
			json.put("result3", true);
			json.put("userNickcheck", "사용가능한 닉네임 입니다!");

		}else if(result == false){
			json.put("result3", false);
			json.put("userNickcheck", "중복된 닉네임 입니다!");
			
		}
		
	  out.print(json.toString());
		
	}
	
	
	
}
