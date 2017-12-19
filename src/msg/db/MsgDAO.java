package msg.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class MsgDAO {

//	private Connection conn; // 데이터베이스에 접근하게 하기위한 객체
//	private ResultSet rs; // 정보를 담을수 있는 객체
//
//	public MsgDAO() {// 자동으로 데이터베이스 연결이 되도록 생성자를 만듦
//		// mysql에 접속할 수 있게 해주는 생성자
//		try {
//			String dbURL = "jdbc:mysql://localhost:3306/BBS"; // 로컬호스트의 포트 3306에 접속하여 BBS에 접속
//			String dbID = "root"; // ID 값
//			String dbPassword = "root"; // Password 값
//			Class.forName("com.mysql.jdbc.Driver"); // mysql드라이버를 찾을 수 있도록 경로를 설정
//			// 데이터베이스에 접속할 수 있도록 하게하는 매개체 역할
//			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public MsgDAO() {
		try {
	         Context init = new InitialContext();
	         ds = (DataSource) init.lookup("java:comp/env/jdbc/mysql");
	      } catch (Exception ex) {
	         System.out.println("DB 연결 실패 : " + ex);
	         return;
	      }
	}

//	public String getDate() { // 우편함에 작성일을 추가 시킬때
//
//		String SQL = "SELECT NOW()";
//		try {
//
//			PreparedStatement pstmt = conn.prepareStatement(SQL);
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				return rs.getString(1);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ""; // 데이터베이스 오류시 return "";
//	}
//
//	public int getNext() { // 우편함의 글 번호를 메길 때
//
//		String SQL = "SELECT msgID FROM MSG ORDER BY msgID DESC";
//		try {
//
//			PreparedStatement pstmt = conn.prepareStatement(SQL);
//			rs = pstmt.executeQuery();
//			if (rs.next()) { // 두 번째 이상 게시물 부터
//				return rs.getInt(1) + 1;
//			}
//			return 1; // 첫 번째 게시물인 경우 return 1;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return -1; // 데이터베이스 오류시 return -1;
//	}

	//글 등록
	public boolean MsgWrite(MsgBean msg){
				int num =0;
				String sql="";
				
				int result=0;
				
				try{
					conn = ds.getConnection();
					pstmt=conn.prepareStatement(
							"select max(msgNum) from msg");
					rs = pstmt.executeQuery();
					
					if(rs.next())
						num =rs.getInt(1)+1;
					else
						num=1;
					
					sql="insert into msg (msgNum, msgTitle, msgNick, msgDate, msgContent, msgAvailable,"+
						"msgReceiveNick) values(?,?,?,NOW(),?,?,?)";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num);
					pstmt.setString(2, msg.getMsgTitle());
					pstmt.setString(3, msg.getMsgNick());
					pstmt.setString(4, msg.getMsgContent());
					pstmt.setInt(5, 0);
					pstmt.setString(6, msg.getMsgReceiveNick());
					
					
					result=pstmt.executeUpdate();
					if(result==0)return false;
					
					return true;
				}catch(Exception ex){
					System.out.println("msgwrite 에러 : "+ex);
				}finally{
					if(rs!=null) try{rs.close();}catch(SQLException ex){}
					if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
					if(conn!=null) try{conn.close();}catch(SQLException ex){}
				}
				return false;
	}
	
