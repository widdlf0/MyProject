package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import user.user.action.ActionForward;
import user.db.UserBean;
import user.db.UserDAO;


import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Authenticator;
import java.util.Properties;
import pe.hoyanet.mail.SMTPAuthenticator;


public class userSendMail implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	request.setCharacterEncoding("UTF-8");
		 	ActionForward forward = new ActionForward();
		 	// 세션에서 아이디를 가져와 MemberBean에 세팅한다.
		 
		 	HttpSession session=request.getSession();
		 	UserDAO userDAO=new UserDAO();
		 	UserBean user=new UserBean();
	   		
	   		request.setCharacterEncoding("UTF-8");

	   		PrintWriter out=response.getWriter();
	   		
	   		StringBuffer buffer = new StringBuffer();
	   		for (int i=0; i<6;i++){
	   			int n=(int)(Math.random()*10);
	   			buffer.append(n);
	   			}

	   		String sender = "widdlf9@naver.com"; // 보내는 메일주소 구글또는 네이버로 입력해주세요. 
	   		String receiver = request.getParameter("emailname");
	   		String subject = "OGV 회원가입 인증번호입니다. ";
	   		String content2=buffer.toString();
	   		session.setAttribute("content2", content2);
	   		String content = "인증번호는 ["+content2+"] 입니다. \n 인증번호를 사이트에 입력해서 인증을 완료하세요";

	   		// 정보를 담기 위한 객체
	   		Properties p = new Properties();
	   		
	   		// SMTP 서버의 계정 설정
	   		// Naver와 연결할 경우 네이버 아이디 지정
	   		// Google과 연결할 경우 본인의 Gmail 주소
	   		p.put("mail.smtp.user", "widdlf9");

	   		// SMTP 서버 정보 설정
	   		// 네이버일 경우 smtp.naver.com
	   		// Google일 경우 smtp.gmail.com
	   		p.put("mail.smtp.host", "smtp.naver.com");
	   		    
	   		// 아래 정보는 네이버와 구글이 동일하므로 수정하지 마세요.
	   		p.put("mail.smtp.port", "465");
	   		p.put("mail.smtp.starttls.enable", "true");
	   		p.put("mail.smtp.auth", "true");
	   		p.put("mail.smtp.debug", "true");
	   		p.put("mail.smtp.socketFactory.port", "465");
	   		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	   		p.put("mail.smtp.socketFactory.fallback", "false");


	   		try {
	   		    Authenticator auth = new SMTPAuthenticator();
	   		    Session ses = Session.getInstance(p, auth);

	   		    // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
	   		    ses.setDebug(true);
	   		        
	   		    // 메일의 내용을 담기 위한 객체
	   		    MimeMessage msg = new MimeMessage(ses);

	   		    // 제목 설정
	   		    msg.setSubject(subject);
	   		        
	   		    // 보내는 사람의 메일주소
	   		    Address fromAddr = new InternetAddress(sender);
	   		    msg.setFrom(fromAddr);
	   		        
	   		    // 받는 사람의 메일주소
	   		    Address toAddr = new InternetAddress(receiver);
	   		    msg.addRecipient(Message.RecipientType.TO, toAddr);
	   		        
	   		    // 메시지 본문의 내용과 형식, 캐릭터 셋 설정
	   		    msg.setContent(content, "text/html;charset=UTF-8");
	   		        
	   		    // 발송하기
	   		    Transport.send(msg);
	   		        
	   		} catch (Exception mex) {
	   		    mex.printStackTrace();
	   		   /* String script = "<script type='text/javascript'>\n";
	   		    script += "alert('메일발송에 실패했습니다.');\n";
	   		    script += "history.back();\n";
	   		    script += "</script>";*/
	   		    out.println("<script>");
	   		    out.println("alert('Mail Send fail!!')");
	   		    out.println("history.back()");
	   		    out.println("</script>");
	   		    /*out.print(script);*/
	   		    return null;
	   		}
	   		    
	   		/*String script = "<script type='text/javascript'>\n";
	   		script += "alert('메일발송에 성공했습니다.');\n";
	   		script += "</script>";
	   		script += "<meta http-equiv='refresh' content='0; url=./SendMailck.me' />";
	   		out.print(script);*/
	   		out.println("<script>");
   		    out.println("alert('Mail Send success!!')");
   		    out.println("location.href='./SendMailck.us'");
   		    out.println("</script>");
	   		
			return null;
	   		
	   		
	 }
	
	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}   		
	   		
	   		
	   		