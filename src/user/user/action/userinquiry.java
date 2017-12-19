package user.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import user.user.action.ActionForward;

import java.util.Map;
import java.util.List;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import pe.hoyanet.mail.SMTPAuthenticator;
import javax.mail.Authenticator;
import java.util.Properties;

public class userinquiry implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		request.setCharacterEncoding("UTF-8");
	 	ActionForward forward = new ActionForward();
	 	// 세션에서 아이디를 가져와 MemberBean에 세팅한다.
	 	System.out.println("===2===");
	 	HttpSession session=request.getSession();
	 
   		
   		request.setCharacterEncoding("UTF-8");

   		PrintWriter out=response.getWriter();
   		
   		System.out.println("===2.1===");
   		request.setCharacterEncoding("utf-8");

   		//String company  = request.getParameter("company");
   		String name = request.getParameter("name");
   		String from = request.getParameter("from");
   		String to = request.getParameter("to");
   		String email = request.getParameter("email");
   		String number = request.getParameter("number");
   		String subject = request.getParameter("subject");
   		String content = request.getParameter("content");
   		System.out.println("===2.2===");  
   		Properties p = new Properties(); // 정보를 담을 객체
   		System.out.println("===2.3===");  
   		p.put("mail.smtp.host","smtp.naver.com");
   		p.put("mail.smtp.port", "465");
   		p.put("mail.smtp.starttls.enable", "true");
   		p.put("mail.smtp.auth", "true");
   		p.put("mail.smtp.debug", "true");
   		p.put("mail.smtp.socketFactory.port", "465");
   		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
   		p.put("mail.smtp.socketFactory.fallback", "false");
   		 
   		System.out.println("===2.4===");  
   		try{
   			System.out.println("===2.5===");
   		    Authenticator auth = new SMTPAuthenticator();
   		    Session ses = Session.getInstance(p, auth);
   		      
   		    ses.setDebug(true);
   		    MimeMessage msg = new MimeMessage(ses); // 메일의 내용을 담을 객체 
   		 
   		    msg.setSubject(subject); //  제목
   		    
   		    System.out.println("===2.6===");
   		    
   		    StringBuffer buffer = new StringBuffer();
   		   /*  buffer.append("업체명 : ");
   		    buffer.append(company+"<br>"); */
   		    buffer.append("문의종류 : ");
   		    buffer.append(name+"<br>");   
   		    buffer.append("이메일 : ");
		    buffer.append(email+"<br>"); 
   		    buffer.append("연락처 : ");
   		    buffer.append(number+"<br>");  
   		    buffer.append("제목 : ");
   		    buffer.append(subject+"<br>");
   		    buffer.append("내용 : ");
   		    buffer.append(content.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")+"<br>");
   		    Address fromAddr = new InternetAddress(from);
   		    msg.setFrom(fromAddr); 
   		    
   		 System.out.println("===2.7===");
   		 
   		    Address toAddr = new InternetAddress(to);
   		    msg.addRecipient(Message.RecipientType.TO, toAddr); // 받는 사람
   		 System.out.println("===2.8===");
   		    msg.setContent(buffer.toString(), "text/html;charset=UTF-8"); // 내용
   		 System.out.println("===2.9===");
   		    Transport.send(msg); // 전송  
   		    out.print("<script>");
   		    out.print("alert('Mail Send success!!');");
   		    out.print("location.href='./main.us'");
   		    out.print("</script>");
   		 
   		} catch(Exception e){
   			System.out.println("===2.91===");
   		    e.printStackTrace();
   		    out.print("<script>");
   		    out.print("alert('Mail Send fail!!');");
   		    out.print("history.back()");
   		    out.print("</script>");
   		    return null;
   		}
		return forward;
	   		
	   		
	 }
	
	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}   		
	   		
	   		