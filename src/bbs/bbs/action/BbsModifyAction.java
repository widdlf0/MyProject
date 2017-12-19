package bbs.bbs.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bbs.db.BbsBean;
import bbs.db.BbsDAO;

public class BbsModifyAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
		 	ActionForward forward=new ActionForward();
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	
		 	
		 	BbsDAO bbsDAO=new BbsDAO();
		   	BbsBean bbsBean=new BbsBean();
		   	
		   	request.setAttribute("bbsBean", bbsBean);
//		   	String BbsNum = request.getParameter("BbsNum");
//		   	System.out.println("BbsNum : "+BbsNum);
		   	//System.out.println("넘버값.:"+request.getParameter("BbsNum"));
		   	//int num = Integer.parseInt(request.getParameter("BbsNum")); //여기 에러네
		   	//System.out.println("BbsNum : " + num);
		   	//String BbsNick = request.getParameter("BbsNick");
		   	
		   	
		   	String realFolder="";
	   		String saveFolder="bbsupload";
	   		
	   		int fileSize=5*1024*1024;
	   		
	   		realFolder=request.getRealPath(saveFolder);
	   		
	   		boolean result=false;
	   		
	   		try{
	   			MultipartRequest multi=null;
	   			
	   			multi=new MultipartRequest(request,
	   					realFolder,
	   					fileSize,
	   					"UTF-8",
	   					new DefaultFileRenamePolicy());
	   			
	   			String ffff = multi.getParameter("BbsNum");
//	   			System.out.println("fffff: "+ffff);
	   			int num = Integer.parseInt(multi.getParameter("BbsNum"));
	   			String BbsNick = multi.getParameter("BbsNick");

	   			System.out.println("수정하는 자의 닉네임 :" + BbsNick);
	   			boolean usercheck = bbsDAO.isBbsWriter(num, BbsNick);
			   	
			   	if(usercheck == false) {
			   		
			   		response.setContentType("text/html:charset=UTF-8");
			   		forward.setRedirect(false);
				   	forward.setPath("/isBbsWriterfalse.bo");
				   	return forward;
			   	}
	   			
	   			bbsBean.setBbsNum(num);
	   			bbsBean.setBbsNick(BbsNick);
	   			bbsBean.setBbsSubject(multi.getParameter("BbsSubject"));
	   			bbsBean.setBbsContent(multi.getParameter("BbsContent"));
	   			bbsBean.setBbsFile(multi.getFilesystemName((String)multi.getFileNames().nextElement()));
		   	
		   	result = bbsDAO.BbsModify(bbsBean);
		   		if(result == false) {
		   			System.out.println("게시판 수정 실패");
		   			return null;
		   		}
		   		System.out.println("게시판 수정 완료");
		   		
		   		forward.setRedirect(true);
		   		forward.setPath("/BbsDetailAction.bo?num=" + bbsBean.getBbsNum()+"&userNick="+bbsBean.getBbsNick());
		   		
		   	}catch(Exception e){
		   		System.out.println("게시판 수정 에러 : " + e);
		   		e.printStackTrace();
		   	}
			return forward;
		}  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
