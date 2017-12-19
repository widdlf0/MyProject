package bbs.image.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.image.db.ImageBean;
import bbs.image.db.ImageDAO;

public class ImgDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		
		String Nick = request.getParameter("userNick");
		int num = Integer.parseInt(request.getParameter("num"));
		
		ImageDAO imgDAO = new ImageDAO();
		ImageBean imgBean = new ImageBean();
		
		boolean usercheck = imgDAO.isImgWriter(num, Nick);
		
		if(usercheck == false) {
	   		
	   		response.setContentType("text/html:charset=UTF-8");
	   		forward.setRedirect(false);
		   	forward.setPath("/isBbsWriterfalse.bo");
		   	return forward;
	   	}
		
		boolean result = false;
		
		result = imgDAO.ImgDelete(num);
		if(result == false) {
			System.out.println("이미지 게시판 삭제 실패");
			return null;
		}

		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('정상적으로 삭제 되었습니다^^!');");
		out.println("location.href = '/ImgListAction.img';");
		out.println("</script>");
		
		return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
