package bbs.bbs.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import bbs.db.BbsDAO;

public class BbsInsertAnsReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String replyContent = request.getParameter("replyContent");
		String replyNick = request.getParameter("replyNick");
		int bbsNum = Integer.parseInt(request.getParameter("bbsNum"));
		int replyNum = Integer.parseInt(request.getParameter("replynum"));
		
		BbsDAO bbsDAO = new BbsDAO();
		bbsDAO.BbsInsertReply(bbsNum, replyNum, replyNick, replyContent);
		
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		json.put("bbsNumCheck", bbsNum);
		out.print(json.toString());
	}

}
