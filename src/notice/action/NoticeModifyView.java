package notice.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import notice.db.NoticeBean;
import notice.db.NoticeDAO;

public class NoticeModifyView implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
		 	ActionForward forward=new ActionForward();
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	NoticeDAO noticeDAO=new NoticeDAO();
		 	NoticeBean noticeBean=new NoticeBean();
		   	
			int num = Integer.parseInt(request.getParameter("num"));
		   	
		   
			noticeBean = noticeDAO.getNoticeDetail(num);
		   	
		   	if(noticeBean == null) {
		   		System.out.println("(수정)상세보기 실패");
		   		return null;
		   	}
		   	
		   	System.out.println("(수정)상세보기 성공");
		   	
		   	request.setAttribute("noticeBean", noticeBean);
		   	
		   	forward.setRedirect(false);
		   	forward.setPath("/noticemodify.no");
		   	return forward;
			
		}  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
