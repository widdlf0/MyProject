package msg.msg.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import msg.db.MsgBean;
import msg.db.MsgDAO;


public class msgDeleteAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
		 	ActionForward forward=new ActionForward();
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	HttpSession session=request.getSession();
			String Nick=(String)session.getAttribute("userNick");
		 	
		 	MsgDAO msgDAO=new MsgDAO();
		   	MsgBean msgBean=new MsgBean();
		   	
		   	
		   	//request.getAttribute("bbsBean");
		   	
		   	int num = Integer.parseInt(request.getParameter("num"));
		   	//String bbsNick = request.getParameter("BbsNick");
		   	
		   	boolean usercheck = msgDAO.isMsgReceiveNick(num, Nick);
		   	
		   	if(usercheck == false) {
		   		
		   		response.setContentType("text/html:charset=UTF-8");
		   		forward.setRedirect(false);
			   	forward.setPath("/isBbsWriterfalse.bo");
			   	return forward;
		   	}
		   	
		   	boolean result = false;
		   	result = msgDAO.MsgDelete(num);
		   	if(result == false) {
		   		System.out.println("게시판 삭제 실패");
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

