package bbs.bbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.db.BbsBean;
import bbs.db.BbsDAO;

public class BbsDeleteAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
		 	ActionForward forward=new ActionForward();
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	HttpSession session=request.getSession();
			String bbsNick=(String)session.getAttribute("userNick");
		 	
		 	BbsDAO bbsDAO=new BbsDAO();
		   	BbsBean bbsBean=new BbsBean();
		   	
		   	
		   	//request.getAttribute("bbsBean");
		   	
		   	int num = Integer.parseInt(request.getParameter("num"));
		   	//String bbsNick = request.getParameter("BbsNick");
		   	
		   	boolean usercheck = bbsDAO.isBbsWriter(num, bbsNick);
		   	
		   	if(usercheck == false) {
		   		
		   		response.setContentType("text/html:charset=UTF-8");
		   		forward.setRedirect(false);
			   	forward.setPath("/isBbsWriterfalse.bo");
			   	return forward;
		   	}
		   	
		   	boolean result = false;
		   	result = bbsDAO.BbsDelete(num);
		   	if(result == false) {
		   		System.out.println("게시판 삭제 실패");
		   		return null;
		   	}
		   	
		   	request.setAttribute("bbsBean", bbsBean);
		   	
		   	forward.setRedirect(false);
		   	forward.setPath("/deletealert.bo");
		   	return forward;
			
		}  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
