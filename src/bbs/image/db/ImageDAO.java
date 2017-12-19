package bbs.image.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class ImageDAO {
	
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public ImageDAO() {
		try {
	         Context init = new InitialContext();
	         ds = (DataSource) init.lookup("java:comp/env/jdbc/mysql");
	      } catch (Exception ex) {
	         System.out.println("DB 연결 실패 : " + ex);
	         return;
	      }
	}
	
	//이미지 게시판 등록
	public int imgInsert(ImageBean imgBean) {
		int num = 0;
		int result = 0;
		String sql = "";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select max(imgNum) from bbsimage");
			rs = pstmt.executeQuery();
			
			if(rs.next())
				num = rs.getInt(1)+1;
			else
				num = 1;
			
			sql = "insert into bbsimage (imgNum, imgNick, imgSubject, imgContent, imgReadcount, imgDate, heartcount) "
					+ "values(?,?,?,?,?,NOW(),?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, imgBean.getImgNick());
			pstmt.setString(3, imgBean.getImgSubject());
			pstmt.setString(4, imgBean.getImgContent());
			pstmt.setInt(5, 0);
			pstmt.setInt(6, 0);
			result = pstmt.executeUpdate();
			
					
		}catch(Exception ex){
			System.out.println("imgInsert 에러 : "+ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
			if(conn!=null) try{conn.close();}catch(SQLException ex){}
		}
		return num;
	}

	public void setImageName(int num) {
		
		String sql = "";

		int count = 0;
		String imagename = "";
		
		try {
			conn = ds.getConnection();
			
			sql = "select instr(imgContent, 'src=\"/upload/') from bbsimage where imgNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			//rs.getInt(1);
			if(rs.next())
				count = rs.getInt(1)+13;
			
			sql = "select substring(imgContent, ?, 40) from bbsimage where imgnum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setInt(2, num);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				imagename = rs.getString(1);
			
			sql = "update bbsimage set imgName = ? where imgnum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, imagename);
			pstmt.setInt(2, num);
			pstmt.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("setImageName 에러 : "+ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
			if(conn!=null) try{conn.close();}catch(SQLException ex){}
		}
	}
	
	//이미지 게시판 리스트 가져오기
	public ArrayList<ImageBean> getImageList(HashMap<String, Object> listOpt){
		
		ArrayList<ImageBean> imglist = new ArrayList<ImageBean>();
		
		String opt = (String) listOpt.get("opt");
		String condition = (String) listOpt.get("condition");
		int start = (Integer) listOpt.get("start");
		
		try {
			conn=ds.getConnection();
			// 글목록 전체를 보여줄 때
	        if(opt == null){
	        	String img_list_sql="select @rownum:=@rownum+1 rnum, imgNum, imgNick, imgSubject, imgContent,"+
	        			"imgReadcount, imgDate, imgName, heartcount from bbsimage,(select @rownum:=0)tmp order by rnum desc limit ?,?";
	        	
	        	pstmt = conn.prepareStatement(img_list_sql);
	            pstmt.setInt(1, start-1);
	            pstmt.setInt(2, 10);
	           
	        }
	        else if(opt.equals("0")) // 제목으로 검색
	        {
	        	String img_subjectserch_sql="select @rownum:=@rownum+1 rnum, imgNum, imgNick, imgSubject, imgContent,"+
	        			"imgReadcount, imgDate, imgName, heartcount from bbsimage,(select @rownum:=0)tmp where imgSubject like ? "
	        			+ "order by rnum desc limit ?,?";
	        	
	        	
	            
	        	pstmt = conn.prepareStatement(img_subjectserch_sql);
	            pstmt.setString(1, "%"+condition+"%");
	            pstmt.setInt(2, start-1);
	            pstmt.setInt(3, 10);
	            
	        }
	        else if(opt.equals("1")) // 내용으로 검색
	        {
	        	String img_contentserch_sql="select @rownum:=@rownum+1 rnum, imgNum, imgNick, imgSubject, imgContent,"+
	        			"imgReadcount, imgDate, imgName, heartcount from bbsimage,(select @rownum:=0)tmp where imgContent like ? "
	        			+ "order by rnum desc limit ?,?";
	            
	        	pstmt = conn.prepareStatement(img_contentserch_sql);
	            pstmt.setString(1, "%"+condition+"%");
	        	pstmt.setInt(2, start-1);
	            pstmt.setInt(3, 10);
	            
	        }
	        else if(opt.equals("2")) // 제목+내용으로 검색
	        {
	        	String img_subjectcontentserch_sql="select @rownum:=@rownum+1 rnum, imgNum, imgNick, imgSubject, imgContent,"+
	        			"imgReadcount, imgDate, imgName, heartcount from bbsimage,(select @rownum:=0)tmp where imgContent like ? "
	        			+ "or imgSubject like ? order by rnum desc limit ?,?";
	            
	        	pstmt = conn.prepareStatement(img_subjectcontentserch_sql);
	            pstmt.setString(1, "%"+condition+"%");
	            pstmt.setString(2, "%"+condition+"%");
	        	pstmt.setInt(3, start-1);
	            pstmt.setInt(4, 10);
	           
	        }
	        else if(opt.equals("3")) // 글쓴이로 검색
	        {
	        	String bbs_idserch_sql="select @rownum:=@rownum+1 rnum, imgNum, imgNick, imgSubject, imgContent,"+
	        			"imgReadcount, imgDate, imgName, heartcount from bbsimage,(select @rownum:=0)tmp where imgNick like ? "
	        			+ "order by rnum desc limit ?,?";
	            
	        	pstmt = conn.prepareStatement(bbs_idserch_sql);
	            pstmt.setString(1, "%"+condition+"%");
	        	pstmt.setInt(2, start-1);
	            pstmt.setInt(3, 10);
	            
	        }
	        
	        rs = pstmt.executeQuery();
	        
		        while(rs.next()) {
		        	
		        	ImageBean imgBean = new ImageBean();
		        	imgBean.setImgNum(rs.getInt("imgNum"));
		        	imgBean.setImgNick(rs.getString("imgNick"));
		        	imgBean.setImgSubject(rs.getString("imgSubject"));
		        	imgBean.setImgContent(rs.getString("imgContent"));
		        	imgBean.setImgReadcount(rs.getInt("imgReadcount"));
		        	imgBean.setImgDate(rs.getString("imgDate").substring(0,19));
		        	imgBean.setImgfile(rs.getString("imgName"));
		        	imgBean.setHeartcount(rs.getInt("heartcount"));
		        	imglist.add(imgBean);
		        	
		        }
		        
		        return imglist;
	        
	        
	        
		}catch(Exception ex){
			System.out.println("getImageList 에러 : " + ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
			if(conn!=null) try{conn.close();}catch(SQLException ex){}
		}
		return null;
		
	}
	
	//이미지 게시판 글 개수 구하기
	public int getImgListCount(HashMap<String, Object> listOpt) {
		
		int result = 0;
		String opt = (String) listOpt.get("opt");
		String condition = (String) listOpt.get("condition");
		
		try {
			
			conn = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			if(opt == null)    // 전체글의 개수
            {
                sql.append("select count(*) from bbsimage");
                pstmt = conn.prepareStatement(sql.toString());
                
                // StringBuffer를 비운다.
                sql.delete(0, sql.toString().length());
            }
            else if(opt.equals("0")) // 제목으로 검색한 글의 개수
            {
                sql.append("select count(*) from bbsimage where imgSubject like ?");
                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, '%'+condition+'%');
                
                sql.delete(0, sql.toString().length());
            }
            else if(opt.equals("1")) // 내용으로 검색한 글의 개수
            {
                sql.append("select count(*) from bbsimage where imgContent like ?");
                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, '%'+condition+'%');
                
                sql.delete(0, sql.toString().length());
            }
            else if(opt.equals("2")) // 제목+내용으로 검색한 글의 개수
            {
                sql.append("select count(*) from bbsimage ");
                sql.append("where imgSubject like ? or imgContent like ?");
                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, '%'+condition+'%');
                pstmt.setString(2, '%'+condition+'%');
                
                sql.delete(0, sql.toString().length());
            }
            else if(opt.equals("3")) // 글쓴이로 검색한 글의 개수
            {
                sql.append("select count(*) from bbsimage where imgNick like ?");
                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, '%'+condition+'%');
                
                sql.delete(0, sql.toString().length());
            }
			
			rs = pstmt.executeQuery();
			if(rs.next())
				result = rs.getInt(1);
		}catch(Exception ex){
			System.out.println("getImgListCount 에러 : " + ex);			
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
			if(conn!=null) try{conn.close();}catch(SQLException ex){}
		}
        
        return result;
		
	}
	
	public void setImageSave(String imgname) {
		System.out.println("넘어오냐?");
		
		String sql = "insert into testimage values(?)";
		
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, imgname);
			pstmt.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("setImageSave 에러 : " + ex);			
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
			if(conn!=null) try{conn.close();}catch(SQLException ex){}
		}
		
	}
	
	public ImageBean getImageView(int num) throws Exception{
		
		String sql = "select *from bbsimage where imgNum = ?";
		
		ImageBean imgBean = null;
		
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				imgBean = new ImageBean();
				imgBean.setImgNum(rs.getInt("imgNum"));
				imgBean.setImgNick(rs.getString("imgNick"));
				imgBean.setImgSubject(rs.getString("imgSubject"));
				imgBean.setImgContent(rs.getString("imgContent"));
				imgBean.setImgReadcount(rs.getInt("imgReadcount"));
				imgBean.setImgDate(rs.getString("imgDate"));
				imgBean.setHeartcount(rs.getInt("heartcount"));
				
			}
			
		}catch(Exception ex){
			System.out.println("getImageView 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
			if(conn !=null)try{conn.close();}catch(SQLException ex){}
		}
		return imgBean;
	}
	
	public void setImgReadCountUpdate(int num, String Nick) throws Exception{
		
		String sql1 = "select *from bbsimagereadcount where imgNum like ? and userNick like ?";
		
		String sql2 = "insert into bbsimagereadcount values(?,?,NOW())";
		
		String sql3="update bbsimage set imgReadcount = "+
			"imgReadcount+1 where imgNum = "+num;
		
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
			System.out.println("setImgReadCountUpdate 에러 : "+ex);
		}
		finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception ex) {}
		}
	}
	
	//글쓴이 인지 확인
	public boolean isImgWriter(int num, String imgNick) {
		
		String sql = "select imgNick from bbsimage where imgNum = ?";
		if(imgNick == null)
			return false;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				if(imgNick.equals(rs.getString("imgNick")))
					return true;
			}
		}catch(SQLException ex){
			System.out.println("isImgWriter 에러 : "+ex);
		}
		finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception ex) {}
		}
		return false;
	}
	
	//글 삭제
	public boolean ImgDelete(int num) {
		
		String sql = "delete from bbsimage where imgNum = ?";
		
		int result = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			
			if(result == 0)
				return false;
			
			return true;
		}catch(Exception ex){
			System.out.println("ImgDelete 에러 : "+ex);
		}finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception ex) {}
		}
		
		return false;
	}
	
	//글 수정
	public boolean ImgModify(ImageBean imgBean) throws Exception{
		
		String sql = "update bbsimage set imgSubject = ?, imgContent = ? where imgNum = ?";
		
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, imgBean.getImgSubject());
			pstmt.setString(2, imgBean.getImgContent());
			pstmt.setInt(3, imgBean.getImgNum());
			pstmt.executeUpdate();
			
			return true;
		}catch(Exception ex){
			System.out.println("ImgModify 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(conn!=null)try{conn.close();}catch(SQLException ex){}
			}
		return false;
	}
	
	//이미지 게시판 답글 등록
	public void imgInsertAnswer(int imgNumCheck, String imgAnswerNick, String AnswerContent) {
		
		String sql = "insert into bbsimageanswer (imgAnswerNum, imgNumCheck, imgNickCheck, imgAnswerContent, "
				+ "imgAnswerAgree, imgAnswerNotAgree, Date, imgReplyNum) values(?,?,?,?,?,?,NOW(),?)";
		int num;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select max(imgAnswerNum) from bbsimageanswer");
			rs = pstmt.executeQuery();
			
			if(rs.next())
				num = rs.getInt(1)+1;
			else
				num = 1;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, imgNumCheck);
			pstmt.setString(3, imgAnswerNick);
			pstmt.setString(4, AnswerContent);
			pstmt.setInt(5, 0);
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("imgInsertAnswer 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
			if(conn !=null)try{conn.close();}catch(SQLException ex){}
		}
	}
	
	//이미지 게시판 댓글 수 구하기
	public int getImageAnswerCount(int imgNum) {
		
		String sql = "select count(*) from bbsimageanswer where imgNumCheck = ?";
		int count = 0;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, imgNum);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				count = rs.getInt(1);
		}catch(Exception ex){
			System.out.println("getImageAnswerCount 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
			if(conn !=null)try{conn.close();}catch(SQLException ex){}
		}
		return count;
	}
	
	//이미지 게시판 글 목록 불러오기
	public ArrayList<ImageBean> getImageAnswerDetail(int imgNumCheck){
		
		String sql = "select *from bbsimageanswer where imgNumCheck = ?";
		String NotAgree = "비공감으로 인하여 블라인드 처리된 댓글 입니다^^!";
		ArrayList<ImageBean> imganswerlist = new ArrayList<ImageBean>();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, imgNumCheck);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt("imgAnswerNotAgree") < 20) {
					ImageBean imgBean = new ImageBean();
					imgBean.setImgAnswerNum(rs.getInt("imgAnswerNum"));
					imgBean.setImgNumCheck(rs.getInt("imgNumCheck"));
					imgBean.setImgNickCheck(rs.getString("imgNickCheck"));
					imgBean.setImgAnswerContent(rs.getString("imgAnswerContent"));
					imgBean.setImgAnswerDate(rs.getString("Date").substring(0, 19));
					imgBean.setImgAnswerAgree(rs.getInt("imgAnswerAgree"));
					imgBean.setImgAnswerNotAgree(rs.getInt("imgAnswerNotAgree"));
					imgBean.setImgReplyNum(rs.getInt("imgReplyNum"));
					
					imganswerlist.add(imgBean);
				}else {
					ImageBean imgBean = new ImageBean();
					imgBean.setImgAnswerNum(rs.getInt("imgAnswerNum"));
					imgBean.setImgNumCheck(rs.getInt("imgNumCheck"));
					imgBean.setImgNickCheck(rs.getString("imgNickCheck"));
					imgBean.setImgAnswerContent(NotAgree);
					imgBean.setImgAnswerDate(rs.getString("Date").substring(0, 19));
					imgBean.setImgAnswerAgree(rs.getInt("imgAnswerAgree"));
					imgBean.setImgAnswerNotAgree(rs.getInt("imgAnswerNotAgree"));
					imgBean.setImgReplyNum(rs.getInt("imgReplyNum"));
					
					imganswerlist.add(imgBean);
				}
			}
		}catch(Exception ex){
			System.out.println("getImageAnswerDetail 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
			if(conn !=null)try{conn.close();}catch(SQLException ex){}
		}
		
		return imganswerlist;
	}
	
	//베스트 댓글 3개 구하기
