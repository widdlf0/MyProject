package msg.msg.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import msg.db.MsgBean;
import msg.db.MsgDAO;

public class msgWriteAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	request.setCharacterEncoding("UTF-8");
		 	
		 	MsgDAO msgDAO=new MsgDAO();
		   	MsgBean msgBean=new MsgBean();
		   	ActionForward forward=new ActionForward();
		   	
	   		
	   		boolean result=false;
	   		
	   		try{
	   			
	   			msgBean.setMsgNick(request.getParameter("msgNick"));
	   			msgBean.setMsgTitle(request.getParameter("msgTitle"));
	   			msgBean.setMsgContent(request.getParameter("msgContent"));
	   			msgBean.setMsgReceiveNick(request.getParameter("msgReceiveNick"));
	   			
		   		
		   		result=msgDAO.MsgWrite(msgBean);
		   		
		   		if(result==false){
		   			System.out.println("메세지 등록 실패");
		   			return null;
		   		}
		   		System.out.println("메세지 등록 완료");
		   		
		   		forward.setRedirect(true);
		   		forward.setPath("/msgNewListAction.mg");
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
