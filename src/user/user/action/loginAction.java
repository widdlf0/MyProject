package user.user.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import user.db.UserBean;
import user.db.UserDAO;

public class loginAction implements Action{
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
   		
   		int result=-1;
   		
   		user.setUserID(request.getParameter("userID"));
   		user.setUserPassword(request.getParameter("userPassword"));
   		
   		result = userDAO.userlogin(user.getUserID(), user.getUserPassword());
   		JSONObject json = new JSONObject();

     
	  
   		if(result == 1){//로그인 성공
   			UserBean us = userDAO.userNick(user);
			session.setAttribute("userID", user.getUserID());
			session.setAttribute("userNick", us.getUserNick());
			
			json.put("result", "success");

		}else if(result == 2) {
			
			json.put("result", "black");
			json.put("reason", "블랙리스트로 등록된 사용자 입니다^^");
			
		}else {
			json.put("result", "fail");
			json.put("reason", "아이디 및 패스워드를 확인해주세요!");
//			List<HashMap<String, String>> rsList = new ArrayList<> ();
//			HashMap<String, String> map1 = new HashMap<>();
//			map1.put("action", "haha1");
//			HashMap<String, String> map2 = new HashMap<>();
//			map2.put("action", "haha2");
//			HashMap<String, String> map3 = new HashMap<>();
//			map3.put("action", "haha3");
//			
//			rsList.add(map1);
//			rsList.add(map2);
//			rsList.add(map3);
//			
//			JSONArray array = new JSONArray();
//			JSONObject jo;
//			for(int i=0;i<rsList.size();i++) {
//				HashMap<String, String> map = rsList.get(i);
//				jo = new JSONObject();
//				jo.put("action", map.get("action"));
//				array.add(jo);
//			}
//			json.put("array", array);
			
			
			System.out.println("dddddddd");
		}
   		
   	  out.print(json.toString());
		
	}
	
	
	
}
