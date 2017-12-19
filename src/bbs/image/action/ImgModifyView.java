package bbs.image.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.image.db.ImageBean;
import bbs.image.db.ImageDAO;

public class ImgModifyView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		
		ImageDAO imgDAO = new ImageDAO();
		ImageBean imgBean = new ImageBean();
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		imgBean = imgDAO.getImageView(num);
		
		if(imgBean == null) {
			System.out.println("(수정)상세보기 실패");
			return null;
		}
		
		request.setAttribute("imgBean", imgBean);
		
		forward.setRedirect(false);
	   	forward.setPath("/ImageModify.img");
	   	return forward;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
