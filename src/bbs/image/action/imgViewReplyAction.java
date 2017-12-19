package bbs.image.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bbs.image.db.ImageBean;
import bbs.image.db.ImageDAO;

public class imgViewReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		
		ImageBean imgBean = new ImageBean();
		ImageDAO imgDAO = new ImageDAO();
		
		int imgNumCheck = Integer.parseInt(request.getParameter("imgNumCheck"));
		int answerNum = Integer.parseInt(request.getParameter("answerNum"));
		
		List replylist = new ArrayList();
		replylist = imgDAO.getReplyDetail(imgNumCheck, answerNum);

		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		JSONArray replyarray = new JSONArray();
		
		for(int i = 0; i<replylist.size(); i++) {
			imgBean = (ImageBean)replylist.get(i);
			
			JSONObject obj = new JSONObject();
			
			obj.put("replyNum", imgBean.getImgAnswerNum());
			obj.put("replyNickcheck", imgBean.getImgNickCheck());
			obj.put("replyContent", imgBean.getImgAnswerContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			obj.put("replyDate", imgBean.getImgDate());
			obj.put("replyAgree", imgBean.getImgAnswerAgree());
			obj.put("replyNotAgree", imgBean.getImgAnswerNotAgree());
			obj.put("reply", imgBean.getImgReplyNum());
//			System.out.println("포문안에 번호" +bbsBean.getReplyNum());
			
			replyarray.add(obj);
		}
		
		json.put("replyans", replyarray);
		
		out.print(json.toString());
	}

}
