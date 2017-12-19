package bbs.bbs.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.db.BbsBean;
import bbs.db.BbsDAO;

public class BbsModifyView implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
		 	ActionForward forward=new ActionForward();
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	BbsDAO bbsDAO=new BbsDAO();
		   	BbsBean bbsBean=new BbsBean();
		   	
			int num = Integer.parseInt(request.getParameter("num"));
		   	
		   
		   	bbsBean = bbsDAO.getBbsDetail(num);
		   	
		   	if(bbsBean == null) {
		   		System.out.println("(수정)상세보기 실패");
		   		return null;
		   	}
		   	
		   	System.out.println("(수정)상세보기 성공");
		   	
		   	request.setAttribute("bbsBean", bbsBean);
		   	
		   	forward.setRedirect(false);
		   	forward.setPath("/bbsmodify.bo");
		   	return forward;
			
		}  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
