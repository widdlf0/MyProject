package msg.msg.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import msg.db.MsgDAO;

public class msgSendListAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	ActionForward forward= new ActionForward();
	 		HttpSession session=request.getSession();
	 		
	    		
	 		MsgDAO msgDAO = new MsgDAO();
	 		List msglist=new ArrayList();
	 		
	 	  	int page=1;
	 		int limit=10;
	 		
	 		if(request.getParameter("page")!=null){
	 			page=Integer.parseInt(request.getParameter("page"));
	 		}
	 		
	 		// 검색조건과 검색내용을 가져온다.
	        String opt = request.getParameter("opt");
	        String condition = request.getParameter("condition");
	        String Nick = (String) session.getAttribute("userNick");
	        System.out.println("리스트 닉네임: " + Nick);
	        
	        // 검색조건과 내용을 Map에 담는다.
	        HashMap<String, Object> listOpt = new HashMap<String, Object>();
	        listOpt.put("opt", opt);
	        listOpt.put("condition", condition);
	        listOpt.put("start", page*10-9);
	        listOpt.put("userNick", Nick);
	 		
	 		int listcount=msgDAO.getMsgSendListCount(listOpt); //총 리스트 수를 받아옴
	 		msglist = msgDAO.getMsgSendList(listOpt); //리스트를 받아옴
	 		
	 		//총 페이지 수
	    		int maxpage=(int)((double)listcount/limit+0.95); // 0.95를 더해서 올림 처리
	    		//현재 페이지에 보여줄 시작 페이지 수(1, 11, 21등...)
	    		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
	    		//현재 페이지에 보여줄 마지막 페이지 수 (10,20,30,등...)
	    		int endpage = maxpage;
	    		
	    		if (endpage>startpage+10-1) endpage=startpage+10-1;
	    		
	    		request.setAttribute("msglist", msglist);
	    		request.setAttribute("page", page);  //현재 페이지 수
	    		request.setAttribute("maxpage", maxpage);  //최대 페이지 수
	    		request.setAttribute("startpage", startpage);  //현재 페이지에 표시할 첫 페이지 수
	    		request.setAttribute("endpage", endpage); //현재 페이지에 표시할 끝 페이지 수
	    		request.setAttribute("listcount",listcount);  //글 수
	    		
	 		
	    		forward.setRedirect(false);
	    		forward.setPath("/messagesend.mg");
	    		return forward;
	 	 }  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
