package msg.msg.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import msg.db.MsgBean;
import msg.db.MsgDAO;

public class msgSendTrashBoxUpdate implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	ActionForward forward= new ActionForward();
	 		
		 	MsgDAO msgDAO = new MsgDAO();
		 	MsgBean msgBean = new MsgBean();
		 	
		 	int num = Integer.parseInt(request.getParameter("num"));
		 	int locat = Integer.parseInt(request.getParameter("locat"));
		 	msgDAO.setMsgSendTrashBoxUpdate(num);
		 	
		 	System.out.println("휴지통 보내기 성공");
		 	
		 	request.setAttribute("msgBean", msgBean);
		 	
		 	if(locat == 1) {
		 		forward.setRedirect(false);
			 	forward.setPath("/msgReadListAction.mg");
			 	return forward;
		 	}
		 	
		 	forward.setRedirect(false);
		 	forward.setPath("/msgNewListAction.mg");
		 	return forward;
	 	 }  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
