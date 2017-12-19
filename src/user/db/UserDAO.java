package user.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class UserDAO {
		
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs;
		DataSource ds;
		
		public UserDAO() {
			try {
		         Context init = new InitialContext();
		         ds = (DataSource) init.lookup("java:comp/env/jdbc/mysql");
		      } catch (Exception ex) {
		         System.out.println("DB 연결 실패 : " + ex);
		         return;
		      }
		}
		
		public int userlogin(String userID, String userPassword) {
			
			String SQL = "SELECT userPassword, userAvailable FROM USER WHERE userID = ?";
			int result = -1;
			try {
				
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					
					System.out.println("available 값 :"+rs.getInt(2));
					if(rs.getString(1).equals(userPassword) && rs.getInt(2)==0)
						result = 1; //로그인 성공
					else if(rs.getString(1).equals(userPassword) && rs.getInt(2)!=0)
						result = 2; //블랙리스트임
					else
						result = 0; //비밀번호 불일치
				}else {
					result = -1; //아이디가 없음
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
		         if (rs != null)
		             try {
		                rs.close();
		             } catch (SQLException ex) {
		             }
		          if (pstmt != null)
		             try {
		                pstmt.close();
		             } catch (SQLException ex) {
		             }
		          if (conn != null)
		             try {
		                conn.close();
		             } catch (SQLException ex) {
		             }
		       }
			
			System.out.println("result = " + result);
			
			return result; //데이터베이스 오류
		}
		
		public UserBean userNick(UserBean userID) {
			UserBean ub = null;
			String SQL = "SELECT userNick FROM USER WHERE userID = ?";
			
			try {
				System.out.println("유저닉 유저 아이디: " + userID.getUserID());
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID.getUserID());
				rs = pstmt.executeQuery();
				if(rs != null)
						rs.next();
				//System.out.println(rs.next());
				ub = new UserBean();
				ub.setUserNick(rs.getString("userNick"));
				
				System.out.println("유저닉 : "+rs.getString("userNick"));
				System.out.println("유저닉 리턴값 : " + ub.getUserNick());
				return ub;
				
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("유저닉 에러: " + e);
			}finally {
		         if (rs != null)
		             try {
		                rs.close();
		             } catch (SQLException ex) {
		             }
		          if (pstmt != null)
		             try {
		                pstmt.close();
		             } catch (SQLException ex) {
		             }
		          if (conn != null)
		             try {
		                conn.close();
		             } catch (SQLException ex) {
		             }
		       }
			
			return null; //데이터베이스 오류
		}
		
		public int userjoin(UserBean user) {
			String SQL = "INSERT INTO USER VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";
			try {
				System.out.println("===3===");
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, user.getUserID());
				pstmt.setString(2, user.getUserPassword());
				pstmt.setString(3, user.getUserName());
				pstmt.setString(4, user.getUserNick());
				pstmt.setString(5, user.getUserPhone1()+"-"+user.getUserPhone2()+"-"+user.getUserPhone3());
				pstmt.setString(6, user.getUserGender());
				pstmt.setString(7, user.getUserDate1()+"년 "+user.getUserDate2()+"월 "+user.getUserDate3()+"일");
				pstmt.setString(8, user.getUserEmail1()+"@"+user.getUserEmail2());
				pstmt.setString(9, user.getUserAddress1()+"-"+user.getUserAddress2()+" "+user.getUserAddress3());
				pstmt.setInt(10, 0);
				System.out.println("===3.1===");
				pstmt.executeUpdate();
							
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("===3.2===");
				return -1; //데이터베이스 오류
			}finally {
				System.out.println("===3.3===");
		         if (rs != null)
		             try {
		                rs.close();
		             } catch (SQLException ex) {
		             }
		          if (pstmt != null)
		             try {
		                pstmt.close();
		             } catch (SQLException ex) {
		             }
		          if (conn != null)
		             try {
		                conn.close();
		             } catch (SQLException ex) {
		             }
		       }
			
			return 0;
		}
		//유저인지 확인
		public int isUser(UserBean user) {
		      String sql = "SELECT userPassword FROM user WHERE userID=?";
		      int result = -1;

		      try {
		         conn = ds.getConnection();
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setString(1, user.getUserID());
		         rs = pstmt.executeQuery();

		         if (rs.next()) {
		            if (rs.getString("userPassword").equals(user.getUserPassword())) {
		               result = 1;// 일치
		            } else {
		               result = 0;// 불일치
		            }
		         } else {
		            result = -1;// 아이디 존재하지 않음
		         }
		      } catch (Exception ex) {
		         System.out.println("isUser 에러: " + ex);
		      } finally {
		         if (rs != null)
		            try {
		               rs.close();
		            } catch (SQLException ex) {
		            }
		         if (pstmt != null)
		            try {
		               pstmt.close();
		            } catch (SQLException ex) {
		            }
		         if (conn != null)
		            try {
		               conn.close();
		            } catch (SQLException ex) {
		            }
		      }

		      return result;
		   }
		
		//유저 닉네임 확인
				public int isNick(UserBean user) {
				      String sql = "SELECT userPassword FROM user WHERE userNick=?";
				      int result = -1;

				      try {
				         conn = ds.getConnection();
				         pstmt = conn.prepareStatement(sql);
				         pstmt.setString(1, user.getUserNick());
				         rs = pstmt.executeQuery();

				         if (rs.next()) {
				            if (rs.getString("userPassword").equals(user.getUserPassword())) {
				               result = 1;// 일치
				            } else {
				               result = 0;// 불일치
				            }
				         } else {
				            result = -1;// 아이디 존재하지 않음
				         }
				      } catch (Exception ex) {
				         System.out.println("isNick 에러: " + ex);
				      } finally {
				         if (rs != null)
				            try {
				               rs.close();
				            } catch (SQLException ex) {
				            }
				         if (pstmt != null)
				            try {
				               pstmt.close();
				            } catch (SQLException ex) {
				            }
				         if (conn != null)
				            try {
				               conn.close();
				            } catch (SQLException ex) {
				            }
				      }

				      return result;
				   }
				
				//유저 리스트 가져오기
				public ArrayList<UserBean> getUserList(HashMap<String, Object> listOpt)
				{
				    ArrayList<UserBean> list = new ArrayList<UserBean>();
				    
				    String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
				    String condition = (String)listOpt.get("condition"); // 검색내용
				    int start = (Integer)listOpt.get("start"); // 현재 페이지번호
				    
				    try {
				    	conn=ds.getConnection();
				        
				        // 글목록 전체를 보여줄 때
				        if(opt == null){
				        	
				        	String user_list_sql="select * from "+
				        			"(select @rownum:=@rownum+1 rnum, userID, userPassword, userName, userNick,"+
				        			"userPhone, userGender, userDate, userEmail, userAddress, userJoindate, userAvailable from user,"+
				        			"(select @rownum:=0)tmp order by userJoindate desc) user where rnum>=? and rnum<=?";
				        	
				        	pstmt = conn.prepareStatement(user_list_sql);
				            pstmt.setInt(1, start);
				            pstmt.setInt(2, start+9);
				           
				        }
				        else if(opt.equals("0")) // 유저 아이디로 검색
				        {
				        	String user_useridserch_sql="select * from "+
				        			"(select @rownum:=@rownum+1 rnum, userID, userPassword, userName, userNick,"+
				        			"userPhone, userGender, userDate, userEmail, userAddress, userJoindate, userAvailable from user,"+
				        			"(select @rownum:=0)tmp order by userJoindate desc) user where rnum>=? and rnum<=? "
				        			+ "and userID like ?";
				            
				        	pstmt = conn.prepareStatement(user_useridserch_sql);
				            pstmt.setInt(1, start);
				            pstmt.setInt(2, start+9);
				            pstmt.setString(3, "%"+condition+"%");
				            
				        }
				        else if(opt.equals("1")) // 유저 닉네임으로 검색
				        {
				        	String user_usernickserch_sql="select * from "+
				        			"(select @rownum:=@rownum+1 rnum, userID, userPassword, userName, userNick,"+
				        			"userPhone, userGender, userDate, userEmail, userAddress, userJoindate, userAvailable from user,"+
				        			"(select @rownum:=0)tmp order by userJoindate desc) user where rnum>=? and rnum<=? "
				        			+ "and userNick like ?";
				            
				        	pstmt = conn.prepareStatement(user_usernickserch_sql);
				        	pstmt.setInt(1, start);
				            pstmt.setInt(2, start+9);
				            pstmt.setString(3, "%"+condition+"%");
				            
				        }
				        
				        rs = pstmt.executeQuery();
				        while(rs.next())
				        {	
				        	UserBean userBean = new UserBean();
				        	userBean.setUserID(rs.getString("userID"));
				        	userBean.setUserPassword(rs.getString("userPassword"));
				        	userBean.setUserName(rs.getString("userName"));
				        	userBean.setUserNick(rs.getString("userNick"));
				        	userBean.setUserPhone1(rs.getString("userPhone"));
				        	userBean.setUserGender(rs.getString("userGender"));
				        	userBean.setUserDate1(rs.getString("userDate"));
				        	userBean.setUserEmail1(rs.getString("userEmail"));
				        	userBean.setUserAddress1(rs.getString("userAddress"));
				        	userBean.setUserDate2(rs.getString("userJoindate"));
				        	userBean.setUserAvailable(rs.getInt("userAvailable"));
				        	list.add(userBean);
				        
				        }
				        
				        return list;
					}catch(Exception ex){
						System.out.println("getUserList 에러 : " + ex);
					}finally{
						if(rs!=null) try{rs.close();}catch(SQLException ex){}
						if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
						if(conn!=null) try{conn.close();}catch(SQLException ex){}
					}
					return null;
				}
				
				//유저 수 구하기
				public int getUserListCount(HashMap<String, Object> listOpt)
			    {
			        int result = 0;
			        String opt = (String)listOpt.get("opt"); // 검색옵션(제목, 내용, 글쓴이 등..)
			        String condition = (String)listOpt.get("condition"); // 검색내용
			        
			        try {
			        	conn=ds.getConnection();
			            StringBuffer sql = new StringBuffer();
			            
			            if(opt == null)    // 전체글의 개수
			            {
			                sql.append("select count(*) from user");
			                pstmt = conn.prepareStatement(sql.toString());
			                
			                // StringBuffer를 비운다.
			                sql.delete(0, sql.toString().length());
			            }
			            else if(opt.equals("0")) // 유저 아이디로 검색
			            {
			                sql.append("select count(*) from user where userID like ?");
			                pstmt = conn.prepareStatement(sql.toString());
			                pstmt.setString(1, '%'+condition+'%');
			                
			                sql.delete(0, sql.toString().length());
			            }
			            else if(opt.equals("1")) // 유저 닉네임으로 검색
			            {
			                sql.append("select count(*) from bbs where userNick like ?");
			                pstmt = conn.prepareStatement(sql.toString());
			                pstmt.setString(1, '%'+condition+'%');
			                
			                sql.delete(0, sql.toString().length());
			            }
			           
			            
			            rs = pstmt.executeQuery();
			            if(rs.next())    result = rs.getInt(1);
			            
			        } catch(Exception ex){
						System.out.println("getUserListCount 에러 : " + ex);			
					}finally{
						if(rs!=null) try{rs.close();}catch(SQLException ex){}
						if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
						if(conn!=null) try{conn.close();}catch(SQLException ex){}
					}
			        
			        return result;
			    }
				
				//유저 블랙리스트 등록
				public boolean setUserBlackUpdate(String userID){
					String SQL="update user set userAvailable = 1 where userID like ?";
				
					try{
						conn = ds.getConnection();
						pstmt=conn.prepareStatement(SQL);
						pstmt.setString(1, userID);
						pstmt.executeUpdate();
						
						return true;
					}catch(Exception ex){
						System.out.println("setUserBlackUpdate 에러 : "+ex);
					}finally{
						try{
							if(pstmt!=null)pstmt.close();
							if(conn!=null)conn.close();
						}catch(Exception ex) {}
					}
					
					return false;
				}
				
				//유저 블랙리스트 해제
				public boolean setUserBlackClearUpdate(String userID){
					String SQL="update user set userAvailable = 0 where userID like ?";
				
					try{
						conn = ds.getConnection();
						pstmt=conn.prepareStatement(SQL);
						pstmt.setString(1, userID);
						pstmt.executeUpdate();
						
						return true;
					}catch(Exception ex){
						System.out.println("setUserBlackClearUpdate 에러 : "+ex);
					}finally{
						try{
							if(pstmt!=null)pstmt.close();
							if(conn!=null)conn.close();
						}catch(Exception ex) {}
					}
					
					return false;
				}
				
				
				//관리자 유저 추방
				public boolean setAdminUserDeleteUpdate(String userID){
					String SQL="DELETE FROM user WHERE userID=?";
				
					try{
						conn = ds.getConnection();
						pstmt=conn.prepareStatement(SQL);
						pstmt.setString(1, userID);
						pstmt.executeUpdate();
						
						return true;
					}catch(Exception ex){
						System.out.println("setUserDeleteUpdate 에러 : "+ex);
					}finally{
						try{
							if(pstmt!=null)pstmt.close();
							if(conn!=null)conn.close();
						}catch(Exception ex) {}
					}
					
					return false;
				}
		//유저 리스트
