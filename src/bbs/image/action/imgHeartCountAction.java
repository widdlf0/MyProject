package bbs.image.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import bbs.image.db.ImageDAO;

public class imgHeartCountAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		
		ImageDAO imgDAO = new ImageDAO();
		int imgNum = Integer.parseInt(request.getParameter("imgNum"));
		imgDAO.updateHeartCount(imgNum);
		
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		json.put("imgNum", imgNum);
		
		out.print(json.toString());

	}

}
