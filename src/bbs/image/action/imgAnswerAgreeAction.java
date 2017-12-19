package bbs.image.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import bbs.image.db.ImageDAO;

public class imgAnswerAgreeAction implements Action {

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

		ImageDAO imgDAO = new ImageDAO();
		
		if(agree.equals("yes")) {
			
			imgDAO.setAnswerAgree(answerNum);
		}else if(agree.equals("no")) {
			
			imgDAO.setAnswerNotAgree(answerNum);
		}
		
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		json.put("answerNum", answerNum);
		out.print(json.toString());
	}

}
