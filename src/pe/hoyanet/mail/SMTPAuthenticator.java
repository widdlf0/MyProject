package pe.hoyanet.mail;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
    	// 네이버나 Gmail 사용자 계정 설정. Gmail의 경우 @gmail.com을 제외한 아이디만 입력한다.
        return new PasswordAuthentication("widdlf9", "jeongil2468"); 
        // 두번째 따옴표 안에 메일계정 비번 넣습니다. 제 비번은 공개할 수가 없네요;; 죄송;;
    }
}