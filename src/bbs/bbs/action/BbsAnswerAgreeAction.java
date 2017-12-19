package bbs.bbs.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import bbs.db.BbsDAO;

public class BbsAnswerAgreeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String agree = request.getParameter("agree");
		int answerNum = Integer.parseInt(request.getParameter("answernum"));
		
		BbsDAO bbsDAO = new BbsDAO();
		
		if(agree.equals("yes")) {
			
			bbsDAO.setAnswerAgree(answerNum);
			
		}else if(agree.equals("no")){
			
			bbsDAO.setAnswerNotAgree(answerNum);
			
		}

		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		json.put("answerNum", answerNum);
		out.print(json.toString());
		
	}

}
