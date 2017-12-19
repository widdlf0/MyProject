package user.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.db.UserBean;
import user.db.UserDAO;


public class userModifyAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
			 	ActionForward forward=new ActionForward();
			 	request.setCharacterEncoding("UTF-8");
			 	
			 	
			 	UserDAO userDAO=new UserDAO();
			   	UserBean userBean=new UserBean();
			   	
			   	boolean result = false;
			   	
			   	response.setContentType("text/html;charset=UTF-8");
			   	userBean.setUserPhone1(request.getParameter("userPhone1"));
			   	userBean.setUserPhone2(request.getParameter("userPhone2"));
			   	userBean.setUserPhone3(request.getParameter("userPhone3"));
			   	userBean.setUserEmail1(request.getParameter("userEmail1"));
			   	userBean.setUserEmail2(request.getParameter("userEmail2"));
			   	userBean.setUserAddress1(request.getParameter("userAddress1"));
			   	userBean.setUserAddress2(request.getParameter("userAddress2"));
			   	userBean.setUserAddress3(request.getParameter("userAddress3"));
			   	userBean.setUserID(request.getParameter("userID"));
		   	
	   			result = userDAO.updateUser(userBean);
	   			
		   		if(result == false) {
		   			System.out.println("내 정보 수정 실패");
		   			return null;
		   		}
		   		
		   		System.out.println("내 정보 수정 완료");
		   		System.out.println("리턴값 확인 : 	" + result);
		   		
		   		forward.setRedirect(true);
		   		forward.setPath("/usermyhome.us");
		   		return forward;
		}  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}

