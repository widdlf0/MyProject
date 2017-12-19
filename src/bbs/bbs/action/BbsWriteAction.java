package bbs.bbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bbs.db.BbsDAO;
import bbs.db.BbsBean;

public class BbsWriteAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	BbsDAO bbsDAO=new BbsDAO();
		   	BbsBean bbsBean=new BbsBean();
		   	ActionForward forward=new ActionForward();
		   	
			String realFolder="";
	   		String saveFolder="bbsupload";
	   		
	   		int fileSize=10*1024*1024;
	   		
	   		realFolder=request.getRealPath(saveFolder);
	   		
	   		boolean result=false;
	   		
	   		try{
	   			MultipartRequest multi=null;
	   			
	   			multi=new MultipartRequest(request,
	   					realFolder,
	   					fileSize,
	   					"UTF-8",
	   					new DefaultFileRenamePolicy());
	   			
	   			bbsBean.setBbsNick(multi.getParameter("BbsNick"));
	   			bbsBean.setBbsSubject(multi.getParameter("BbsSubject"));
	   			bbsBean.setBbsContent(multi.getParameter("BbsContent"));
	   			bbsBean.setBbsFile(multi.getFilesystemName((String)multi.getFileNames().nextElement()));
	   			
		   		
		   		result=bbsDAO.BbsInsert(bbsBean);
		   		
		   		if(result==false){
		   			System.out.println("게시판 등록 실패");
		   			return null;
		   		}
		   		System.out.println("게시판 등록 완료");
		   		
		   		forward.setRedirect(true);
		   		forward.setPath("/BbsListAction.bo");
		   		return forward;
		   		
	  		}catch(Exception ex){
	   			ex.printStackTrace();
	   		}
	  		return null;
		}  	
	

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
}