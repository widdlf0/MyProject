package bbs.image.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import bbs.image.db.ImageDAO;

public class imgInsertAnsReplyAction implements Action {

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
		int imgNum = Integer.parseInt(request.getParameter("imgNum"));
		int replyNum = Integer.parseInt(request.getParameter("replynum"));
		
		ImageDAO imgDAO = new ImageDAO();
		imgDAO.imgInsertReply(imgNum, replyNum, replyNick, replyContent);
		
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		json.put("imgNumCheck", imgNum);
		out.print(json.toString());

	}

}
