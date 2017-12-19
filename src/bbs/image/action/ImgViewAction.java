package bbs.image.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.image.db.ImageBean;
import bbs.image.db.ImageDAO;

public class ImgViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		request.setCharacterEncoding("UTF-8");
		
		ActionForward forward = new ActionForward();
		
		ImageDAO imgDAO = new ImageDAO();
		ImageBean imgBean = new ImageBean();
		
		String imganswerCheck = request.getParameter("imganswerCheck");
		if(imganswerCheck == null)
			imganswerCheck = "no";
		
		String Nick = request.getParameter("userNick");
		int num = Integer.parseInt(request.getParameter("num"));
		imgBean = imgDAO.getImageView(num);
		
		if(imgBean == null) {
			System.out.println("상세보기 실패");
			return null;
		}
		
		request.setAttribute("imgBean", imgBean);
		
		if(imganswerCheck.equals("no")) {
	 		System.out.println("중복 조회수 체크 " );
		 	imgDAO.setImgReadCountUpdate(num, Nick);
		 	
		 	forward.setRedirect(false);
		 	forward.setPath("/bbsImageView.img");
		 	return forward;
	 	}else {
		 	forward.setRedirect(false);
		 	forward.setPath("/ImgViewAnswerOpen.img");
		 	return forward;
	 	}
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