//	public int write(String msgReceiveID , String msgTitle, String userID, String msgContent) { // 쪽지보내기 할 때 테이블에 입력
//		String SQL = "INSERT INTO MSG VALUES(?, ?, ?, ?, ?, ?, ?)";
//		try {
//
//			PreparedStatement pstmt = conn.prepareStatement(SQL);
//			pstmt.setInt(1, getNext());
//			pstmt.setString(2, msgTitle);
//			pstmt.setString(3, userID);
//			pstmt.setString(4, getDate());
//			pstmt.setString(5, msgContent);
//			pstmt.setInt(6, 1);
//			pstmt.setString(7, msgReceiveID);
//			return pstmt.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return -1; // 데이터베이스 오류시 return -1;
//	}
	
	//새로 받은 메세지목록 가져오기
			public ArrayList<MsgBean> getMsgList(HashMap<String, Object> listOpt)
			{
			    ArrayList<MsgBean> list = new ArrayList<MsgBean>();
			    
			    String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
			    String condition = (String)listOpt.get("condition"); // 검색내용
			    int start = (Integer)listOpt.get("start"); // 현재 페이지번호
			    String Nick = (String)listOpt.get("userNick");
			    System.out.println("MsgDAO Nick : " + Nick);
			    try {
			    	conn=ds.getConnection();
			        
			        // 글목록 전체를 보여줄 때
			        if(opt == null)
			        {
			            // BOARD_RE_REF(그룹번호)의 내림차순 정렬 후 동일한 그룹번호일 때는
			            // BOARD_RE_SEQ(답변글 순서)의 오름차순으로 정렬 한 후에
			            // 10개의 글을 한 화면에 보여주는(start번째 부터 start+9까지) 쿼리
			            // desc : 내림차순, asc : 오름차순 ( 생략 가능 )
			        	String msg_list_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 0 "
			        			+"and msgReceiveNick like ? order by rnum desc limit ?,?";
//			        	String msg_list_sql="select * from "
//			        			+ "(select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
//			        			+ "msgDate, msgAvailable from msg,(select @rownum:=0)tmp order by rnum desc)"
//			        			+ " msg where rnum>=? and rnum<=? and msgAvailable = 0";
			        	
			        	pstmt = conn.prepareStatement(msg_list_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 10);
			           
			        }
			        else if(opt.equals("0")) // 제목으로 검색
			        {
			        	String msg_msgTitleserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 0 "
			        			+"and msgReceiveNick like ? and msgTitle like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_msgTitleserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			            
			        }
			        else if(opt.equals("1")) // 내용으로 검색
			        {
			        	String msg_contentserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 0 "
			        			+"and msgReceiveNick like ? and msgContent like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_contentserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			            
			        }
			        else if(opt.equals("2")) // 제목+내용으로 검색
			        {
			        	String msg_subjectcontentserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 0 "
			        			+"and msgReceiveNick like ? and msgContent like ? and msgTitle like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_subjectcontentserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setString(3, "%"+condition+"%");
			            pstmt.setInt(4, start-1);
			            pstmt.setInt(5, 10);
			           
			        }
			        else if(opt.equals("3")) // 글쓴이로 검색
			        {
			        	String msg_idserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 0 "
			        			+"and msgReceiveNick like ? and msgNick like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_idserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			            
			        }
			        
			        rs = pstmt.executeQuery();
			        while(rs.next())
			        {
			        	MsgBean msgBean = new MsgBean();

			        	msgBean.setRnum(rs.getInt("rnum"));
			        	msgBean.setMsgNum(rs.getInt("msgNum"));
			        	msgBean.setMsgReceiveNick(rs.getString("msgReceiveNick"));
			        	msgBean.setMsgTitle(rs.getString("msgTitle"));
			        	msgBean.setMsgContent(rs.getString("msgContent"));
			        	msgBean.setMsgNick(rs.getString("msgNick"));
			        	msgBean.setMsgDate(rs.getDate("msgDate"));
			        	msgBean.setMsgAvailable(rs.getInt("msgAvailable"));
			        	list.add(msgBean);
			        }
			        
			        return list;
				}catch(Exception ex){
					System.out.println("getMsgList 에러 : " + ex);
				}finally{
					if(rs!=null) try{rs.close();}catch(SQLException ex){}
					if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
					if(conn!=null) try{conn.close();}catch(SQLException ex){}
				}
				return null;
			}
			
			//읽은 메세지목록 가져오기
			public ArrayList<MsgBean> getMsgReadList(HashMap<String, Object> listOpt)
			{
			    ArrayList<MsgBean> list = new ArrayList<MsgBean>();
			    
			    String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
			    String condition = (String)listOpt.get("condition"); // 검색내용
			    int start = (Integer)listOpt.get("start"); // 현재 페이지번호
			    String Nick = (String)listOpt.get("userNick");
			    System.out.println("MsgDAO Nick : " + Nick);
			    try {
			    	conn=ds.getConnection();
			        
			        // 글목록 전체를 보여줄 때
			        if(opt == null)
			        {
			            // BOARD_RE_REF(그룹번호)의 내림차순 정렬 후 동일한 그룹번호일 때는
			            // BOARD_RE_SEQ(답변글 순서)의 오름차순으로 정렬 한 후에
			            // 10개의 글을 한 화면에 보여주는(start번째 부터 start+9까지) 쿼리
			            // desc : 내림차순, asc : 오름차순 ( 생략 가능 )
			        	String msg_list_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 1 "
			        			+"and msgReceiveNick like ? order by rnum desc limit ?,?";
//			        	String msg_list_sql="select * from "
//			        			+ "(select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
//			        			+ "msgDate, msgAvailable from msg,(select @rownum:=0)tmp order by rnum desc)"
//			        			+ " msg where rnum>=? and rnum<=? and msgAvailable = 0";
			        	
			        	pstmt = conn.prepareStatement(msg_list_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 10);
			           
			        }
			        else if(opt.equals("0")) // 제목으로 검색
			        {
			        	String msg_msgTitleserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 1 "
			        			+"and msgReceiveNick like ? and msgTitle like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_msgTitleserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			            
			        }
			        else if(opt.equals("1")) // 내용으로 검색
			        {
			        	String msg_contentserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 1 "
			        			+"and msgReceiveNick like ? and msgContent like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_contentserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			            
			        }
			        else if(opt.equals("2")) // 제목+내용으로 검색
			        {
			        	String msg_subjectcontentserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 1 "
			        			+"and msgReceiveNick like ? and msgContent like ? and msgTitle like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_subjectcontentserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setString(3, "%"+condition+"%");
			            pstmt.setInt(4, start-1);
			            pstmt.setInt(5, 10);
			           
			        }
			        else if(opt.equals("3")) // 글쓴이로 검색
			        {
			        	String msg_idserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 1 "
			        			+"and msgReceiveNick like ? and msgNick like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_idserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			            
			        }
			        
			        rs = pstmt.executeQuery();
			        while(rs.next())
			        {
			        	MsgBean msgBean = new MsgBean();

			        	msgBean.setRnum(rs.getInt("rnum"));
			        	msgBean.setMsgNum(rs.getInt("msgNum"));
			        	msgBean.setMsgReceiveNick(rs.getString("msgReceiveNick"));
			        	msgBean.setMsgTitle(rs.getString("msgTitle"));
			        	msgBean.setMsgContent(rs.getString("msgContent"));
			        	msgBean.setMsgNick(rs.getString("msgNick"));
			        	msgBean.setMsgDate(rs.getDate("msgDate"));
			        	msgBean.setMsgAvailable(rs.getInt("msgAvailable"));
			        	list.add(msgBean);
			        }
			        
			        return list;
				}catch(Exception ex){
					System.out.println("getMsgList 에러 : " + ex);
				}finally{
					if(rs!=null) try{rs.close();}catch(SQLException ex){}
					if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
					if(conn!=null) try{conn.close();}catch(SQLException ex){}
				}
				return null;
			}
			
			//보낸 메세지목록 가져오기
			public ArrayList<MsgBean> getMsgSendList(HashMap<String, Object> listOpt)
			{
			    ArrayList<MsgBean> list = new ArrayList<MsgBean>();
			    
			    String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
			    String condition = (String)listOpt.get("condition"); // 검색내용
			    int start = (Integer)listOpt.get("start"); // 현재 페이지번호
			    String Nick = (String)listOpt.get("userNick");
			    System.out.println("MsgDAO Nick : " + Nick);
			    try {
			    	conn=ds.getConnection();
			        
			        // 글목록 전체를 보여줄 때
			        if(opt == null)
			        {
			            // BOARD_RE_REF(그룹번호)의 내림차순 정렬 후 동일한 그룹번호일 때는
			            // BOARD_RE_SEQ(답변글 순서)의 오름차순으로 정렬 한 후에
			            // 10개의 글을 한 화면에 보여주는(start번째 부터 start+9까지) 쿼리
			            // desc : 내림차순, asc : 오름차순 ( 생략 가능 )
			        	String msg_list_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where "
			        			+"msgNick like ? order by rnum desc limit ?,?";
//			        	String msg_list_sql="select * from "
//			        			+ "(select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
//			        			+ "msgDate, msgAvailable from msg,(select @rownum:=0)tmp order by rnum desc)"
//			        			+ " msg where rnum>=? and rnum<=? and msgAvailable = 0";
			        	
			        	pstmt = conn.prepareStatement(msg_list_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 10);
			           
			        }
			        else if(opt.equals("0")) // 제목으로 검색
			        {
			        	String msg_msgTitleserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where "
			        			+"msgNick like ? and msgTitle like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_msgTitleserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			            
			        }
			        else if(opt.equals("1")) // 내용으로 검색
			        {
			        	String msg_contentserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where "
			        			+"msgNick like ? and msgContent like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_contentserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			            
			        }
			        else if(opt.equals("2")) // 제목+내용으로 검색
			        {
			        	String msg_subjectcontentserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where "
			        			+"msgNick like ? and msgContent like ? and msgTitle like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_subjectcontentserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setString(3, "%"+condition+"%");
			            pstmt.setInt(4, start-1);
			            pstmt.setInt(5, 10);
			           
			        }
			        else if(opt.equals("3")) // 받은이로 검색
			        {
			        	String msg_idserch_sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where "
			        			+"msgNick like ? and msgReceiveNick like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(msg_idserch_sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setString(2, "%"+condition+"%");
			            pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			            
			        }
			        
			        rs = pstmt.executeQuery();
			        while(rs.next())
			        {
			        	MsgBean msgBean = new MsgBean();

			        	msgBean.setRnum(rs.getInt("rnum"));
			        	msgBean.setMsgNum(rs.getInt("msgNum"));
			        	msgBean.setMsgReceiveNick(rs.getString("msgReceiveNick"));
			        	msgBean.setMsgTitle(rs.getString("msgTitle"));
			        	msgBean.setMsgContent(rs.getString("msgContent"));
			        	msgBean.setMsgNick(rs.getString("msgNick"));
			        	msgBean.setMsgDate(rs.getDate("msgDate"));
			        	msgBean.setMsgAvailable(rs.getInt("msgAvailable"));
			        	list.add(msgBean);
			        }
			        
			        return list;
				}catch(Exception ex){
					System.out.println("getMsgList 에러 : " + ex);
				}finally{
					if(rs!=null) try{rs.close();}catch(SQLException ex){}
					if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
					if(conn!=null) try{conn.close();}catch(SQLException ex){}
				}
				return null;
			}
			
			//휴지통 메세지목록 가져오기
			public ArrayList<MsgBean> getMsgTrashBoxList(HashMap<String, Object> listOpt)
			{
			    ArrayList<MsgBean> list = new ArrayList<MsgBean>();
			    
			    String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
			    String condition = (String)listOpt.get("condition"); // 검색내용
			    int start = (Integer)listOpt.get("start"); // 현재 페이지번호
			    String Nick = (String)listOpt.get("userNick");
			    System.out.println("MsgDAO Nick : " + Nick);
			    try {
			    	conn=ds.getConnection();
			        
			        // 글목록 전체를 보여줄 때
			        if(opt == null){
			        	
			        	String sql="select @rownum:=@rownum+1 rnum, msgNum, msgTitle, msgReceiveNick, msgContent, msgNick,"
			        			+"msgDate, msgAvailable from msg,(select @rownum:=0)tmp where msgAvailable = 2 "
			        			+"and msgReceiveNick like ? order by rnum desc limit ?,?";

			        	pstmt = conn.prepareStatement(sql);
			        	pstmt.setString(1, Nick);
			            pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 10);
			           
			        }
			        
			        rs = pstmt.executeQuery();
			        while(rs.next())
			        {
			        	MsgBean msgBean = new MsgBean();

			        	msgBean.setRnum(rs.getInt("rnum"));
			        	msgBean.setMsgNum(rs.getInt("msgNum"));
			        	msgBean.setMsgReceiveNick(rs.getString("msgReceiveNick"));
			        	msgBean.setMsgTitle(rs.getString("msgTitle"));
			        	msgBean.setMsgContent(rs.getString("msgContent"));
			        	msgBean.setMsgNick(rs.getString("msgNick"));
			        	msgBean.setMsgDate(rs.getDate("msgDate"));
			        	msgBean.setMsgAvailable(rs.getInt("msgAvailable"));
			        	list.add(msgBean);
			        }
			        
			        return list;
				}catch(Exception ex){
					System.out.println("getMsgList 에러 : " + ex);
				}finally{
					if(rs!=null) try{rs.close();}catch(SQLException ex){}
					if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
					if(conn!=null) try{conn.close();}catch(SQLException ex){}
				}
				return null;
			}
			
			//글 개수 구하기
			public int getMsgListCount(HashMap<String, Object> listOpt)
		    {
		        int result = 0;
		        String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
		        String condition = (String)listOpt.get("condition"); // 검색내용
		        String Nick = (String)listOpt.get("userNick");
		        
		        try {
		        	conn=ds.getConnection();
		            StringBuffer sql = new StringBuffer();
		            
		            if(opt == null)    // 전체글의 개수
		            {
		                String sqlcount = "select count(*) from msg where msgAvailable = 0 and msgReceiveNick like ? ";
		                
		                pstmt = conn.prepareStatement(sqlcount);
		                pstmt.setString(1, Nick);
		                // StringBuffer를 비운다.
		            }
		            else if(opt.equals("0")) // 제목으로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg where msgAvailable = 0 and msgReceiveNick like ? and msgTitle like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            else if(opt.equals("1")) // 내용으로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg where msgAvailable = 0 and msgReceiveNick like ? and msgContent like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            else if(opt.equals("2")) // 제목+내용으로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg ");
		                sql.append("where msgAvailable = 0 and msgReceiveNick like ? and msgTitle like ? and msgContent like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                pstmt.setString(3, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            else if(opt.equals("3")) // 글쓴이로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg where msgAvailable = 0 and msgReceiveNick like ? and msgNick like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            
		            rs = pstmt.executeQuery();
		            if(rs.next()) {    
		            	result = rs.getInt(1);
		            	System.out.println("카운트 글 개수 : " + result);
		            }
		        } catch(Exception ex){
					System.out.println("getListCount 에러 : " + ex);			
				}finally{
					if(rs!=null) try{rs.close();}catch(SQLException ex){}
					if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
					if(conn!=null) try{conn.close();}catch(SQLException ex){}
				}
		        
		        return result;
		    }
			
			//읽은 메시지 개수 구하기
			public int getMsgReadListCount(HashMap<String, Object> listOpt)
		    {
		        int result = 0;
		        String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
		        String condition = (String)listOpt.get("condition"); // 검색내용
		        String Nick = (String)listOpt.get("userNick");
		        
		        try {
		        	conn=ds.getConnection();
		            StringBuffer sql = new StringBuffer();
		            
		            if(opt == null)    // 전체글의 개수
		            {
		                String sqlcount = "select count(*) from msg where msgAvailable = 1 and msgReceiveNick like ? ";
		                
		                pstmt = conn.prepareStatement(sqlcount);
		                pstmt.setString(1, Nick);
		                // StringBuffer를 비운다.
		            }
		            else if(opt.equals("0")) // 제목으로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg where msgAvailable = 1 and msgReceiveNick like ? and msgTitle like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            else if(opt.equals("1")) // 내용으로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg where msgAvailable = 1 and msgReceiveNick like ? and msgContent like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            else if(opt.equals("2")) // 제목+내용으로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg ");
		                sql.append("where msgAvailable = 1 and msgReceiveNick like ? and msgTitle like ? and msgContent like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                pstmt.setString(3, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            else if(opt.equals("3")) // 글쓴이로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg where msgAvailable = 1 and msgReceiveNick like ? and msgNick like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            
		            rs = pstmt.executeQuery();
		            if(rs.next()) {    
		            	result = rs.getInt(1);
		            	System.out.println("카운트 글 개수 : " + result);
		            }
		        } catch(Exception ex){
					System.out.println("getMsgReadListCount 에러 : " + ex);			
				}finally{
					if(rs!=null) try{rs.close();}catch(SQLException ex){}
					if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
					if(conn!=null) try{conn.close();}catch(SQLException ex){}
				}
		        
		        return result;
		    }
			
			//보낸 메시지 개수 구하기
			public int getMsgSendListCount(HashMap<String, Object> listOpt)
		    {
		        int result = 0;
		        String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
		        String condition = (String)listOpt.get("condition"); // 검색내용
		        String Nick = (String)listOpt.get("userNick");
		        
		        try {
		        	conn=ds.getConnection();
		            StringBuffer sql = new StringBuffer();
		            
		            if(opt == null)    // 전체글의 개수
		            {
		                String sqlcount = "select count(*) from msg where msgNick like ? ";
		                
		                pstmt = conn.prepareStatement(sqlcount);
		                pstmt.setString(1, Nick);
		                // StringBuffer를 비운다.
		            }
		            else if(opt.equals("0")) // 제목으로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg where msgNick like ? and msgTitle like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            else if(opt.equals("1")) // 내용으로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg where msgNick like ? and msgContent like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            else if(opt.equals("2")) // 제목+내용으로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg ");
		                sql.append("where msgNick like ? and msgTitle like ? and msgContent like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                pstmt.setString(3, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            else if(opt.equals("3")) // 받은사람으로 검색한 글의 개수
		            {
		                sql.append("select count(*) from msg where msgNick like ? and msgReceiveNick like ?");
		                pstmt = conn.prepareStatement(sql.toString());
		                pstmt.setString(1, Nick);
		                pstmt.setString(2, '%'+condition+'%');
		                
		                sql.delete(0, sql.toString().length());
		            }
		            
		            rs = pstmt.executeQuery();
		            if(rs.next()) {    
		            	result = rs.getInt(1);
		            	System.out.println("카운트 글 개수 : " + result);
		            }
		        } catch(Exception ex){
					System.out.println("getMsgSendListCount 에러 : " + ex);			
				}finally{
					if(rs!=null) try{rs.close();}catch(SQLException ex){}
					if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
					if(conn!=null) try{conn.close();}catch(SQLException ex){}
				}
		        
		        return result;
		    }
			
			//휴지통 메시지 개수 구하기
			public int getMsgTrashBoxListCount(HashMap<String, Object> listOpt)
		    {
		        int result = 0;
		        String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
		        String condition = (String)listOpt.get("condition"); // 검색내용
		        String Nick = (String)listOpt.get("userNick");
		        
		        try {
		        	conn=ds.getConnection();
		            
		            if(opt == null)    // 전체글의 개수
		            {
		                String sqlcount = "select count(*) from msg where msgAvailable = 2 and msgReceiveNick like ? ";
		                
		                pstmt = conn.prepareStatement(sqlcount);
		                pstmt.setString(1, Nick);
		            }
		            
		            
		            rs = pstmt.executeQuery();
		            if(rs.next()) {    
		            	result = rs.getInt(1);
		            	System.out.println("카운트 글 개수 : " + result);
		            }
		        } catch(Exception ex){
					System.out.println("getMsgTrashBoxListCount 에러 : " + ex);			
				}finally{
					if(rs!=null) try{rs.close();}catch(SQLException ex){}
					if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
					if(conn!=null) try{conn.close();}catch(SQLException ex){}
				}
		        
		        return result;
		    }
			
			//메세지 내용 보기
			public MsgBean getMsgDetail(int num) throws Exception{
				MsgBean msgBean = null;
				try{
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(
							"select * from msg where msgNum = ?");
					pstmt.setInt(1, num);
					
					rs= pstmt.executeQuery();
					
					if(rs.next()){
						msgBean = new MsgBean();
			        	msgBean.setMsgNum(rs.getInt("msgNum"));
			        	msgBean.setMsgReceiveNick(rs.getString("msgReceiveNick"));
			        	msgBean.setMsgTitle(rs.getString("msgTitle"));
			        	msgBean.setMsgContent(rs.getString("msgContent"));
			        	msgBean.setMsgNick(rs.getString("msgNick"));
			        	msgBean.setMsgDate(rs.getDate("msgDate"));
			        	msgBean.setMsgAvailable(rs.getInt("msgAvailable"));
			        	
					}
					return msgBean;
				}catch(Exception ex){
					System.out.println("getmsgDetail 에러 : " + ex);
				}finally{
					if(rs!=null)try{rs.close();}catch(SQLException ex){}
					if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
					if(conn !=null)try{conn.close();}catch(SQLException ex){}
				}
				return null;
			}
			
			//뉴 메세지 창 띄우기
			public ArrayList<MsgBean> getNewMsg(String msgNick) throws Exception{
				ArrayList<MsgBean> list = new ArrayList<MsgBean>();
				MsgBean msgBean = null;
				try{
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(
							"select msgAvailable from msg where msgReceiveNick = ?");
					pstmt.setString(1, msgNick);
					
					rs= pstmt.executeQuery();
					
					if(rs.next()){
						msgBean = new MsgBean();
			        	msgBean.setMsgAvailable(rs.getInt("msgAvailable"));
			        	list.add(msgBean);
					}
					return list;
				}catch(Exception ex){
					System.out.println("getNewMsg 에러 : " + ex);
				}finally{
					if(rs!=null)try{rs.close();}catch(SQLException ex){}
					if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
					if(conn !=null)try{conn.close();}catch(SQLException ex){}
				}
				return null;
			}
			
			//Available 업데이트
			public void setMsgAvailableUpdate(int num) throws Exception{
				String sql="update msg set msgAvailable = msgAvailable+1 where msgNum = "+num;
				
				try{
					conn=ds.getConnection();
					pstmt=conn.prepareStatement(sql);
					pstmt.executeUpdate();
				}catch(SQLException ex){
					System.out.println("setMsgAvailableUpdate 에러 : "+ex);
				}
				finally{
					try{
						if(pstmt!=null)pstmt.close();
						if(conn!=null)conn.close();
					}catch(Exception ex) {}
				}
			}
			
			//메세지 삭제
			public boolean MsgDelete(int num){
				String SQL="delete from msg where msgNum=?";
				
				int result=0;
				
				try{
					conn = ds.getConnection();
					pstmt=conn.prepareStatement(SQL);
					pstmt.setInt(1, num);
					result=pstmt.executeUpdate();
					if(result==0)return false;
					
					return true;
				}catch(Exception ex){
					System.out.println("MsgDelete 에러 : "+ex);
				}finally{
					try{
						if(pstmt!=null)pstmt.close();
						if(conn!=null)conn.close();
					}catch(Exception ex) {}
				}
				
				return false;
			}
			
			//휴지통 비우기
			public boolean MsgAllDelete(int available, String Nick){
				String SQL="delete from msg where msgAvailable=? and msgReceiveNick like ?";
				
				int result=0;
				
				try{
					conn = ds.getConnection();
					pstmt=conn.prepareStatement(SQL);
					pstmt.setInt(1, available);
					pstmt.setString(2, Nick);
					result=pstmt.executeUpdate();
					if(result==0)return false;
					
					return true;
				}catch(Exception ex){
					System.out.println("MsgAllDelete 에러 : "+ex);
				}finally{
					try{
						if(pstmt!=null)pstmt.close();
						if(conn!=null)conn.close();
					}catch(Exception ex) {}
				}
				
				return false;
			}
			
			//받은이 인지 확인
			public boolean isMsgReceiveNick(int num, String msgReceiveNick){
				
				String SQL="select msgReceiveNick from msg where msgNum=?";
				if(msgReceiveNick == null)
					return false;
				
				try{
					conn=ds.getConnection();
					pstmt=conn.prepareStatement(SQL);
					pstmt.setInt(1, num);
					rs=pstmt.executeQuery();
					rs.next();
					System.out.println("bbsNick="+msgReceiveNick +"쿼리값: " +rs.getString("msgReceiveNick"));
					if(msgReceiveNick.equals(rs.getString("msgReceiveNick"))){
						return true;
					}
				}catch(SQLException ex){
					System.out.println("isMsgReceiveNick 에러 : "+ex);
				}
				finally{
					try{
						if(pstmt!=null)pstmt.close();
						if(conn!=null)conn.close();
					}catch(Exception ex) {}
				}
				return false;
			}
	
			//휴지통에서 메세지 살림 업데이트
			public void setMsgLive(int num) throws Exception{
				String sql="update msg set msgAvailable = 1 where msgNum = "+num;
				
				try{
					conn=ds.getConnection();
					pstmt=conn.prepareStatement(sql);
					pstmt.executeUpdate();
				}catch(SQLException ex){
					System.out.println("setMsgLive 에러 : "+ex);
				}
				finally{
					try{
						if(pstmt!=null)pstmt.close();
						if(conn!=null)conn.close();
					}catch(Exception ex) {}
				}
			}
			
			//휴지통으로 보내기 업데이트
			public void setMsgSendTrashBoxUpdate(int num) throws Exception{
				String sql="update msg set msgAvailable = 2 where msgNum = "+num;
				
				try{
					conn=ds.getConnection();
					pstmt=conn.prepareStatement(sql);
					pstmt.executeUpdate();
				}catch(SQLException ex){
					System.out.println("setMsgSendTrashBoxUpdate 에러 : "+ex);
				}
				finally{
					try{
						if(pstmt!=null)pstmt.close();
						if(conn!=null)conn.close();
					}catch(Exception ex) {}
				}
			}
//	public ArrayList<MsgBean> getList(int pageNumber){ // 우편함을 불러올때
//		
//		String SQL = "SELECT * FROM MSG WHERE msgID < ? ORDER BY msgID DESC LIMIT 10";
//		ArrayList<MsgBean> list = new ArrayList<MsgBean>();
//		try {
//			
//			PreparedStatement pstmt = conn.prepareStatement(SQL);
//			pstmt.setInt(1, getNext() - (pageNumber -1)*10); // getNext()는 글의 다음 숫자 (pageNumber -1)*10는 현재 페이지가 1페이지 일 경우 0이 됨
//			// 현재 글의 수가 5개이면 getNext()는 6이 되고 위의 SQL 문에서 WHERE bbsID < ?를 적용해보면
//			// 1부터 5까지만 출력하는 것을 알 수 있다.
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				MsgBean msg = new MsgBean();
//				msg.setMsgID(rs.getInt(1));
//				msg.setMsgTitle(rs.getString(2));
//				msg.setUserID(rs.getString(3));
//				msg.setMsgDate(rs.getString(4));
//				msg.setMsgContent(rs.getString(5));
//				msg.setMsgAvailable(rs.getInt(6));
//				msg.setMsgReceiveID(rs.getString(7));
//				list.add(msg);
//			}
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return list; //게시글 입력시 list 반환
//		
//	}
//	
//	public boolean nextPage(int pageNumber) { // 다음 페이지를 적용 시킬때
//		
//		String SQL = "SELECT * FROM MSG WHERE msgID < ? AND bbsAvailable = 1";
//		try {
//			
//			PreparedStatement pstmt = conn.prepareStatement(SQL);
//			pstmt.setInt(1, getNext() - (pageNumber -1)*10); // getNext()는 글의 다음 숫자 (pageNumber -1)*10는 현재 페이지가 1페이지 일 경우 0이 됨
//			// 현재 글의 수가 5개이면 getNext()는 6이 되고 위의 SQL 문에서 WHERE bbsID < ?를 적용해보면
//			// 1부터 5까지만 출력하는 것을 알 수 있다.
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				return true; // 다음 페이지가 있을 경우 true 반환
//			}
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return false; // 다음 페이지가 없을 경우 false 반환
//		
//	}
//	
//	public MsgBean getMsg(int msgID) { // 우편함 해당 글 보기
//		
//		String SQL = "SELECT * FROM MSG WHERE msgID = ?";
//		try {
//			
//			PreparedStatement pstmt = conn.prepareStatement(SQL);
//			pstmt.setInt(1, msgID); 
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				MsgBean msg = new MsgBean();
//				msg.setMsgID(rs.getInt(1));
//				msg.setMsgTitle(rs.getString(2));
//				msg.setUserID(rs.getString(3));
//				msg.setMsgDate(rs.getString(4));
//				msg.setMsgContent(rs.getString(5));
//				msg.setMsgAvailable(rs.getInt(6));
//				msg.setMsgReceiveID(rs.getString(7));
//				return msg; // public Bbs에 적용됨 Bbs bbs = new Bbs();를 만들었고 값을 넣은 bbs를 return으로 반환한다.
//				// 해당글이 있을 경우 bbs를 반환
//			}
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return null; // 해당 글이 없을 경우 null을 반환
//		
//	}
//	
//	public int delete(int msgID) { // 쪽지 글 휴지통으로 보낼 때
//		String SQL = "UPDATE MSG SET msgAvailable = 0 WHERE msgID = ?";
//		try {
//			
//			PreparedStatement pstmt = conn.prepareStatement(SQL);
//			pstmt.setInt(1, msgID);
//			return pstmt.executeUpdate();
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return -1; // 해당 글이 업데이트가 안될 경우 -1을 반환
//	}
//	
//	public int deletereal(int msgID) { // 쪽지 글 휴지통에서 삭제 할 때
//		String SQL = "DELETE FROM MSG WHERE msgID = ? AND msgAvailable = 0";
//		try {
//			
//			PreparedStatement pstmt = conn.prepareStatement(SQL);
//			pstmt.setInt(1, msgID);
//			pstmt.executeUpdate();
//			return 0;
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return -1; // 해당 글이 업데이트가 안될 경우 -1을 반환
//	}
//	
//	
//	public int view(int msgID) { // 쪽지 글 읽은함에 보낼 때
//		String SQL = "UPDATE MSG SET msgAvailable = 2 WHERE msgID = ?";
//		try {
//			
//			PreparedStatement pstmt = conn.prepareStatement(SQL);
//			pstmt.setInt(1, msgID);
//			return pstmt.executeUpdate();
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return -1; // 해당 글이 업데이트가 안될 경우 -1을 반환
//	}
	
	

}
