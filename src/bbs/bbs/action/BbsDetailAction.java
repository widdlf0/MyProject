package bbs.bbs.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.db.BbsBean;
import bbs.db.BbsDAO;

public class BbsDetailAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	ActionForward forward= new ActionForward();
	 		
		 	BbsDAO bbsDAO = new BbsDAO();
		 	BbsBean bbsBean = new BbsBean();
		 	
		 	String answerCheck = request.getParameter("answerCheck");
		 	if(answerCheck == null)
		 		answerCheck = "no";
		 	
		 	int num = Integer.parseInt(request.getParameter("num"));
		 	bbsBean = bbsDAO.getBbsDetail(num);
		 	String Nick = request.getParameter("userNick");
		 	
		 	if(bbsBean == null) {
		 		System.out.println("상세보기 실패");
		 		return null;
		 	}
		 	
		 	System.out.println("상세보기 성공");
		 	
		 	request.setAttribute("bbsBean", bbsBean);
		 	
		 	if(answerCheck.equals("no")) {
		 		System.out.println("중복 조회수 체크 " );
			 	bbsDAO.setReadCountUpdate(num, Nick);
			 	
			 	forward.setRedirect(false);
			 	forward.setPath("/bbsview.bo");
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
