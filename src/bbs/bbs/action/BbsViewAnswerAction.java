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

public class BbsViewAnswerAction implements Action {

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
		
		int answerCount = bbsDAO.getAnswerCount(bbsNumCheck);
		
		
		List bbsAnswerlist = new ArrayList();
		bbsAnswerlist = bbsDAO.getBbsAnswerDetail(bbsNumCheck);
		
		JSONArray bbsansarray = new JSONArray();
		
		for(int i = 0; i<bbsAnswerlist.size(); i++) {
			bbsBean = (BbsBean)bbsAnswerlist.get(i);
			
			JSONObject obj = new JSONObject();
			
			obj.put("answerNum", bbsBean.getBbsAnswerNum());
			obj.put("answerNick", bbsBean.getBbsNickCheck());
			obj.put("answerContent", bbsBean.getBbsAnswerContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			obj.put("answerDate", bbsBean.getAnswerDate());
			obj.put("answerAgree", bbsBean.getBbsAnswerAgree());
			obj.put("answerNotAgree", bbsBean.getBbsAnswerNotAgree());
			obj.put("reply", bbsBean.getReplyNum());
			
			bbsansarray.add(obj);
		}
		
		//댓글 공감 순서
		

		List bbsAnswerAgree = new ArrayList();
		bbsAnswerAgree = bbsDAO.getBbsAnswerDetailAgree(bbsNumCheck);
		
		JSONArray bbsansarrayAgree = new JSONArray();
		
		for(int i = 0; i<bbsAnswerAgree.size(); i++) {
			BbsBean bbsBean1 = (BbsBean)bbsAnswerAgree.get(i);
			
			JSONObject obj1 = new JSONObject();
			
			obj1.put("answerNum1", bbsBean1.getBbsAnswerNum());
			obj1.put("answerNick1", bbsBean1.getBbsNickCheck());
			obj1.put("answerContent1", bbsBean1.getBbsAnswerContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			obj1.put("answerDate1", bbsBean1.getAnswerDate());
			obj1.put("answerAgree1", bbsBean1.getBbsAnswerAgree());
			obj1.put("answerNotAgree1", bbsBean1.getBbsAnswerNotAgree());
			obj1.put("reply1", bbsBean1.getReplyNum());
			
			bbsansarrayAgree.add(obj1);
		}
//		System.out.println("일반 댓글 :" + bbsansarray);
//		System.out.println("공감 댓글 :" + bbsansarrayAgree);
		json.put("answercount", answerCount);
		json.put("answeragree", bbsansarrayAgree);
		json.put("bbsans", bbsansarray);
		
		out.print(json.toString());
	}

}
