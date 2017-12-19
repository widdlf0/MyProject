package bbs.bbs.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bbs.db.BbsBean;
import bbs.db.BbsDAO;

public class BbsViewReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		
		BbsDAO bbsDAO = new BbsDAO();
		BbsBean bbsBean = new BbsBean();
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		int bbsNumCheck = Integer.parseInt(request.getParameter("bbsNumCheck"));
		int answerNum = Integer.parseInt(request.getParameter("answerNum"));
//		System.out.println("게시판 번호 : "+answerNum);
		int replycount = bbsDAO.getReplyCount(bbsNumCheck, answerNum);
		
		List replylist = new ArrayList();
		replylist = bbsDAO.getReplyDetail(bbsNumCheck, answerNum);

		JSONArray replyarray = new JSONArray();
		
		for(int i = 0; i<replylist.size(); i++) {
			bbsBean = (BbsBean)replylist.get(i);
			
			JSONObject obj = new JSONObject();
			
			obj.put("replyNum", bbsBean.getBbsAnswerNum());
			obj.put("replyNickcheck", bbsBean.getBbsNickCheck());
			obj.put("replyContent", bbsBean.getBbsAnswerContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			obj.put("replyDate", bbsBean.getAnswerDate());
			obj.put("replyAgree", bbsBean.getBbsAnswerAgree());
			obj.put("replyNotAgree", bbsBean.getBbsAnswerNotAgree());
			obj.put("reply", bbsBean.getReplyNum());
//			System.out.println("포문안에 번호" +bbsBean.getReplyNum());
			
			replyarray.add(obj);
		}
		
		json.put("replycount", replycount);
		json.put("replyans", replyarray);
		
		out.print(json.toString());
	}

}
