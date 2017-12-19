package notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import notice.db.NoticeBean;
import notice.db.NoticeDAO;

public class NoticeWriteAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	NoticeDAO noticeDAO=new NoticeDAO();
		 	NoticeBean noticeBean=new NoticeBean();
		   	ActionForward forward=new ActionForward();
		   	
			String realFolder="";
	   		String saveFolder="noticeupload";
	   		
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
	   			
	   			noticeBean.setNoticeNick(multi.getParameter("NoticeNick"));
	   			noticeBean.setNoticeSubject(multi.getParameter("NoticeSubject"));
	   			noticeBean.setNoticeContent(multi.getParameter("NoticeContent"));
	   			noticeBean.setNoticeFile(multi.getFilesystemName((String)multi.getFileNames().nextElement()));
	   			
		   		
		   		result=noticeDAO.NoticeInsert(noticeBean);
		   		
		   		if(result==false){
		   			System.out.println("게시판 등록 실패");
		   			return null;
		   		}
		   		System.out.println("게시판 등록 완료");
		   		
		   		forward.setRedirect(true);
		   		forward.setPath("/NoticeListAction.no");
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