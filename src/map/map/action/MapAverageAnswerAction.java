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

public class MapAverageAnswerAction implements Action {

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
		//System.out.println("mapNum : "+mapNum);
		List maplist = new ArrayList();
		maplist = mapDAO.mapAnswerList(mapNum);
		
		//JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		for(int i=0;i<maplist.size();i++) {
			mapBean = (MapBean)maplist.get(i);
			JSONObject obj = new JSONObject();
			
			obj.put("mapNickcheck", mapBean.getMapNick());
    		//move2 등록일
    		obj.put("Date", mapBean.getMapMove2());
			obj.put("mapAverage", mapBean.getMapAverage());
			obj.put("mapContent", mapBean.getMapContent());
			
			array.add(obj);
		}
		
		json.put("array", array); 
		
		out.print(json.toString());
		

	}

}
