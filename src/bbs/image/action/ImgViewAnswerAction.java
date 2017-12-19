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

public class ImgViewAnswerAction implements Action {

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
		ImageBean imgBean = new ImageBean();
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		int imgNumCheck = Integer.parseInt(request.getParameter("imgNumCheck"));
		
		int answerCount = imgDAO.getImageAnswerCount(imgNumCheck);
		
		List imgAnswerlist = new ArrayList();
		imgAnswerlist = imgDAO.getImageAnswerDetail(imgNumCheck);
		
		JSONArray imgAnsArray = new JSONArray();
		
		for(int i = 0; i<imgAnswerlist.size(); i++) {
			
			imgBean = (ImageBean)imgAnswerlist.get(i);
			
			JSONObject obj = new JSONObject();
			
			obj.put("answerNum", imgBean.getImgAnswerNum());
			obj.put("imganswerNum", imgBean.getImgNumCheck());
			obj.put("answerNick", imgBean.getImgNickCheck());
			obj.put("answerContent", imgBean.getImgAnswerContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			obj.put("answerDate", imgBean.getImgAnswerDate());
			obj.put("answerAgree", imgBean.getImgAnswerAgree());
			obj.put("answerNotAgree", imgBean.getImgAnswerNotAgree());
			obj.put("replyNum", imgBean.getImgReplyNum());
			
			imgAnsArray.add(obj);
		}
		
		//베스트 댓글
		List agreelist = new ArrayList();
		agreelist = imgDAO.getImageAnswerDetailAgree(imgNumCheck);
		
		JSONArray agreeArray = new JSONArray();
		
		for(int i = 0; i<agreelist.size(); i++) {
			
			imgBean = (ImageBean)agreelist.get(i);
			
			JSONObject obj = new JSONObject();
			
			obj.put("answerNum", imgBean.getImgAnswerNum());
			obj.put("imganswerNum", imgBean.getImgNumCheck());
			obj.put("answerNick", imgBean.getImgNickCheck());
			obj.put("answerContent", imgBean.getImgAnswerContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			obj.put("answerDate", imgBean.getImgAnswerDate());
			obj.put("answerAgree", imgBean.getImgAnswerAgree());
			obj.put("answerNotAgree", imgBean.getImgAnswerNotAgree());
			obj.put("replyNum", imgBean.getImgReplyNum());
			
			agreeArray.add(obj);
		}
		
		json.put("answercount", answerCount);
		json.put("agreeans", agreeArray);
		json.put("imgans", imgAnsArray);
		
		out.print(json.toString());

	}

}
