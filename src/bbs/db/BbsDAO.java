package bbs.db;

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


public class BbsDAO {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public BbsDAO() {
		try {
	         Context init = new InitialContext();
	         ds = (DataSource) init.lookup("java:comp/env/jdbc/mysql");
	      } catch (Exception ex) {
	         System.out.println("DB 연결 실패 : " + ex);
	         return;
	      }
	}
	
	//글 등록
		public boolean BbsInsert(BbsBean bbs){
			int num =0;
			String sql="";
			
			int result=0;
			
			try{
				conn = ds.getConnection();
				pstmt=conn.prepareStatement(
						"select max(BbsNum) from bbs");
				rs = pstmt.executeQuery();
				
				if(rs.next())
					num =rs.getInt(1)+1;
				else
					num=1;
				
				sql="insert into bbs (BbsNum,BbsNick,BbsSubject,";
				sql+="BbsContent, BbsFile,BbsReRef,"+
					"BbsReLev,BbsReSeq,BbsReadcount,"+
					"BbsDate) values(?,?,?,?,?,?,?,?,?,NOW())";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, bbs.getBbsNick());
				pstmt.setString(3, bbs.getBbsSubject());
				pstmt.setString(4, bbs.getBbsContent());
				pstmt.setString(5, bbs.getBbsFile());
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
		public ArrayList<BbsBean> getBbsList(HashMap<String, Object> listOpt)
		{
		    ArrayList<BbsBean> list = new ArrayList<BbsBean>();
		    
		    String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
		    String condition = (String)listOpt.get("condition"); // 검색내용
		    int start = (Integer)listOpt.get("start"); // 현재 페이지번호
		    int bbsviewcount = (Integer)listOpt.get("bbsviewcount");
		    
		    try {
		    	conn=ds.getConnection();
		        
		    	if(bbsviewcount == 10) {
			        // 글목록 전체를 보여줄 때
			        if(opt == null)
			        {
			            // BOARD_RE_REF(그룹번호)의 내림차순 정렬 후 동일한 그룹번호일 때는
			            // BOARD_RE_SEQ(답변글 순서)의 오름차순으로 정렬 한 후에
			            // 10개의 글을 한 화면에 보여주는(start번째 부터 start+9까지) 쿼리
			            // desc : 내림차순, asc : 오름차순 ( 생략 가능 )
			        	String bbs_list_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp order by rnum desc limit ?,?";
			        	
			        	pstmt = conn.prepareStatement(bbs_list_sql);
			            pstmt.setInt(1, start-1);
			            pstmt.setInt(2, 10);
			           
			        }
			        else if(opt.equals("0")) // 제목으로 검색
			        {
			        	String bbs_subjectserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 10);
			            
			        }
			        else if(opt.equals("1")) // 내용으로 검색
			        {
			        	String bbs_contentserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsContent like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_contentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 10);
			            
			        }
			        else if(opt.equals("2")) // 제목+내용으로 검색
			        {
			        	String bbs_subjectcontentserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsContent like ? or BbsSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectcontentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setString(2, "%"+condition+"%");
			        	pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 10);
			           
			        }
			        else if(opt.equals("3")) // 글쓴이로 검색
			        {
			        	String bbs_idserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsNick like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_idserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 10);
			            
			        }
		    	}else if(bbsviewcount == 20) {
		    		 // 글목록 전체를 보여줄 때
			        if(opt == null)
			        {
			        	String bbs_list_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp order by rnum desc limit ?,?";
			        	
			        	pstmt = conn.prepareStatement(bbs_list_sql);
			            pstmt.setInt(1, start-1);
			            pstmt.setInt(2, 20);
			           
			        }
			        else if(opt.equals("0")) // 제목으로 검색
			        {
			        	String bbs_subjectserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 20);
			            
			        }
			        else if(opt.equals("1")) // 내용으로 검색
			        {
			        	String bbs_contentserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsContent like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_contentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 20);
			            
			        }
			        else if(opt.equals("2")) // 제목+내용으로 검색
			        {
			        	String bbs_subjectcontentserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsContent like ? or BbsSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectcontentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setString(2, "%"+condition+"%");
			        	pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 20);
			           
			        }
			        else if(opt.equals("3")) // 글쓴이로 검색
			        {
			        	String bbs_idserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsNick like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_idserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 20);
			            
			        }
		    	}else if(bbsviewcount == 50) {
		    		 // 글목록 전체를 보여줄 때
			        if(opt == null)
			        {
			        	String bbs_list_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp order by rnum desc limit ?,?";
			        	
			        	pstmt = conn.prepareStatement(bbs_list_sql);
			            pstmt.setInt(1, start-1);
			            pstmt.setInt(2, 50);
			           
			        }
			        else if(opt.equals("0")) // 제목으로 검색
			        {
			        	String bbs_subjectserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 50);
			            
			        }
			        else if(opt.equals("1")) // 내용으로 검색
			        {
			        	String bbs_contentserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsContent like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_contentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 50);
			            
			        }
			        else if(opt.equals("2")) // 제목+내용으로 검색
			        {
			        	String bbs_subjectcontentserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsContent like ? or BbsSubject like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_subjectcontentserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			            pstmt.setString(2, "%"+condition+"%");
			        	pstmt.setInt(3, start-1);
			            pstmt.setInt(4, 50);
			           
			        }
			        else if(opt.equals("3")) // 글쓴이로 검색
			        {
			        	String bbs_idserch_sql="select @rownum:=@rownum+1 rnum, BbsNum, BbsNick, BbsSubject, BbsContent,"+
			        			"BbsFile, BbsReRef, BbsReLev, BbsReSeq, BbsReadcount, BbsDate from bbs,"+
			        			"(select @rownum:=0)tmp where BbsNick like ? order by rnum desc limit ?,?";
			            
			        	pstmt = conn.prepareStatement(bbs_idserch_sql);
			            pstmt.setString(1, "%"+condition+"%");
			        	pstmt.setInt(2, start-1);
			            pstmt.setInt(3, 50);
			            
			        }
		    	}
		        
		        rs = pstmt.executeQuery();
		        while(rs.next())
		        {
		        	BbsBean bbsBean = new BbsBean();
		        	bbsBean.setBbsNum(rs.getInt("BbsNum"));
		        	bbsBean.setBbsNick(rs.getString("BbsNick"));
		        	bbsBean.setBbsSubject(rs.getString("BbsSubject"));
		        	bbsBean.setBbsContent(rs.getString("BbsContent"));
		        	bbsBean.setBbsFile(rs.getString("BbsFile"));
		        	bbsBean.setBbsReRef(rs.getInt("BbsReRef"));
		        	bbsBean.setBbsReLev(rs.getInt("BbsReLev"));
		        	bbsBean.setBbsReSeq(rs.getInt("BbsReSeq"));
		        	bbsBean.setBbsReadcount(rs.getInt("BbsReadcount"));
		        	bbsBean.setBbsDate(rs.getDate("BbsDate"));
		        	list.add(bbsBean);
		        
		        }
		        
		        return list;
			}catch(Exception ex){
				System.out.println("getBoardList 에러 : " + ex);
			}finally{
				if(rs!=null) try{rs.close();}catch(SQLException ex){}
				if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
				if(conn!=null) try{conn.close();}catch(SQLException ex){}
			}
			return null;
		}
	
		//글 개수 구하기
		public int getBbsListCount(HashMap<String, Object> listOpt)
	    {
	        int result = 0;
	        String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
	        String condition = (String)listOpt.get("condition"); // 검색내용
	        
	        try {
	        	conn=ds.getConnection();
	            StringBuffer sql = new StringBuffer();
	            
	            if(opt == null)    // 전체글의 개수
	            {
	                sql.append("select count(*) from bbs");
	                pstmt = conn.prepareStatement(sql.toString());
	                
	                // StringBuffer를 비운다.
	                sql.delete(0, sql.toString().length());
	            }
	            else if(opt.equals("0")) // 제목으로 검색한 글의 개수
	            {
	                sql.append("select count(*) from bbs where BbsSubject like ?");
	                pstmt = conn.prepareStatement(sql.toString());
	                pstmt.setString(1, '%'+condition+'%');
	                
	                sql.delete(0, sql.toString().length());
	            }
	            else if(opt.equals("1")) // 내용으로 검색한 글의 개수
	            {
	                sql.append("select count(*) from bbs where BbsContent like ?");
	                pstmt = conn.prepareStatement(sql.toString());
	                pstmt.setString(1, '%'+condition+'%');
	                
	                sql.delete(0, sql.toString().length());
	            }
	            else if(opt.equals("2")) // 제목+내용으로 검색한 글의 개수
	            {
	                sql.append("select count(*) from bbs ");
	                sql.append("where BbsSubject like ? or BbsContent like ?");
	                pstmt = conn.prepareStatement(sql.toString());
	                pstmt.setString(1, '%'+condition+'%');
	                pstmt.setString(2, '%'+condition+'%');
	                
	                sql.delete(0, sql.toString().length());
	            }
	            else if(opt.equals("3")) // 글쓴이로 검색한 글의 개수
	            {
	                sql.append("select count(*) from bbs where BbsNick like ?");
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
		public BbsBean getBbsDetail(int num) throws Exception{
			BbsBean bbsBean = null;
			try{
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(
						"select * from bbs where BbsNum = ?");
				pstmt.setInt(1, num);
				
				rs= pstmt.executeQuery();
				
				if(rs.next()){
					bbsBean = new BbsBean();
					bbsBean.setBbsNum(rs.getInt("BbsNum"));
		        	bbsBean.setBbsNick(rs.getString("BbsNick"));
		        	bbsBean.setBbsSubject(rs.getString("BbsSubject"));
		        	bbsBean.setBbsContent(rs.getString("BbsContent"));
		        	bbsBean.setBbsFile(rs.getString("BbsFile"));
		        	bbsBean.setBbsReRef(rs.getInt("BbsReRef"));
		        	bbsBean.setBbsReLev(rs.getInt("BbsReLev"));
		        	bbsBean.setBbsReSeq(rs.getInt("BbsReSeq"));
		        	bbsBean.setBbsReadcount(rs.getInt("BbsReadcount"));
		        	bbsBean.setBbsDate(rs.getDate("BbsDate"));
					
				}
				return bbsBean;
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

			String sql1 = "select *from bbsreadcount where bbsnum like ? and usernick like ?";
			
			String sql2 = "insert into bbsreadcount values(?,?,NOW())";
			
			String sql3="update bbs set BbsReadcount = "+
				"BbsReadcount+1 where BbsNum = "+num;
			
			try{
				
					conn=ds.getConnection();
					
					pstmt=conn.prepareStatement(sql1);
					pstmt.setInt(1, num);
					pstmt.setString(2, Nick);
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
						if(!(rs.getInt(1) == num && rs.getString(2).equals(Nick))) {
							
							pstmt=conn.prepareStatement(sql2);
							pstmt.setInt(1, num);
							pstmt.setString(2, Nick);
							pstmt.executeUpdate();
							
							pstmt=conn.prepareStatement(sql3);
							pstmt.executeUpdate();
							
						}
					
					}else {
						
						pstmt=conn.prepareStatement(sql2);
						pstmt.setInt(1, num);
						pstmt.setString(2, Nick);
						pstmt.executeUpdate();
						
						pstmt=conn.prepareStatement(sql3);
						pstmt.executeUpdate();
						
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
		public boolean isBbsWriter(int num, String bbsNick){
			
			String SQL="select BbsNick from bbs where BbsNum=?";
			if(bbsNick == null)
				return false;
			
			try{
				conn=ds.getConnection();
				pstmt=conn.prepareStatement(SQL);
				pstmt.setInt(1, num);
				rs=pstmt.executeQuery();
				
				if(rs.next()) {
					if(bbsNick.equals(rs.getString("BbsNick"))){
						return true;
					}
				}
				
			}catch(SQLException ex){
				System.out.println("isBbsWriter 에러 : "+ex);
			}
			finally{
				try{
					if(pstmt!=null)pstmt.close();
					if(conn!=null)conn.close();
				}catch(Exception ex) {}
			}
			return false;
		}
		
		//글 내용 보기
		public BbsBean getDetail(int num) throws Exception{
			BbsBean bbsBean = null;
			try{
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(
						"select * from bbs where Bbsnum = ?");
				pstmt.setInt(1, num);
				
				rs= pstmt.executeQuery();
				
				if(rs.next()){
					bbsBean = new BbsBean();
					
					bbsBean.setBbsNum(rs.getInt("BbsNum"));
					bbsBean.setBbsNick(rs.getString("BbsNick"));
					bbsBean.setBbsSubject(rs.getString("BbsSubject"));
					bbsBean.setBbsContent(rs.getString("BbsContent"));
					bbsBean.setBbsFile(rs.getString("BbsFile"));
					bbsBean.setBbsReRef(rs.getInt("BbsReRef"));
					bbsBean.setBbsReLev(rs.getInt("BbsReLev"));
					bbsBean.setBbsReSeq(rs.getInt("BbsReSeq"));
					bbsBean.setBbsReadcount(rs.getInt("BbsReadcount"));
					bbsBean.setBbsDate(rs.getDate("BbsDate"));
					
				}
				return bbsBean;
			}catch(Exception ex){
				System.out.println("getDetail 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
			return null;
		}
		
		//글 수정
		public boolean BbsModify(BbsBean modifybbs) throws Exception{
			String sql="update bbs set BbsSubject=?," +
								"BbsContent=?, BbsFile=? where BbsNum=?";
			
			try{
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, modifybbs.getBbsSubject());
				pstmt.setString(2, modifybbs.getBbsContent());
				pstmt.setString(3, modifybbs.getBbsFile());
				pstmt.setInt(4, modifybbs.getBbsNum());
				pstmt.executeUpdate();
				return true;
			}catch(Exception ex){
				System.out.println("bbsModify 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn!=null)try{conn.close();}catch(SQLException ex){}
				}
			return false;
		}
		
		//글 삭제
		public boolean BbsDelete(int num){
			String SQL="delete from bbs where BbsNum=?";
			
			int result=0;
			
			try{
				conn = ds.getConnection();
				pstmt=conn.prepareStatement(SQL);
				pstmt.setInt(1, num);
				result=pstmt.executeUpdate();
				if(result==0)return false;
				
				return true;
			}catch(Exception ex){
				System.out.println("bbsDelete 에러 : "+ex);
			}finally{
				try{
					if(pstmt!=null)pstmt.close();
					if(conn!=null)conn.close();
				}catch(Exception ex) {}
			}
			
			return false;
		}
		
		public void BbsInsertAnswer(int bbsNum, String AnswerNick, String AnswerContent){
			String sql = "insert into bbsanswer (BbsAnswerNum, BbsNumCheck, BbsNickCheck, "
					+ "BbsAnswerContent, BbsAnswerAgree, BbsAnswerNotAgree, Date, ReplyNum) values(?,?,?,?,?,?,NOW(),?) ";
			int num;
			try {
				
				conn = ds.getConnection();
				pstmt=conn.prepareStatement(
						"select max(BbsAnswerNum) from bbsanswer");
				rs = pstmt.executeQuery();
				
				if(rs.next())
					num =rs.getInt(1)+1;
				else
					num=1;
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setInt(2, bbsNum);
				pstmt.setString(3, AnswerNick);
				pstmt.setString(4, AnswerContent);
				pstmt.setInt(5, 0);
				pstmt.setInt(6, 0);
				pstmt.setInt(7, 0);
				pstmt.executeUpdate();
				
			}catch(Exception ex){
				System.out.println("BbsInsertAnswer 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
		}
		
		public ArrayList<BbsBean> getBbsAnswerDetail(int bbsNum) {
			String sql = "select *from bbsanswer where BbsNumCheck = ?";
			String NotAgree = "비공감으로 인하여 블라인드 처리된 댓글 입니다^^!";
			ArrayList<BbsBean> bbsanswerlist = new ArrayList<BbsBean>();
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bbsNum);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					if(rs.getInt("BbsAnswerNotAgree") < 20) {
						
						BbsBean bbsBean = new BbsBean();
						bbsBean.setBbsAnswerNum(rs.getInt("BbsAnswerNum"));
						bbsBean.setBbsNickCheck(rs.getString("BbsNickCheck"));
						bbsBean.setBbsAnswerContent(rs.getString("BbsAnswerContent"));
						bbsBean.setAnswerDate(rs.getString("Date").substring(0,19));
						bbsBean.setBbsAnswerAgree(rs.getInt("BbsAnswerAgree"));
						bbsBean.setBbsAnswerNotAgree(rs.getInt("BbsAnswerNotAgree"));
						bbsBean.setReplyNum(rs.getInt("ReplyNum"));
						
						bbsanswerlist.add(bbsBean);

					}else {
						
						BbsBean bbsBean = new BbsBean();
						bbsBean.setBbsAnswerNum(rs.getInt("BbsAnswerNum"));
						bbsBean.setBbsNickCheck(rs.getString("BbsNickCheck"));
						bbsBean.setBbsAnswerContent(NotAgree);
						bbsBean.setAnswerDate(rs.getString("Date").substring(0,19));
						bbsBean.setBbsAnswerAgree(rs.getInt("BbsAnswerAgree"));
						bbsBean.setBbsAnswerNotAgree(rs.getInt("BbsAnswerNotAgree"));
						bbsBean.setReplyNum(rs.getInt("ReplyNum"));
						
						bbsanswerlist.add(bbsBean);
						
					}
				}
				
			}catch(Exception ex){
				System.out.println("getBbsAnswerDetail 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
			
			return bbsanswerlist;
		}
		
		public ArrayList<BbsBean> getBbsAnswerDetailAgree(int bbsNum) {
			
			String sql = "select *from bbsanswer where BbsNumCheck = ? and"
					+ " BbsAnswerAgree>10 order by Bbsansweragree desc limit 0,3";
			
			String NotAgree = "비공감으로 인하여 블라인드 처리된 댓글 입니다^^!";
			
			ArrayList<BbsBean> bbsanswerlist = new ArrayList<BbsBean>();
			
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bbsNum);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					if(rs.getInt("BbsAnswerNotAgree") < 20) {
						
						BbsBean bbsBean = new BbsBean();
						bbsBean.setBbsAnswerNum(rs.getInt("BbsAnswerNum"));
						bbsBean.setBbsNickCheck(rs.getString("BbsNickCheck"));
						bbsBean.setBbsAnswerContent(rs.getString("BbsAnswerContent"));
						bbsBean.setAnswerDate(rs.getString("Date").substring(0,19));
						bbsBean.setBbsAnswerAgree(rs.getInt("BbsAnswerAgree"));
						bbsBean.setBbsAnswerNotAgree(rs.getInt("BbsAnswerNotAgree"));
						bbsBean.setReplyNum(rs.getInt("ReplyNum"));
						
						bbsanswerlist.add(bbsBean);

					}else {
						
						BbsBean bbsBean = new BbsBean();
						bbsBean.setBbsAnswerNum(rs.getInt("BbsAnswerNum"));
						bbsBean.setBbsNickCheck(rs.getString("BbsNickCheck"));
						bbsBean.setBbsAnswerContent(NotAgree);
						bbsBean.setAnswerDate(rs.getString("Date").substring(0,19));
						bbsBean.setBbsAnswerAgree(rs.getInt("BbsAnswerAgree"));
						bbsBean.setBbsAnswerNotAgree(rs.getInt("BbsAnswerNotAgree"));
						bbsBean.setReplyNum(rs.getInt("ReplyNum"));
						
						bbsanswerlist.add(bbsBean);
						
					}
				}
				
			}catch(Exception ex){
				System.out.println("getBbsAnswerDetailAgree 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
			
			return bbsanswerlist;
		}
		
		public int getAnswerCount(int bbsNum) {
			String sql = "select count(*) from bbsanswer where BbsNumCheck = ?";
			int count = 0;
			try {
				
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bbsNum);
				rs = pstmt.executeQuery();
				
				if(rs.next())
					count = rs.getInt(1);
				
				
			}catch(Exception ex){
				System.out.println("getAnswerCount 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
			return count;
		}
		
		public void setAnswerAgree(int answerNum) {
			
			String sql = "update bbsanswer set BbsAnswerAgree = BbsAnswerAgree+1 where BbsAnswerNum = ?";
			
			try {
				
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, answerNum);
				pstmt.executeUpdate();
				
			}catch(Exception ex){
				System.out.println("setAnswerAgree 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
		}
		
		public void setAnswerNotAgree(int answerNum) {
			
			String sql = "update bbsanswer set BbsAnswerNotAgree = BbsAnswerNotAgree+1 where BbsAnswerNum = ?";
			
			try {
				
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, answerNum);
				pstmt.executeUpdate();
				
			}catch(Exception ex){
				System.out.println("setAnswerNotAgree 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
		}
		
		//게시판 댓글 삭제
		public boolean AnswerDelete(int answerNum) {
			
			String sql = "delete from bbsanswer where BbsAnswerNum = ?";
			boolean result = false;
			int delete = 0;
			try {
				
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, answerNum);
				delete = pstmt.executeUpdate();
				
				if(delete != 0)
					return true;
				else
					return false;
				
			}catch(Exception ex){
				System.out.println("AnswerDelete 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
			
			return false;
		}
		
		//댓글 리플레이 등록
		public void BbsInsertReply(int bbsNum, int replyNum, String replyNick, String replyContent){
			String sql = "insert into bbsanswer (BbsAnswerNum, BbsNumCheck, BbsNickCheck, "
					+ "BbsAnswerContent, BbsAnswerAgree, BbsAnswerNotAgree, Date, ReplyNum) values(?,?,?,?,?,?,NOW(),?) ";
			int num;
			try {
				
				conn = ds.getConnection();
				pstmt=conn.prepareStatement(
						"select max(BbsAnswerNum) from bbsanswer");
				rs = pstmt.executeQuery();
				
				if(rs.next())
					num =rs.getInt(1)+1;
				else
					num=1;
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setInt(2, bbsNum);
				pstmt.setString(3, replyNick);
				pstmt.setString(4, replyContent);
				pstmt.setInt(5, 0);
				pstmt.setInt(6, 0);
				pstmt.setInt(7, replyNum);
				pstmt.executeUpdate();
				
			}catch(Exception ex){
				System.out.println("BbsInsertReply 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
		}
		
		//답글 갯수 구하기
		public int getReplyCount(int bbsNum, int answernum) {
			String sql = "select count(*) from bbsanswer where BbsNumCheck = ? and ReplyNum = ?";
			int count = 0;
			try {
				
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bbsNum);
				pstmt.setInt(2, answernum);
				rs = pstmt.executeQuery();
				
				if(rs.next())
					count = rs.getInt(1);
				
				
			}catch(Exception ex){
				System.out.println("getReplyCount 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
			return count;
		}
		
		//답글 리스트 가져오기
		public ArrayList<BbsBean> getReplyDetail(int bbsNum, int answernum) {
			String sql = "select *from bbsanswer where BbsNumCheck = ? and ReplyNum = ?";
			String NotAgree = "비공감으로 인하여 블라인드 처리된 댓글 입니다^^!";
			
			ArrayList<BbsBean> replylist = new ArrayList<BbsBean>();
			
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bbsNum);
				pstmt.setInt(2, answernum);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					if(rs.getInt("BbsAnswerNotAgree") < 20) {
						
						BbsBean bbsBean = new BbsBean();
						bbsBean.setBbsAnswerNum(rs.getInt("BbsAnswerNum"));
						bbsBean.setBbsNickCheck(rs.getString("BbsNickCheck"));
						bbsBean.setBbsAnswerContent(rs.getString("BbsAnswerContent"));
						bbsBean.setAnswerDate(rs.getString("Date").substring(0,19));
						bbsBean.setBbsAnswerAgree(rs.getInt("BbsAnswerAgree"));
						bbsBean.setBbsAnswerNotAgree(rs.getInt("BbsAnswerNotAgree"));
						bbsBean.setReplyNum(rs.getInt("ReplyNum"));
						
						replylist.add(bbsBean);

					}else {
						
						BbsBean bbsBean = new BbsBean();
						bbsBean.setBbsAnswerNum(rs.getInt("BbsAnswerNum"));
						bbsBean.setBbsNickCheck(rs.getString("BbsNickCheck"));
						bbsBean.setBbsAnswerContent(NotAgree);
						bbsBean.setAnswerDate(rs.getString("Date").substring(0,19));
						bbsBean.setBbsAnswerAgree(rs.getInt("BbsAnswerAgree"));
						bbsBean.setBbsAnswerNotAgree(rs.getInt("BbsAnswerNotAgree"));
						bbsBean.setReplyNum(rs.getInt("ReplyNum"));
						
						replylist.add(bbsBean);
						
					}
				}
				
			}catch(Exception ex){
				System.out.println("getReplyDetail 에러 : " + ex);
			}finally{
				if(rs!=null)try{rs.close();}catch(SQLException ex){}
				if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
				if(conn !=null)try{conn.close();}catch(SQLException ex){}
			}
			
			return replylist;
		}
}