public ArrayList<ImageBean> getImageAnswerDetailAgree(int imgNumCheck){
		
		String sql = "select *from bbsimageanswer where imgNumCheck = ? "
				+ "and imgAnswerAgree>10 order by imgAnswerAgree desc limit 0,3";
		String NotAgree = "비공감으로 인하여 블라인드 처리된 댓글 입니다^^!";
		ArrayList<ImageBean> imganswerlist = new ArrayList<ImageBean>();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, imgNumCheck);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt("imgAnswerNotAgree") < 20) {
					ImageBean imgBean = new ImageBean();
					imgBean.setImgAnswerNum(rs.getInt("imgAnswerNum"));
					imgBean.setImgNumCheck(rs.getInt("imgNumCheck"));
					imgBean.setImgNickCheck(rs.getString("imgNickCheck"));
					imgBean.setImgAnswerContent(rs.getString("imgAnswerContent"));
					imgBean.setImgAnswerDate(rs.getString("Date").substring(0, 19));
					imgBean.setImgAnswerAgree(rs.getInt("imgAnswerAgree"));
					imgBean.setImgAnswerNotAgree(rs.getInt("imgAnswerNotAgree"));
					imgBean.setImgReplyNum(rs.getInt("imgReplyNum"));
					
					imganswerlist.add(imgBean);
				}else {
					ImageBean imgBean = new ImageBean();
					imgBean.setImgAnswerNum(rs.getInt("imgAnswerNum"));
					imgBean.setImgNumCheck(rs.getInt("imgNumCheck"));
					imgBean.setImgNickCheck(rs.getString("imgNickCheck"));
					imgBean.setImgAnswerContent(NotAgree);
					imgBean.setImgAnswerDate(rs.getString("Date").substring(0, 19));
					imgBean.setImgAnswerAgree(rs.getInt("imgAnswerAgree"));
					imgBean.setImgAnswerNotAgree(rs.getInt("imgAnswerNotAgree"));
					imgBean.setImgReplyNum(rs.getInt("imgReplyNum"));
					
					imganswerlist.add(imgBean);
				}
			}
		}catch(Exception ex){
			System.out.println("getImageAnswerDetailAgree 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
			if(conn !=null)try{conn.close();}catch(SQLException ex){}
		}
		
		return imganswerlist;
	}
	
	//이미지 게시판 답글 갯수 구하기
	public int getImgAnswerCount(int imgNum) {
		
		String sql = "select count(*) from bbsimageanswer where imgNumCheck = ?";
		int count = 0;
		
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, imgNum);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				count = rs.getInt(1);
		}catch(Exception ex){
			System.out.println("getImgAnswerCount 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
			if(conn !=null)try{conn.close();}catch(SQLException ex){}
		}
		return count;
	}
	
	//댓글 공감 올리기
	public void setAnswerAgree(int answerNum) {
		
		String sql = "update bbsimageanswer set imgAnswerAgree = imgAnswerAgree+1 where imgAnswerNum = ?";
		
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
	
	//댓글 비공감
	public void setAnswerNotAgree(int answerNum) {
		
		String sql = "update bbsimageanswer set imgAnswerNotAgree = imgAnswerNotAgree+1 where imgAnswerNum = ?";
		
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
	
	//댓글 삭제
	public boolean AnswerDelete(int answerNum) {
				
		String sql = "delete from bbsimageanswer where imgAnswerNum = ?";
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
			public void imgInsertReply(int imgNum, int replyNum, String replyNick, String replyContent){
				String sql = "insert into bbsimageanswer (imgAnswerNum, imgNumCheck, imgNickCheck, "
						+ "imgAnswerContent, imgAnswerAgree, imgAnswerNotAgree, Date, imgReplyNum) values(?,?,?,?,?,?,NOW(),?) ";
				int num;
				try {
					
					conn = ds.getConnection();
					pstmt=conn.prepareStatement(
							"select max(imgAnswerNum) from bbsimageanswer");
					rs = pstmt.executeQuery();
					
					if(rs.next())
						num =rs.getInt(1)+1;
					else
						num=1;
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num);
					pstmt.setInt(2, imgNum);
					pstmt.setString(3, replyNick);
					pstmt.setString(4, replyContent);
					pstmt.setInt(5, 0);
					pstmt.setInt(6, 0);
					pstmt.setInt(7, replyNum);
					pstmt.executeUpdate();
					
				}catch(Exception ex){
					System.out.println("imgInsertReply 에러 : " + ex);
				}finally{
					if(rs!=null)try{rs.close();}catch(SQLException ex){}
					if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
					if(conn !=null)try{conn.close();}catch(SQLException ex){}
				}
			}
			
			//답글 리스트 가져오기
			public ArrayList<ImageBean> getReplyDetail(int imgNum, int answernum) {
				String sql = "select *from bbsimageanswer where imgNumCheck = ? and imgReplyNum = ?";
				String NotAgree = "비공감으로 인하여 블라인드 처리된 댓글 입니다^^!";
				
				ArrayList<ImageBean> replylist = new ArrayList<ImageBean>();
				
				try {
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, imgNum);
					pstmt.setInt(2, answernum);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						if(rs.getInt("imgAnswerNotAgree") < 20) {
							
							ImageBean imgBean = new ImageBean();
							imgBean.setImgAnswerNum(rs.getInt("imgAnswerNum"));
							imgBean.setImgNickCheck(rs.getString("imgNickCheck"));
							imgBean.setImgAnswerContent(rs.getString("imgAnswerContent"));
							imgBean.setImgDate(rs.getString("Date").substring(0,19));
							imgBean.setImgAnswerAgree(rs.getInt("imgAnswerAgree"));
							imgBean.setImgAnswerNotAgree(rs.getInt("imgAnswerNotAgree"));
							imgBean.setImgReplyNum(rs.getInt("imgReplyNum"));
							
							replylist.add(imgBean);

						}else {
							
							ImageBean imgBean = new ImageBean();
							imgBean.setImgAnswerNum(rs.getInt("imgAnswerNum"));
							imgBean.setImgNickCheck(rs.getString("imgNickCheck"));
							imgBean.setImgAnswerContent(NotAgree);
							imgBean.setImgDate(rs.getString("Date").substring(0,19));
							imgBean.setImgAnswerAgree(rs.getInt("imgAnswerAgree"));
							imgBean.setImgAnswerNotAgree(rs.getInt("imgAnswerNotAgree"));
							imgBean.setImgReplyNum(rs.getInt("imgReplyNum"));
							
							replylist.add(imgBean);
							
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
			
	//이미지 게시판 추천수
	public void updateHeartCount(int imgNum) {
		
		String sql = "update bbsimage set heartcount = heartcount+1 where imgNum =?";
		
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, imgNum);
			pstmt.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("updateHeartCount 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
			if(conn !=null)try{conn.close();}catch(SQLException ex){}
		}
	}
			
}


