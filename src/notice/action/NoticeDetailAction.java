package notice.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import notice.db.NoticeBean;
import notice.db.NoticeDAO;

public class NoticeDetailAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	ActionForward forward= new ActionForward();
	 		
		 	NoticeDAO noticeDAO=new NoticeDAO();
		 	NoticeBean noticeBean=new NoticeBean();
		 	
		 	String answerCheck = request.getParameter("answerCheck");
		 	if(answerCheck == null)
		 		answerCheck = "no";
		 	
		 	int num = Integer.parseInt(request.getParameter("num"));
		 	noticeBean = noticeDAO.getNoticeDetail(num);
		 	String Nick = request.getParameter("userNick");
		 	
		 	if(noticeBean == null) {
		 		System.out.println("상세보기 실패");
		 		return null;
		 	}
		 	
		 	System.out.println("상세보기 성공");
		 	
		 	request.setAttribute("noticeBean", noticeBean);
		 	
		 	if(answerCheck.equals("no")) {
		 		System.out.println("중복 조회수 체크 " );
		 		noticeDAO.setReadCountUpdate(num, Nick);
			 	
			 	forward.setRedirect(false);
			 	forward.setPath("/noticeview.no");
			 	return forward;
		 	}else {
			 	forward.setRedirect(false);
			 	forward.setPath("/bbsanswerview.bo");
			 	return forward;
		 	}
	 	 }  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
