package msg.msg.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import msg.db.MsgBean;
import msg.db.MsgDAO;

public class msgLiveAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	ActionForward forward= new ActionForward();
	 		
		 	MsgDAO msgDAO = new MsgDAO();
		 	MsgBean msgBean = new MsgBean();
		 	
		 	int num = Integer.parseInt(request.getParameter("num"));
		 	msgDAO.setMsgLive(num);
		 	
		 	System.out.println("메세지 살리기 성공");
		 	
		 	request.setAttribute("msgBean", msgBean);
		 	
		 	forward.setRedirect(false);
		 	forward.setPath("/msgTrashBoxListAction.mg");
		 	return forward;
	 	 }  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
