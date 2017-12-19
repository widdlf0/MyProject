package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.user.action.ActionForward;

public class userLogoutAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
				 	ActionForward forward = new ActionForward();
				 	
				 	HttpSession session=request.getSession();
			   		
			   		session.invalidate(); //모든 세션정보 삭제
			   		
			   		forward.setRedirect(true);
			   		forward.setPath("/main.us");	// 로그인 화면으로 다시 돌아간다.
			   		return forward;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}