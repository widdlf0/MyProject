package map.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import map.db.MapBean;

public class MapDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;

	public MapDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/mysql");
		} catch (Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
			return;
		}
	}
	
	public int getNext() { // 글 번호를 메길 때

		String SQL = "SELECT mapNum FROM map ORDER BY mapNum DESC";
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 두 번째 이상 게시물 부터
				return rs.getInt(1) + 1;
			}
			return 1; // 첫 번째 게시물인 경우 return 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류시 return -1;
	}

	// 맛집 등록
	public boolean MapInsert(MapBean map) {
		String sql = "insert into map (mapNum, mapNick, mapCategori, mapRstName, mapMenu, mapTel, "
				+ "mapMove, lat, lng, address, mapRstMenu, Date, mapAverage, mapCount, mapAverageplus) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(),?,?,?)";

		int result = 0;

		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, map.getMapNick());
			pstmt.setString(3, map.getMapCategori());
			pstmt.setString(4, map.getMapRstName());
			pstmt.setString(5, map.getMapMenu());
			pstmt.setString(6, map.getMapTel());
			pstmt.setString(7, map.getMapMove1()+"~"+map.getMapMove2());
			pstmt.setFloat(8, map.getLat());
			pstmt.setFloat(9, map.getLng());
			pstmt.setString(10, map.getAddress());
			pstmt.setString(11, map.getMapRstMenu());
			pstmt.setInt(12, 0);
			pstmt.setInt(13, 0);
			pstmt.setInt(14, 0);
			
			result = pstmt.executeUpdate();
			if (result == 0)
				return false;

			return true;
		} catch (Exception ex) {
			System.out.println("mapInsert 에러 : " + ex);
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

//	// 맛집 내용 보기
//	public MapBean getMapDetail() throws Exception {
//		MapBean mapBean = null;
//		try {
//			conn = ds.getConnection();
//			pstmt = conn.prepareStatement("select * from map");
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				mapBean = new MapBean();
//				mapBean.setMapStoreName(rs.getString("mapStoreName"));
//				mapBean.setMaplat(rs.getFloat("maplat"));
//				mapBean.setMaplng(rs.getFloat("maplng"));
//
//			}
//			return mapBean;
//		} catch (Exception ex) {
//			System.out.println("getMapDetail 에러 : " + ex);
//		} finally {
//			if (rs != null)
//				try {
//					rs.close();
//				} catch (SQLException ex) {
//				}
//			if (pstmt != null)
//				try {
//					pstmt.close();
//				} catch (SQLException ex) {
//				}
//			if (conn != null)
//				try {
//					conn.close();
//				} catch (SQLException ex) {
//				}
//		}
//		return null;
//	}

	// 맛집 삭제
	public boolean MapDelete(int num) {
		String SQL = "delete from map";

		int result = 0;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(SQL);
			/*pstmt.setInt(1, num);*/
			result = pstmt.executeUpdate();
			if (result == 0)
				return false;

			return true;
		} catch (Exception ex) {
			System.out.println("MapDelete 에러 : " + ex);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}

		return false;
	}

	//맵 리스트 가져오기
	public ArrayList<MapBean> mapList(){
		
		ArrayList<MapBean> list = new ArrayList<MapBean>();
		
		try {
			conn=ds.getConnection();
			String SQL="select *from map";
        	
        	pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            
	        while(rs.next())
	        {	
	        	MapBean mapBean = new MapBean();
	        	mapBean.setMapNum(rs.getInt("mapNum"));
	        	mapBean.setMapNick(rs.getString("mapNick"));
	        	mapBean.setMapCategori(rs.getString("mapCategori"));
	    		mapBean.setMapRstName(rs.getString("mapRstName"));
	    		mapBean.setMapMenu(rs.getString("mapMenu"));
	    		mapBean.setMapTel(rs.getString("mapTel"));
	    		mapBean.setMapMove1(rs.getString("mapMove"));
	    		mapBean.setLat(rs.getFloat("lat"));
	    		mapBean.setLng(rs.getFloat("lng"));
	    		mapBean.setAddress(rs.getString("address"));
	    		mapBean.setMapRstMenu(rs.getString("mapRstMenu"));
	    		//등록일
	    		mapBean.setMapMove2(rs.getString("Date"));
	    		mapBean.setMapAverage(rs.getInt("mapAverage"));
	    		mapBean.setMapCount(rs.getInt("mapCount"));
	    		list.add(mapBean);
	    		
	        
	        }
	        
	        return list;
		}catch(Exception ex){
			System.out.println("mapList 에러 : " + ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
			if(conn!=null) try{conn.close();}catch(SQLException ex){}
		}
		return null;
	}
	
	public MapBean getMapDetail(int mapNum) {
		
		String SQL = "select *from map where mapNum = ?";
		MapBean mapBean = new MapBean();
		try {
			conn=ds.getConnection();
        	pstmt = conn.prepareStatement(SQL);
        	pstmt.setInt(1, mapNum);
            rs = pstmt.executeQuery();
            if(rs.next()) {
            	if(rs.getInt("mapNum")==mapNum) {
		        	//System.out.println("getMapdetail 값 :" + rs.getString("mapNum") +"받아온값:" +mapNum);
            		mapBean.setMapNum(rs.getInt("mapNum"));
		        	mapBean.setMapNick(rs.getString("mapNick"));
		        	mapBean.setMapCategori(rs.getString("mapCategori"));
		    		mapBean.setMapRstName(rs.getString("mapRstName"));
		    		mapBean.setMapMenu(rs.getString("mapMenu"));
		    		mapBean.setMapTel(rs.getString("mapTel"));
		    		mapBean.setMapMove1(rs.getString("mapMove"));
		    		mapBean.setLat(rs.getFloat("lat"));
		    		mapBean.setLng(rs.getFloat("lng"));
		    		mapBean.setAddress(rs.getString("address"));
		    		mapBean.setMapRstMenu(rs.getString("mapRstMenu"));
		    		//등록일
		    		mapBean.setMapMove2(rs.getString("Date"));
            	}
            }	
	        
	        
		}catch(Exception ex){
			System.out.println("getMapDetail 에러 : " + ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
			if(conn!=null) try{conn.close();}catch(SQLException ex){}
		}
		return mapBean;
		
	}
	
	// 평가하기 댓글 등록
		public void setAverage(int Num, int Average, String Content, String Nick) {
			String sql1 = "insert into mapanswer (mapNumcheck,mapNickcheck,mapAveragecheck,mapContent,Date) "
					+ "values(?,?,?,?,Now())";
			String sql2 = "update map set mapCount=mapCount+1, mapAverageplus=mapAverageplus+? "
					+ "where mapNum like ?";
			
			try {
				conn = ds.getConnection();
				
				pstmt = conn.prepareStatement(sql1);
				pstmt.setInt(1, Num);
				pstmt.setString(2, Nick);
				pstmt.setInt(3, Average);
				pstmt.setString(4, Content);
				pstmt.executeUpdate();
				
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, Average);
				pstmt.setInt(2, Num);
				pstmt.executeUpdate();
				
			} catch (Exception ex) {
				System.out.println("setAverage 에러 : " + ex);
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
		}
		
		// 별점 평균 구하기
				public void setStarAverage(int Num) {
					String sql = "update map set mapAverage = mapAverageplus/mapCount where mapNum like ?";

					try {
						conn = ds.getConnection();
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, Num);
						pstmt.executeUpdate();
						
					} catch (Exception ex) {
						System.out.println("setStarAverage 에러 : " + ex);
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
				}
				
				//맵 리스트 가져오기
				public ArrayList<MapBean> mapAnswerList(int Num){
					
					ArrayList<MapBean> list = new ArrayList<MapBean>();
					
					try {
						conn=ds.getConnection();
						String SQL="select *from mapanswer where mapNumcheck = ?";
			        	
			        	pstmt = conn.prepareStatement(SQL);
			        	pstmt.setInt(1, Num);
			            rs = pstmt.executeQuery();
			            
				        while(rs.next())
				        {	
				        	MapBean mapBean = new MapBean();
				        	mapBean.setMapNick(rs.getString("mapNickcheck"));
				    		//등록일
				    		mapBean.setMapMove2(rs.getString("Date").substring(0,19));
				    		mapBean.setMapAverage(rs.getInt("mapAveragecheck"));
				    		mapBean.setMapContent(rs.getString("mapContent"));
				    		list.add(mapBean);
				    		
				        }
				        
				        return list;
					}catch(Exception ex){
						System.out.println("mapAnswerList 에러 : " + ex);
					}finally{
						if(rs!=null) try{rs.close();}catch(SQLException ex){}
						if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
						if(conn!=null) try{conn.close();}catch(SQLException ex){}
					}
					return null;
				}
}
