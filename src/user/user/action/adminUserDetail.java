package user.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.db.UserBean;
import user.db.UserDAO;

public class adminUserDetail implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		
		UserDAO userDAO = new UserDAO();
		UserBean userBean = new UserBean();
		
		String userID = request.getParameter("userIDdetail");
		
		userBean = userDAO.adminUserDetail(userID);
		
		request.setAttribute("userBean", userBean);
		
		forward.setRedirect(false);
		forward.setPath("/adminUserDetailView.us");
		return forward;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
