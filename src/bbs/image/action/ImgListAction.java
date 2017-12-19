package bbs.image.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.image.action.ActionForward;
import bbs.image.db.ImageDAO;

public class ImgListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


		request.setCharacterEncoding("UTF-8");
		
		ActionForward forward = new ActionForward();
		ImageDAO imgDAO = new ImageDAO();
		List imglist = new ArrayList();
		
		int page = 1;
		int limit = 10;
		
		if(request.getParameter("page") != null)
			page = Integer.parseInt(request.getParameter("page"));
		
		
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");
		
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		listOpt.put("start", page*10-9);
		
		int listcount = imgDAO.getImgListCount(listOpt);
		imglist = imgDAO.getImageList(listOpt);
		
		//총 페이지 수
		int maxpage=(int)((double)listcount/limit+0.95); // 0.95를 더해서 올림 처리
		//현재 페이지에 보여줄 시작 페이지 수(1, 11, 21등...)
		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
		//현재 페이지에 보여줄 마지막 페이지 수 (10,20,30,등...)
		int endpage = maxpage;
		
		if (endpage>startpage+10-1) 
			endpage=startpage+10-1;
		
		request.setAttribute("opt", opt);
		request.setAttribute("condition", condition);
		request.setAttribute("imglist", imglist);
		request.setAttribute("page", page);  //현재 페이지 수
		request.setAttribute("maxpage", maxpage);  //최대 페이지 수
		request.setAttribute("startpage", startpage);  //현재 페이지에 표시할 첫 페이지 수
		request.setAttribute("endpage", endpage); //현재 페이지에 표시할 끝 페이지 수
		request.setAttribute("listcount",listcount);  //글 수
		
//		System.out.println("imglist" + imglist);
//		System.out.println("page" + page);
//		System.out.println("maxpage" + maxpage);
//		System.out.println("startpage" + startpage);
//		System.out.println("endpage" + endpage);
//		System.out.println("listcount" + listcount);
		
		forward.setRedirect(false);
		forward.setPath("/Imagebbs.img");
		return forward;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
