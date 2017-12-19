package bbs.bbs.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import bbs.db.BbsDAO;

public class BbsAnswerDeleteAction implements Action {

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
		
		int answerNum = Integer.parseInt(request.getParameter("answernum"));
		boolean result = false;
		
		result = bbsDAO.AnswerDelete(answerNum);
		
//		System.out.println("댓글 삭제 성공? : "+result);
		
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		out.print(json.toString());
		
	}

}
