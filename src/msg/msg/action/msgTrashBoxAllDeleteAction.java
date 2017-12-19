package msg.msg.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import msg.db.MsgBean;
import msg.db.MsgDAO;

public class msgTrashBoxAllDeleteAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
		 	ActionForward forward=new ActionForward();
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	HttpSession session=request.getSession();
			String Nick=(String)session.getAttribute("userNick");
		 	
		 	MsgDAO msgDAO=new MsgDAO();
		   	MsgBean msgBean=new MsgBean();
		   	
		   	int available = Integer.parseInt(request.getParameter("msgAvailable"));
		   	
		   	boolean result = false;
		   	
		   	result = msgDAO.MsgAllDelete(available, Nick);
		   	if(result == false) {
		   		System.out.println("휴지통 비우기 실패");
		   		return null;
		   	}
		   	
		   	request.setAttribute("msgBean", msgBean);
		   	
		   	forward.setRedirect(false);
		   	forward.setPath("/msgdeletealert.mg");
		   	return forward;
			
		}  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
