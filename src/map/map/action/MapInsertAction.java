package map.map.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import map.map.action.ActionForward;
import map.db.*;

public class MapInsertAction implements Action{
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
			 	throws Exception{
		 	return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		
		MapDAO mapDAO = new MapDAO();
		MapBean mapBean = new MapBean();
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		boolean result = false;
		
		mapBean.setMapNick(request.getParameter("mapNick"));
		mapBean.setMapCategori(request.getParameter("mapCategori"));
		mapBean.setMapRstName(request.getParameter("mapRstName"));
		mapBean.setMapMenu(request.getParameter("mapMenu"));
		mapBean.setMapTel(request.getParameter("mapTel"));
		mapBean.setMapMove1(request.getParameter("mapMove1"));
		mapBean.setMapMove2(request.getParameter("mapMove2"));
		mapBean.setLat(Float.parseFloat(request.getParameter("lat")));
		mapBean.setLng(Float.parseFloat(request.getParameter("lng")));
		mapBean.setAddress(request.getParameter("address"));
		mapBean.setMapRstMenu(request.getParameter("mapRstMenu"));
   		
   		result=mapDAO.MapInsert(mapBean);
   		
   		if(result == false){
   			System.out.println("맛집 등록 실패");
	   	}
		
   		
		out.print(json.toString());
		
		
	}
}
