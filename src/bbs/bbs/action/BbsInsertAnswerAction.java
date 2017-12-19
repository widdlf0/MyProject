package bbs.bbs.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import bbs.db.BbsDAO;

public class BbsInsertAnswerAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String AnswerContent = request.getParameter("bbsAnswerContent");
		String AnswerNick = request.getParameter("bbsAnswerNick");
		int bbsNum = Integer.parseInt(request.getParameter("bbsNumCheck"));
		
		BbsDAO bbsDAO = new BbsDAO();
		bbsDAO.BbsInsertAnswer(bbsNum, AnswerNick, AnswerContent);
		
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		json.put("bbsNumCheck", bbsNum);
		out.print(json.toString());
	}

}
