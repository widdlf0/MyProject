package bbs.image.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.image.db.ImageBean;
import bbs.image.db.ImageDAO;

public class ImgModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		
		ImageDAO imgDAO = new ImageDAO();
		ImageBean imgBean = new ImageBean();
		
		int num = Integer.parseInt(request.getParameter("imgNum"));
		String Nick = request.getParameter("imgNick");
		
		imgBean.setImgNum(num);
		imgBean.setImgNick(Nick);
		imgBean.setImgSubject(request.getParameter("imgSubject"));
		imgBean.setImgContent(request.getParameter("imageContent"));
		
		boolean result = false;
		
		result = imgDAO.ImgModify(imgBean);
		
		if(result == false) {
			System.out.println("이미지 게시판 수정 실패");
			return null;
		}
		
//		System.out.println("유저닉 : " + Nick);
		forward.setRedirect(true);
		forward.setPath("/ImgViewAction.img?num="+num+"&userNick="+Nick);
		return forward;
		
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
