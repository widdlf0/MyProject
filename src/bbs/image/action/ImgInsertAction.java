package bbs.image.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.image.db.ImageBean;
import bbs.image.db.ImageDAO;

public class ImgInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		ImageBean imgBean = new ImageBean();
		ImageDAO imgDAO = new ImageDAO();
		ActionForward forward = new ActionForward();
		
		int result = 0;
		
		imgBean.setImgNick(request.getParameter("imgNick"));
		imgBean.setImgSubject(request.getParameter("imgSubject"));
		imgBean.setImgContent(request.getParameter("imageContent"));
		
		result = imgDAO.imgInsert(imgBean);
		
		imgDAO.setImageName(result);
		
		System.out.println("이미지 게시판 등록 완료");
		
		forward.setRedirect(true);
		forward.setPath("/ImgListAction.img");
		
		return forward;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
