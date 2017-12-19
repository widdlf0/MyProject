package notice.db;

import java.sql.Date;

public class NoticeBean {
	private int NoticeNum;
	private String NoticeNick;
	private String NoticeSubject;
	private String NoticeContent;
	private String NoticeFile;
	private int NoticeReRef;
	private int NoticeReLev;
	private int NoticeReSeq;
	private int NoticeReadcount;
	private Date NoticeDate;
	public int getNoticeNum() {
		return NoticeNum;
	}
	public void setNoticeNum(int noticeNum) {
		NoticeNum = noticeNum;
	}
	public String getNoticeNick() {
		return NoticeNick;
	}
	public void setNoticeNick(String noticeNick) {
		NoticeNick = noticeNick;
	}
	public String getNoticeSubject() {
		return NoticeSubject;
	}
	public void setNoticeSubject(String noticeSubject) {
		NoticeSubject = noticeSubject;
	}
	public String getNoticeContent() {
		return NoticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		NoticeContent = noticeContent;
	}
	public String getNoticeFile() {
		return NoticeFile;
	}
	public void setNoticeFile(String noticeFile) {
		NoticeFile = noticeFile;
	}
	public int getNoticeReRef() {
		return NoticeReRef;
	}
	public void setNoticeReRef(int noticeReRef) {
		NoticeReRef = noticeReRef;
	}
	public int getNoticeReLev() {
		return NoticeReLev;
	}
	public void setNoticeReLev(int noticeReLev) {
		NoticeReLev = noticeReLev;
	}
	public int getNoticeReSeq() {
		return NoticeReSeq;
	}
	public void setNoticeReSeq(int noticeReSeq) {
		NoticeReSeq = noticeReSeq;
	}
	public int getNoticeReadcount() {
		return NoticeReadcount;
	}
	public void setNoticeReadcount(int noticeReadcount) {
		NoticeReadcount = noticeReadcount;
	}
	public Date getNoticeDate() {
		return NoticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		NoticeDate = noticeDate;
	}
	
}
