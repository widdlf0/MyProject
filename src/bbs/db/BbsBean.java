package bbs.db;

import java.sql.Date;

public class BbsBean {

		private int BbsNum;
		private String BbsNick;
		private String BbsSubject;
		private String BbsContent;
		private String BbsFile;
		private int BbsReRef;
		private int BbsReLev;
		private int BbsReSeq;
		private int BbsReadcount;
		private Date BbsDate;
		
		//댓글 변수
		private int BbsAnswerNum;
		private int BbsNumCheck;
		private String BbsNickCheck;
		private String BbsAnswerContent;
		private int BbsAnswerAgree;
		private int BbsAnswerNotAgree;
		private String AnswerDate;
		private int AnswerCount;
		private int ReplyNum;
		
		
		
		
		public int getReplyNum() {
			return ReplyNum;
		}
		public void setReplyNum(int replyNum) {
			ReplyNum = replyNum;
		}
		public int getAnswerCount() {
			return AnswerCount;
		}
		public void setAnswerCount(int answerCount) {
			AnswerCount = answerCount;
		}
		public int getBbsAnswerNum() {
			return BbsAnswerNum;
		}
		public void setBbsAnswerNum(int bbsAnswerNum) {
			BbsAnswerNum = bbsAnswerNum;
		}
		public int getBbsNumCheck() {
			return BbsNumCheck;
		}
		public void setBbsNumCheck(int bbsNumCheck) {
			BbsNumCheck = bbsNumCheck;
		}
		public String getBbsNickCheck() {
			return BbsNickCheck;
		}
		public void setBbsNickCheck(String bbsNickCheck) {
			BbsNickCheck = bbsNickCheck;
		}
		public String getBbsAnswerContent() {
			return BbsAnswerContent;
		}
		public void setBbsAnswerContent(String bbsAnswerContent) {
			BbsAnswerContent = bbsAnswerContent;
		}
		public int getBbsAnswerAgree() {
			return BbsAnswerAgree;
		}
		public void setBbsAnswerAgree(int bbsAnswerAgree) {
			BbsAnswerAgree = bbsAnswerAgree;
		}
		public int getBbsAnswerNotAgree() {
			return BbsAnswerNotAgree;
		}
		public void setBbsAnswerNotAgree(int bbsAnswerNotAgree) {
			BbsAnswerNotAgree = bbsAnswerNotAgree;
		}
		public String getAnswerDate() {
			return AnswerDate;
		}
		public void setAnswerDate(String answerDate) {
			AnswerDate = answerDate;
		}
		public int getBbsNum() {
			return BbsNum;
		}
		public void setBbsNum(int bbsNum) {
			BbsNum = bbsNum;
		}
		public String getBbsNick() {
			return BbsNick;
		}
		public void setBbsNick(String bbsNick) {
			BbsNick = bbsNick;
		}
		public String getBbsSubject() {
			return BbsSubject;
		}
		public void setBbsSubject(String bbsSubject) {
			BbsSubject = bbsSubject;
		}
		public String getBbsContent() {
			return BbsContent;
		}
		public void setBbsContent(String bbsContent) {
			BbsContent = bbsContent;
		}
		public String getBbsFile() {
			return BbsFile;
		}
		public void setBbsFile(String bbsFile) {
			BbsFile = bbsFile;
		}
		public int getBbsReRef() {
			return BbsReRef;
		}
		public void setBbsReRef(int bbsReRef) {
			BbsReRef = bbsReRef;
		}
		public int getBbsReLev() {
			return BbsReLev;
		}
		public void setBbsReLev(int bbsReLev) {
			BbsReLev = bbsReLev;
		}
		public int getBbsReSeq() {
			return BbsReSeq;
		}
		public void setBbsReSeq(int bbsReSeq) {
			BbsReSeq = bbsReSeq;
		}
		public int getBbsReadcount() {
			return BbsReadcount;
		}
		public void setBbsReadcount(int bbsReadcount) {
			BbsReadcount = bbsReadcount;
		}
		public Date getBbsDate() {
			return BbsDate;
		}
		public void setBbsDate(Date bbsDate) {
			BbsDate = bbsDate;
		}
		
		
		
		
		
}
