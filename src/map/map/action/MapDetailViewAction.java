package map.map.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import map.db.MapBean;
import map.db.MapDAO;

public class MapDetailViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
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
		
		
		int mapNum = Integer.parseInt(request.getParameter("mapNum"));
		
		mapBean = mapDAO.getMapDetail(mapNum);
		
		json.put("mapNum", mapBean.getMapNum());
		json.put("mapNick", mapBean.getMapNick());
		json.put("mapCategori", mapBean.getMapCategori());
		json.put("mapRstName", mapBean.getMapRstName());
		json.put("mapMenu", mapBean.getMapMenu());
		json.put("mapTel", mapBean.getMapTel());
		json.put("mapMove", mapBean.getMapMove1());
		json.put("address", mapBean.getAddress());
		json.put("mapRstMenu", mapBean.getMapRstMenu());
    	//move2 등록일
		json.put("Date", mapBean.getMapMove2());
		json.put("lat", mapBean.getLat());
		json.put("lng", mapBean.getLng());
		
		out.print(json.toString());
		

	}

}
