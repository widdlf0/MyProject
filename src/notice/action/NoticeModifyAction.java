package notice.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import notice.db.NoticeBean;
import notice.db.NoticeDAO;

public class NoticeModifyAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 
		 	ActionForward forward=new ActionForward();
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	
		 	
		 	NoticeDAO noticeDAO=new NoticeDAO();
		 	NoticeBean noticeBean=new NoticeBean();
		   	
		   	request.setAttribute("noticeBean", noticeBean);
//		   	String BbsNum = request.getParameter("BbsNum");
//		   	System.out.println("BbsNum : "+BbsNum);
		   	//System.out.println("넘버값.:"+request.getParameter("BbsNum"));
		   	//int num = Integer.parseInt(request.getParameter("BbsNum")); //여기 에러네
		   	//System.out.println("BbsNum : " + num);
		   	//String BbsNick = request.getParameter("BbsNick");
		   	
		   	
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
	   			
	   			String ffff = multi.getParameter("NoticeNum");
	   			System.out.println("fffff: "+ffff);
	   			int num = Integer.parseInt(multi.getParameter("NoticeNum"));
	   			
	   			String NoticeNick = multi.getParameter("NoticeNick");
	   			boolean usercheck = noticeDAO.isNoticeWriter(num, NoticeNick);
			   	
			   	if(usercheck == false) {
			   		
			   		response.setContentType("text/html:charset=UTF-8");
			   		forward.setRedirect(false);
				   	forward.setPath("/isBbsWriterfalse.no");
				   	return forward;
			   }
	   			
			   	noticeBean.setNoticeNum(num);
			   	noticeBean.setNoticeSubject(multi.getParameter("NoticeSubject"));
			   	noticeBean.setNoticeContent(multi.getParameter("NoticeContent"));
			   	noticeBean.setNoticeFile(multi.getFilesystemName((String)multi.getFileNames().nextElement()));
		   	
		   	result = noticeDAO.NoticeModify(noticeBean);
		   		if(result == false) {
		   			System.out.println("게시판 수정 실패");
		   			return null;
		   		}
		   		System.out.println("게시판 수정 완료");
		   		
		   		forward.setRedirect(true);
		   		forward.setPath("/NoticeDetailAction.no?num=" + noticeBean.getNoticeNum());
		   		
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
