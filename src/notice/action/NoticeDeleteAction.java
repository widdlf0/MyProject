package notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import notice.db.NoticeBean;
import notice.db.NoticeDAO;

public class NoticeDeleteAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
		 	ActionForward forward=new ActionForward();
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	HttpSession session=request.getSession();
			String NoticeNick=(String)session.getAttribute("userNick");
		 	
			NoticeDAO noticeDAO=new NoticeDAO();
		 	NoticeBean noticeBean=new NoticeBean();
		   	
		   	
		   	//request.getAttribute("bbsBean");
		   	
		   	int num = Integer.parseInt(request.getParameter("num"));
		   	//String bbsNick = request.getParameter("BbsNick");
		   	
		   	boolean usercheck = noticeDAO.isNoticeWriter(num, NoticeNick);
		   	
		   	if(usercheck == false) {
		   		
		   		response.setContentType("text/html:charset=UTF-8");
		   		forward.setRedirect(false);
			   	forward.setPath("/isBbsWriterfalse.no");
			   	return forward;
		   	}
		   	
		   	boolean result = false;
		   	result = noticeDAO.NoticeDelete(num);
		   	if(result == false) {
		   		System.out.println("게시판 삭제 실패");
		   		return null;
		   	}
		   	
		   	request.setAttribute("noticeBean", noticeBean);
		   	
		   	forward.setRedirect(false);
		   	forward.setPath("/noticedeletealert.no");
		   	return forward;
			
		}  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
