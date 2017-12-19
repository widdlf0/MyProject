package notice.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class NoticeDAO {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public NoticeDAO() {
		try {
	         Context init = new InitialContext();
	         ds = (DataSource) init.lookup("java:comp/env/jdbc/mysql");
	      } catch (Exception ex) {
	         System.out.println("DB 연결 실패 : " + ex);
	         return;
	      }
	}
	
	//글 등록
		public boolean NoticeInsert(NoticeBean notice){
			int num =0;
			String sql="";
			
			int result=0;
			
			try{
				conn = ds.getConnection();
				pstmt=conn.prepareStatement(
						"select max(NoticeNum) from notice");
				rs = pstmt.executeQuery();
				
				if(rs.next())
					num =rs.getInt(1)+1;
				else
					num=1;
				
				sql="insert into notice (NoticeNum,NoticeNick,NoticeSubject,";
				sql+="NoticeContent, NoticeFile,NoticeReRef,"+
					"NoticeReLev,NoticeReSeq,NoticeReadcount,"+
					"NoticeDate) values(?,?,?,?,?,?,?,?,?,NOW())";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, notice.getNoticeNick());
				pstmt.setString(3, notice.getNoticeSubject());
				pstmt.setString(4, notice.getNoticeContent());
				pstmt.setString(5, notice.getNoticeFile());
				pstmt.setInt(6, num);
				pstmt.setInt(7, 0);
				pstmt.setInt(8, 0);
				pstmt.setInt(9, 0);
				
				result=pstmt.executeUpdate();
				if(result==0)return false;
				
				return true;
			}catch(Exception ex){
				System.out.println("bbsInsert 에러 : "+ex);
			}finally{
				if(rs!=null) try{rs.close();}catch(SQLException ex){}
				if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
				if(conn!=null) try{conn.close();}catch(SQLException ex){}
			}
			return false;
		}
	
		//글목록 가져오기
		public ArrayList<NoticeBean> getNoticeList(HashMap<String, Object> listOpt)
		{
		    ArrayList<NoticeBean> list = new ArrayList<NoticeBean>();
		    
		    String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
		    String condition = (String)listOpt.get("condition"); // 검색내용
		    int start = (Integer)listOpt.get("start"); // 현재 페이지번호
		    int noticeviewcount = (Integer)listOpt.get("noticeviewcount");
		    
		    try {
		    	conn=ds.getConnection();
		        
		    	if(noticeviewcount == 10) {
			        // 글목록 전체를 보여줄 때
			        if(opt == null)
			        	//검색조건은 처음에는 설정되지 않으므로 null이다
			        {
			            // BOARD_RE_REF(그룹번호)의 내림차순 정렬 후 동일한 그룹번호일 때는
			            // BOARD_RE_SEQ(답변글 순서)의 오름차순으로 정렬 한 후에
			            // 10개의 글을 한 화면에 보여주는(start번째 부터 start+9까지) 쿼리
			            // desc : 내림차순, asc : 오름차순 ( 생략 가능 )
			        	String bbs_list_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp order by rnum desc limit ?,?";
			        	//mysql에는 rownum기능이 없다 그래서 이렇게 나열해준 것인데
			        	//rownum변수를 생성해서 계속 +1씩 더해준다
			        	//그리고 tmp라는 별칭을 만들어서 초기화해준다
			        	//그뒤에 내림차순으로 ?,?처음부터 끝까지 나열해준다
			        	
			        	pstmt = conn.prepareStatement(bbs_list_sql);
			            pstmt.setInt(1, start-1);
			            pstmt.setInt(2, 10);
			           
			        }
			        else if(opt.equals("0")) // 제목으로 검색
			        {
			        	String bbs_subjectserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 10);
			            
			        }
			        else if(opt.equals("1")) // 내용으로 검색
			        {
			        	String bbs_contentserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeContent like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_contentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 10);
			            
			        }
			        else if(opt.equals("2")) // 제목+내용으로 검색
			        {
			        	String bbs_subjectcontentserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeContent like ? or NoticeSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectcontentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setString(2, "%"+condition+"%");
			        	pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			           
			        }
			        else if(opt.equals("3")) // 글쓴이로 검색
			        {
			        	String bbs_idserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeNick like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_idserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 10);
			            
			        }
		    	}else if(noticeviewcount == 20) {
		    		 // 글목록 전체를 보여줄 때
			        if(opt == null)
			        {
			        	String bbs_list_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp order by rnum desc limit ?,?";
			        	
			        	pstmt = conn.prepareStatement(bbs_list_sql);
			            pstmt.setInt(1, start-1);
			            pstmt.setInt(2, 20);
			           
			        }
			        else if(opt.equals("0")) // 제목으로 검색
			        {
			        	String bbs_subjectserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 20);
			            
			        }
			        else if(opt.equals("1")) // 내용으로 검색
			        {
			        	String bbs_contentserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeContent like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_contentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 20);
			            
			        }
			        else if(opt.equals("2")) // 제목+내용으로 검색
			        {
			        	String bbs_subjectcontentserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeContent like ? or NoticeSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectcontentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setString(2, "%"+condition+"%");
			        	pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 20);
			           
			        }
			        else if(opt.equals("3")) // 글쓴이로 검색
			        {
			        	String bbs_idserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeNick like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_idserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 20);
			            
			        }
		    	}else if(noticeviewcount == 50) {
		    		 // 글목록 전체를 보여줄 때
			        if(opt == null)
			        {
			        	String bbs_list_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp order by rnum desc limit ?,?";
			        	
			        	pstmt = conn.prepareStatement(bbs_list_sql);
			            pstmt.setInt(1, start-1);
			            pstmt.setInt(2, 50);
			           
			        }
			        else if(opt.equals("0")) // 제목으로 검색
			        {
			        	String bbs_subjectserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 50);
			            
			        }
			        else if(opt.equals("1")) // 내용으로 검색
			        {
			        	String bbs_contentserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeContent like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_contentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 50);
			            
			        }
			        else if(opt.equals("2")) // 제목+내용으로 검색
			        {
			        	String bbs_subjectcontentserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeContent like ? or NoticeSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectcontentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setString(2, "%"+condition+"%");
			        	pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 50);
			           
			        }
			        else if(opt.equals("3")) // 글쓴이로 검색
			        {
			        	String bbs_idserch_sql="select @rownum:=@rownum+1 rnum, NoticeNum, NoticeNick, NoticeSubject, NoticeContent,"+
			        			"NoticeFile, NoticeReRef, NoticeReLev, NoticeReSeq, NoticeReadcount, NoticeDate from notice,"+
			        			"(select @rownum:=0)tmp where NoticeNick like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_idserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 50);
			            
			        }
		    	}
		        
		        rs = pstmt.executeQuery();
		        while(rs.next())
		        {
		        	NoticeBean noticeBean = new NoticeBean();
		        	noticeBean.setNoticeNum(rs.getInt("NoticeNum"));
		        	noticeBean.setNoticeNick(rs.getString("NoticeNick"));
		        	noticeBean.setNoticeSubject(rs.getString("NoticeSubject"));
		        	noticeBean.setNoticeContent(rs.getString("NoticeContent"));
		        	noticeBean.setNoticeFile(rs.getString("NoticeFile"));
		        	noticeBean.setNoticeReRef(rs.getInt("NoticeReRef"));
		        	noticeBean.setNoticeReLev(rs.getInt("NoticeReLev"));
		        	noticeBean.setNoticeReSeq(rs.getInt("NoticeReSeq"));
		        	noticeBean.setNoticeReadcount(rs.getInt("NoticeReadcount"));
		        	noticeBean.setNoticeDate(rs.getDate("NoticeDate"));
		        	list.add(noticeBean);
		        	//등록이 된 데이터를 가져오는 것이다
		        
		        }
		        
		        return list;
		        //등록된 데이터를 돌려준다
			}catch(Exception ex){
				System.out.println("글목록 가져오기 에러 : " + ex);
			}finally{
				if(rs!=null) try{rs.close();}catch(SQLException ex){}
				if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
				if(conn!=null) try{conn.close();}catch(SQLException ex){}
			}
			return null;
		}
	
		//글 개수 구하기
		public int getNoticeListCount(HashMap<String, Object> listOpt)
	    {
			//여기서 전체글의 개수와
			//각 검색마다 글의 개수를 알려준다
	        int result = 0;
	        String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
	        String condition = (String)listOpt.get("condition"); // 검색내용
	        
	        try {
	        	conn=ds.getConnection();
	            StringBuffer sql = new StringBuffer();
	            
	            if(opt == null)    // 전체글의 개수
	            {
	                sql.append("select count(*) from notice");
	                pstmt = conn.prepareStatement(sql.toString());
	                
	                // StringBuffer를 비운다.
	                sql.delete(0, sql.toString().length());
	            }
	            else if(opt.equals("0")) // 제목으로 검색한 글의 개수
	            {
	                sql.append("select count(*) from notice where NoticeSubject like ?");
	                pstmt = conn.prepareStatement(sql.toString());
	                pstmt.setString(1, '%'+condition+'%');
	                
	                sql.delete(0, sql.toString().length());
	            }
	            else if(opt.equals("1")) // 내용으로 검색한 글의 개수
	            {
	                sql.append("select count(*) from notice where NoticeContent like ?");
	                pstmt = conn.prepareStatement(sql.toString());
	                pstmt.setString(1, '%'+condition+'%');
	                
	                sql.delete(0, sql.toString().length());
	            }
	            else if(opt.equals("2")) // 제목+내용으로 검색한 글의 개수
	            {
	                sql.append("select count(*) from notice ");
	                sql.append("where NoticeSubject like ? or NoticeContent like ?");
	                pstmt = conn.prepareStatement(sql.toString());
	                pstmt.setString(1, '%'+condition+'%');
	                pstmt.setString(2, '%'+condition+'%');
	                
	                sql.delete(0, sql.toString().length());
	            }
	            else if(opt.equals("3")) // 글쓴이로 검색한 글의 개수
	            {
	                sql.append("select count(*) from notice where NoticeNick like ?");
	                pstmt = conn.prepareStatement(sql.toString());
	                pstmt.setString(1, '%'+condition+'%');
	                
	                sql.delete(0, sql.toString().length());
	            }
	            
	            rs = pstmt.executeQuery();
	            if(rs.next())    result = rs.getInt(1);
	            
	        } catch(Exception ex){
				System.out.println("getListCount 에러 : " + ex);			
			}finally{
				if(rs!=null) try{rs.close();}catch(SQLException ex){}
				if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
				if(conn!=null) try{conn.close();}catch(SQLException ex){}
			}
	        
	        return result;
	    }
		
		//글 내용 보기
		public NoticeBean getNoticeDetail(int num) throws Exception{
			NoticeBean noticeBean = null;
			try{
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(
						"select * from notice where NoticeNum = ?");
				pstmt.setInt(1, num);
				
				rs= pstmt.executeQuery();
				
				if(rs.next()){
					noticeBean = new NoticeBean();
					noticeBean.setNoticeNum(rs.getInt("NoticeNum"));
					noticeBean.setNoticeNick(rs.getString("NoticeNick"));
					noticeBean.setNoticeSubject(rs.getString("NoticeSubject"));
					noticeBean.setNoticeContent(rs.getString("NoticeContent"));
					noticeBean.setNoticeFile(rs.getString("NoticeFile"));
					noticeBean.setNoticeReRef(rs.getInt("NoticeReRef"));
					noticeBean.setNoticeReLev(rs.getInt("NoticeReLev"));
					noticeBean.setNoticeReSeq(rs.getInt("NoticeReSeq"));
					noticeBean.setNoticeReadcount(rs.getInt("NoticeReadcount"));
					noticeBean.setNoticeDate(rs.getDate("NoticeDate"));
					
				}
				return noticeBean;
			}catch(Exception ex){
				System.out.println("getDetail 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
			return null;
		}
		
		//조회수 업데이트
		public void setReadCountUpdate(int num, String Nick) throws Exception{

			String sql1 = "select *from noticereadcount where noticenum like ? and usernick like ?";
			
			String sql2 = "insert into noticereadcount values(?,?,NOW())";
			
			String sql3="update notice set noticereadcount = "+
				"noticereadcount+1 where noticeNum = "+num;
			System.out.println("===1===");
			try{
				System.out.println("===1.1===");
					conn=ds.getConnection();
					
					pstmt=conn.prepareStatement(sql1);
					pstmt.setInt(1, num);
					pstmt.setString(2, Nick);
					rs = pstmt.executeQuery();
					System.out.println("===1.2===");
					if(rs.next()) {
						if(!(rs.getInt(1) == num && rs.getString(2).equals(Nick))) {
							System.out.println("===1.3===");
							pstmt=conn.prepareStatement(sql2);
							pstmt.setInt(1, num);
							pstmt.setString(2, Nick);
							pstmt.executeUpdate();
							
							pstmt=conn.prepareStatement(sql3);
							pstmt.executeUpdate();
							System.out.println("===1.4===");
						}
					
					}else {
						System.out.println("===1.5===");
						pstmt=conn.prepareStatement(sql2);
						pstmt.setInt(1, num);
						pstmt.setString(2, Nick);
						pstmt.executeUpdate();
						
						pstmt=conn.prepareStatement(sql3);
						pstmt.executeUpdate();
						System.out.println("===1.6===");
					}
				
			}catch(SQLException ex){
				System.out.println("setReadCountUpdate 에러 : "+ex);
			}
			finally{
				try{
					if(pstmt!=null)pstmt.close();
					if(conn!=null)conn.close();
				}catch(Exception ex) {}
			}
		}
		
		//글쓴이 인지 확인
		public boolean isNoticeWriter(int num, String NoticeNick){
			
			String SQL="select NoticeNick from notice where NoticeNum=?";
			if(NoticeNick == null)
				return false;
			
			try{
				conn=ds.getConnection();
				pstmt=conn.prepareStatement(SQL);
				pstmt.setInt(1, num);
				rs=pstmt.executeQuery();
				rs.next();
				System.out.println("NoticeNick="+NoticeNick +"쿼리값: " +rs.getString("NoticeNick"));
				if(NoticeNick.equals(rs.getString("NoticeNick"))){
					return true;
				}
			}catch(SQLException ex){
				System.out.println("isBoardWriter 에러 : "+ex);
			}
			finally{
				try{
					if(pstmt!=null)pstmt.close();
					if(conn!=null)conn.close();
				}catch(Exception ex) {}
			}
			return false;
		}
//		
//		//글 내용 보기
//		public BbsBean getDetail(int num) throws Exception{
//			BbsBean bbsBean = null;
//			try{
//				conn = ds.getConnection();
//				pstmt = conn.prepareStatement(
//						"select * from bbs where Bbsnum = ?");
//				pstmt.setInt(1, num);
//				
//				rs= pstmt.executeQuery();
//				
//				if(rs.next()){
//					bbsBean = new BbsBean();
//					
//					bbsBean.setBbsNum(rs.getInt("BbsNum"));
//					bbsBean.setBbsNick(rs.getString("BbsNick"));
//					bbsBean.setBbsSubject(rs.getString("BbsSubject"));
//					bbsBean.setBbsContent(rs.getString("BbsContent"));
//					bbsBean.setBbsFile(rs.getString("BbsFile"));
//					bbsBean.setBbsReRef(rs.getInt("BbsReRef"));
//					bbsBean.setBbsReLev(rs.getInt("BbsReLev"));
//					bbsBean.setBbsReSeq(rs.getInt("BbsReSeq"));
//					bbsBean.setBbsReadcount(rs.getInt("BbsReadcount"));
//					bbsBean.setBbsDate(rs.getDate("BbsDate"));
//					
//				}
//				return bbsBean;
//			}catch(Exception ex){
//				System.out.println("getDetail 에러 : " + ex);
//			}finally{
//				if(rs!=null)try{rs.close();}catch(SQLException ex){}
//				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
//				if(conn !=null)try{conn.close();}catch(SQLException ex){}
//			}
//			return null;
//		}
//		
		//글 수정
		public boolean NoticeModify(NoticeBean modifynotice) throws Exception{
			String sql="update notice set NoticeSubject=?," +
								"NoticeContent=?, NoticeFile=? where NoticeNum=?";
			
			try{
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, modifynotice.getNoticeSubject());
				pstmt.setString(2, modifynotice.getNoticeContent());
				pstmt.setString(3, modifynotice.getNoticeFile());
				pstmt.setInt(4, modifynotice.getNoticeNum());
				pstmt.executeUpdate();
				return true;
			}catch(Exception ex){
				System.out.println("NoticeModify 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn!=null)try{conn.close();}catch(SQLException ex){}
				}
			return false;
		}
//		
		//글 삭제
		public boolean NoticeDelete(int num){
			String SQL="delete from notice where NoticeNum=?";
			
			int result=0;
			
			try{
				conn = ds.getConnection();
				pstmt=conn.prepareStatement(SQL);
				pstmt.setInt(1, num);
				result=pstmt.executeUpdate();
				if(result==0)return false;
				
				return true;
			}catch(Exception ex){
				System.out.println("NoticeDelete 에러 : "+ex);
			}finally{
				try{
					if(pstmt!=null)pstmt.close();
					if(conn!=null)conn.close();
				}catch(Exception ex) {}
			}
			
			return false;
		}
//		
//		public void BbsInsertAnswer(int bbsNum, String AnswerNick, String AnswerContent){
//			String sql = "insert into bbsanswer (BbsAnswerNum, BbsNumCheck, BbsNickCheck, "
//					+ "BbsAnswerContent, BbsAnswerAgree, BbsAnswerNotAgree, Date) values(?,?,?,?,?,?,NOW()) ";
//			int num;
//			try {
//				
//				conn = ds.getConnection();
//				pstmt=conn.prepareStatement(
//						"select max(BbsAnswerNum) from bbsanswer");
//				rs = pstmt.executeQuery();
//				
//				if(rs.next())
//					num =rs.getInt(1)+1;
//				else
//					num=1;
//				pstmt = conn.prepareStatement(sql);
//				pstmt.setInt(1, num);
//				pstmt.setInt(2, bbsNum);
//				pstmt.setString(3, AnswerNick);
//				pstmt.setString(4, AnswerContent);
//				pstmt.setInt(5, 0);
//				pstmt.setInt(6, 0);
//				pstmt.executeUpdate();
//				
//			}catch(Exception ex){
//				System.out.println("BbsInsertAnswer 에러 : " + ex);
//			}finally{
//				if(rs!=null)try{rs.close();}catch(SQLException ex){}
//				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
//				if(conn !=null)try{conn.close();}catch(SQLException ex){}
//			}
//		}
//		
//		public ArrayList<BbsBean> getBbsAnswerDetail(int bbsNum) {
//			String sql = "select *from bbsanswer where BbsNumCheck = ?";
//			ArrayList<BbsBean> bbsanswerlist = new ArrayList<BbsBean>();
//			try {
//				conn = ds.getConnection();
//				pstmt = conn.prepareStatement(sql);
//				pstmt.setInt(1, bbsNum);
//				rs = pstmt.executeQuery();
//				
//				while(rs.next()) {
//					
//					BbsBean bbsBean = new BbsBean();
//					bbsBean.setBbsAnswerNum(rs.getInt("BbsAnswerNum"));
//					bbsBean.setBbsNickCheck(rs.getString("BbsNickCheck"));
//					bbsBean.setBbsAnswerContent(rs.getString("BbsAnswerContent"));
//					bbsBean.setAnswerDate(rs.getString("Date").substring(0,19));
//					bbsBean.setBbsAnswerAgree(rs.getInt("BbsAnswerAgree"));
//					bbsBean.setBbsAnswerNotAgree(rs.getInt("BbsAnswerNotAgree"));
//					
//					bbsanswerlist.add(bbsBean);
//				}
//				
//			}catch(Exception ex){
//				System.out.println("getBbsAnswerDetail 에러 : " + ex);
//			}finally{
//				if(rs!=null)try{rs.close();}catch(SQLException ex){}
//				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
//				if(conn !=null)try{conn.close();}catch(SQLException ex){}
//			}
//			
//			return bbsanswerlist;
//		}
//		
//		public int getAnswerCount(int Num) {
//			String sql = "select count(*) from bbsanswer where BbsNumCheck = ?";
//			int count = 0;
//			try {
//				
//				conn = ds.getConnection();
//				pstmt = conn.prepareStatement(sql);
//				pstmt.setInt(1, bbsNum);
//				rs = pstmt.executeQuery();
//				
//				if(rs.next())
//					count = rs.getInt(1);
//				
//				
//			}catch(Exception ex){
//				System.out.println("getAnswerCount 에러 : " + ex);
//			}finally{
//				if(rs!=null)try{rs.close();}catch(SQLException ex){}
//				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
//				if(conn !=null)try{conn.close();}catch(SQLException ex){}
//			}
//			return count;
//		}
}
