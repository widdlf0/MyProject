package msg.db;

import java.sql.Date;

public class MsgBean {
	private int msgNum;
	private String msgTitle;
	private String msgNick;
	private Date msgDate;
	private String msgContent;
	private int msgAvailable;
	private String msgReceiveNick;
	private int rnum;
	
	
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public int getMsgNum() {
		return msgNum;
	}
	public void setMsgNum(int msgNum) {
		this.msgNum = msgNum;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgNick() {
		return msgNick;
	}
	public void setMsgNick(String msgNick) {
		this.msgNick = msgNick;
	}
	public Date getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public int getMsgAvailable() {
		return msgAvailable;
	}
	public void setMsgAvailable(int msgAvailable) {
		this.msgAvailable = msgAvailable;
	}
	public String getMsgReceiveNick() {
		return msgReceiveNick;
	}
	public void setMsgReceiveNick(String msgReceiveNick) {
		this.msgReceiveNick = msgReceiveNick;
	}
	
	

	
}