//		public List getUserList() {
//		      String sql = "SELECT *FROM user";
//		      List userlist = new ArrayList();
//
//		      try {
//		         conn = ds.getConnection();
//		         pstmt = conn.prepareStatement(sql);
//		         rs = pstmt.executeQuery();
//
//		         while (rs.next()) {
//		        	 
//		        	UserBean ub = new UserBean();
//		        	ub.setUserID(rs.getString("userID"));
//		        	ub.setUserPassword(rs.getString("userPassword"));
//		        	ub.setUserName(rs.getString("userName"));
//		        	ub.setUserNick(rs.getString("userNick"));
//		        	ub.setUserPhone1(rs.getString("userPhone"));
//		        	ub.setUserGender(rs.getString("userGender"));
//		        	ub.setUserDate1(rs.getString("userDate"));
//		        	ub.setUserEmail1(rs.getString("userEmail"));
//		        	ub.setUserAddress1(rs.getString("userAddress"));
//		        	
//		        	userlist.add(ub);
//		            
//		         }
//
//		         return userlist;
//		      } catch (Exception ex) {
//		         System.out.println("getUserList 에러: " + ex);
//		      } finally {
//		         if (rs != null)
//		            try {
//		               rs.close();
//		            } catch (SQLException ex) {
//		            }
//		         if (pstmt != null)
//		            try {
//		               pstmt.close();
//		            } catch (SQLException ex) {
//		            }
//		         if (conn != null)
//		            try {
//		               conn.close();
//		            } catch (SQLException ex) {
//		            }
//		      }
//		      return null;
//		   }
		//유저 상세정보
		public UserBean getUserDetail(String userID) {
		      String sql = "SELECT *FROM user WHERE userID=?";

		      try {
		         conn = ds.getConnection();
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setString(1, userID);
		         rs = pstmt.executeQuery();
		         rs.next();

		        UserBean ub = new UserBean();
		        ub.setUserID(rs.getString("userID"));
		        ub.setUserPassword(rs.getString("userPassword"));
		        ub.setUserName(rs.getString("userName"));
		        ub.setUserNick(rs.getString("userNick"));
		        ub.setUserPhone1(rs.getString("userPhone"));
		        ub.setUserGender(rs.getString("userGender"));
		        ub.setUserDate1(rs.getString("userDate"));
		        ub.setUserDate2(rs.getString("userJoindate"));
		        ub.setUserEmail1(rs.getString("userEmail"));
		        ub.setUserAddress1(rs.getString("userAddress"));

		         return ub;
		      } catch (Exception ex) {
		         System.out.println("getDeatilMember 에러: " + ex);
		      } finally {
		         if (rs != null)
		            try {
		               rs.close();
		            } catch (SQLException ex) {
		            }
		         if (pstmt != null)
		            try {
		               pstmt.close();
		            } catch (SQLException ex) {
		            }
		         if (conn != null)
		            try {
		               conn.close();
		            } catch (SQLException ex) {
		            }
		      }

		      return null;
		   }
		//유저 삭제
		public boolean userDelete(String userNick) {
		      String sql1 = "DELETE FROM bbs WHERE BbsNick=?";
		      String sql2 = "DELETE FROM user WHERE userNick=?";
		      
		      System.out.println("deleteNick=" + userNick);
		      try {
		         conn = ds.getConnection();
		         pstmt = conn.prepareStatement(sql1);
		         pstmt.setString(1, userNick);
		         pstmt.executeUpdate();

		         pstmt = conn.prepareStatement(sql2);
		         pstmt.setString(1, userNick);
		         pstmt.executeUpdate();
		         
		         return true;
		      } catch (Exception ex) {
		         System.out.println("userDelete 에러: " + ex);
		      } finally {
		         if (rs != null)
		            try {
		               rs.close();
		            } catch (SQLException ex) {
		            }
		         if (pstmt != null)
		            try {
		               pstmt.close();
		            } catch (SQLException ex) {
		            }
		         if (conn != null)
		            try {
		               conn.close();
		            } catch (SQLException ex) {
		            }
		      }

		      return false;
		   }
		
		//회원정보 수정
		   public boolean updateUser(UserBean userBean) throws SQLException{

			   String SQL = "UPDATE user set userPhone=?, userEmail=?, userAddress=? WHERE userID=?";

			   try {
			      conn = ds.getConnection();
			      pstmt = conn.prepareStatement(SQL);
			      

			      System.out.println("정보 넘어 오냐? " + userBean.getUserPhone1());
			      pstmt.setString(1, userBean.getUserPhone1()+"-"+userBean.getUserPhone2()+"-"+userBean.getUserPhone3());
			      pstmt.setString(2, userBean.getUserEmail1()+"@"+userBean.getUserEmail2());
			      pstmt.setString(3, userBean.getUserAddress1()+"-"+userBean.getUserAddress2()+" "+userBean.getUserAddress3());
			      pstmt.setString(4, userBean.getUserID());
			      
		          pstmt.executeUpdate();
		          
		           return true;
		       } catch (Exception ex) {
		           System.out.println("updateUser 에러: " + ex);
		       } finally {
		          if (rs != null)
		             try {
		                rs.close();
		             } catch (SQLException ex) {
		             }
		          if (pstmt != null)
		             try {
		                pstmt.close();
		             } catch (SQLException ex) {
		             }
		          if (conn != null)
		             try {
		                conn.close();
		             } catch (SQLException ex) {
		             }
		       }
			return false;
		   }
		   
		 //비밀번호 수정
		   public boolean updateUserPw(UserBean userBean) throws SQLException{

			   String SQL = "UPDATE user set userPassword=? WHERE userID=?";

			   try {
			      conn = ds.getConnection();
			      pstmt = conn.prepareStatement(SQL);
			      

			     pstmt.setString(1, userBean.getUserEmail2());
			     pstmt.setString(2, userBean.getUserID());
			      
		          pstmt.executeUpdate();
		          
		           return true;
		       } catch (Exception ex) {
		           System.out.println("updateUserPw 에러: " + ex);
		       } finally {
		          if (rs != null)
		             try {
		                rs.close();
		             } catch (SQLException ex) {
		             }
		          if (pstmt != null)
		             try {
		                pstmt.close();
		             } catch (SQLException ex) {
		             }
		          if (conn != null)
		             try {
		                conn.close();
		             } catch (SQLException ex) {
		             }
		       }
			return false;
		   }
		   
		   //아이디 중복확인
		   public boolean userIdCheck(String userID) {
				
				String SQL = "SELECT userID FROM user WHERE userID = ?";
				boolean result = true;
				try {
					
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, userID);
					rs = pstmt.executeQuery();
					
					System.out.println("유저 아이디 : " +userID);
					
					if(rs.next()) {
						System.out.println("====결과값 : " +rs.getString(1));
						if(rs.getString(1).equals(userID)) {
							result = false; //아이디 중복
							System.out.println("아이디 중복");
						}
						return result;
					}	
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
			         if (rs != null)
			             try {
			                rs.close();
			             } catch (SQLException ex) {
			             }
			          if (pstmt != null)
			             try {
			                pstmt.close();
			             } catch (SQLException ex) {
			             }
			          if (conn != null)
			             try {
			                conn.close();
			             } catch (SQLException ex) {
			             }
			       }
				
				System.out.println("result = " + result);
				
				return result;
			}
		   
		 
		   
		   //닉네임 중복확인
		   public boolean userNickCheck(String userNick) {
				
				String SQL = "SELECT userNick FROM user WHERE userNick = ?";
				boolean result = true;
				try {
					
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, userNick);
					rs = pstmt.executeQuery();
					
					
					if(rs.next()) {
						System.out.println("====결과값 : " +rs.getString(1));
						if(rs.getString(1).equals(userNick)) {
							result = false; //아이디 중복
							System.out.println("아이디 중복");
						}
						return result;
					}	
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
			         if (rs != null)
			             try {
			                rs.close();
			             } catch (SQLException ex) {
			             }
			          if (pstmt != null)
			             try {
			                pstmt.close();
			             } catch (SQLException ex) {
			             }
			          if (conn != null)
			             try {
			                conn.close();
			             } catch (SQLException ex) {
			             }
			       }
				
				System.out.println("result = " + result);
				
				return result;
			}
		   
		 //이메일로 비밀번호 보내기
		   public boolean updateUserPw2(UserBean userBean) throws SQLException{

			   String SQL = "UPDATE user set userPassword=? WHERE userID=?";

			   try {
			      conn = ds.getConnection();
			      pstmt = conn.prepareStatement(SQL);
			      

			     pstmt.setString(1, userBean.getUserPassword());
			     pstmt.setString(2, userBean.getUserID());
			      
		          pstmt.executeUpdate();
		          
		           return true;
		       } catch (Exception ex) {
		           System.out.println("updateUserPw 에러: " + ex);
		       } finally {
		          if (rs != null)
		             try {
		                rs.close();
		             } catch (SQLException ex) {
		             }
		          if (pstmt != null)
		             try {
		                pstmt.close();
		             } catch (SQLException ex) {
		             }
		          if (conn != null)
		             try {
		                conn.close();
		             } catch (SQLException ex) {
		             }
		       }
			return false;
		   }
		   
		   //아이디찾기할때 이름의 존재여부 확인
		   public boolean userNameCheck(String userName) {
				
				String SQL = "SELECT userName FROM user WHERE userName = ?";
				boolean result = true;
				try {
					
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, userName);
					rs = pstmt.executeQuery();
					
					
					if(rs.next()) {
						System.out.println("====결과값 : " +rs.getString(1));
						if(rs.getString(1).equals(userName)) {
							result = false; //이름 중복
							System.out.println("이름 중복");
						}
						return result;
					}	
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
			         if (rs != null)
			             try {
			                rs.close();
			             } catch (SQLException ex) {
			             }
			          if (pstmt != null)
			             try {
			                pstmt.close();
			             } catch (SQLException ex) {
			             }
			          if (conn != null)
			             try {
			                conn.close();
			             } catch (SQLException ex) {
			             }
			       }
				
				System.out.println("result = " + result);
				
				return result;
			}
		   
		 //유저 상세정보
			public UserBean useridfind(String userName) {
			      String sql = "SELECT userID FROM user WHERE userName=?";

			      try {
			         conn = ds.getConnection();
			         pstmt = conn.prepareStatement(sql);
			         pstmt.setString(1, userName);
			         rs = pstmt.executeQuery();
			         rs.next();

			        UserBean ub = new UserBean();
			        ub.setUserID(rs.getString("userID"));
			        

			         return ub;
			      } catch (Exception ex) {
			         System.out.println("getDeatilMember 에러: " + ex);
			      } finally {
			         if (rs != null)
			            try {
			               rs.close();
			            } catch (SQLException ex) {
			            }
			         if (pstmt != null)
			            try {
			               pstmt.close();
			            } catch (SQLException ex) {
			            }
			         if (conn != null)
			            try {
			               conn.close();
			            } catch (SQLException ex) {
			            }
			      }

			      return null;
			   }
		   
		   //관리자 유저 상세 보기
			public UserBean adminUserDetail(String userID) {
				
				String sql = "select *from user where userID = ?";
				
				UserBean userBean = null;
				try {
					
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, userID);
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
						userBean = new UserBean();
						userBean.setUserID(rs.getString("userID"));
						userBean.setUserName(rs.getString("userName"));
						userBean.setUserNick(rs.getString("userNick"));
						userBean.setUserPhone1(rs.getString("userPhone"));
						userBean.setUserGender(rs.getString("userGender"));
						userBean.setUserDate1(rs.getString("userDate"));
						userBean.setUserEmail1(rs.getString("userEmail"));
						userBean.setUserAddress1(rs.getString("userAddress"));
						userBean.setUserDate3(rs.getString("userJoindate"));
						
					}
					
					return userBean;
				} catch (Exception ex) {
			         System.out.println("getDeatilMember 에러: " + ex);
			      } finally {
			         if (rs != null)
			            try {
			               rs.close();
			            } catch (SQLException ex) {
			            }
			         if (pstmt != null)
			            try {
			               pstmt.close();
			            } catch (SQLException ex) {
			            }
			         if (conn != null)
			            try {
			               conn.close();
			            } catch (SQLException ex) {
			            }
			      }
				
				return null;
			}
		   
		   
}
