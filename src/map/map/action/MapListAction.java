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

public class MapListAction implements Action {

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
		
		List maplist = new ArrayList();
		maplist = mapDAO.mapList();
		
		request.setAttribute("maplist", maplist);
		//JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		for(int i=0;i<maplist.size();i++) {
			mapBean = (MapBean)maplist.get(i);
			JSONObject obj = new JSONObject();
			
			obj.put("mapNum", mapBean.getMapNum());
			obj.put("mapNick", mapBean.getMapNick());
    		obj.put("mapCategori", mapBean.getMapCategori());
    		obj.put("mapRstName", mapBean.getMapRstName());
    		obj.put("mapMenu", mapBean.getMapMenu());
    		obj.put("mapTel", mapBean.getMapTel());
    		obj.put("mapMove", mapBean.getMapMove1());
    		obj.put("address", mapBean.getAddress());
    		obj.put("mapRstMenu", mapBean.getMapRstMenu());
    		//move2 등록일
    		obj.put("Date", mapBean.getMapMove2());
			obj.put("lat", mapBean.getLat());
			obj.put("lng", mapBean.getLng());
			obj.put("mapAverage", mapBean.getMapAverage());
			//더추가할꺼 잇으면 넣고 인자 됏지?  ㅇㅋ 감사 이것떄매 겁나 해맴 ㅠㅠ ㅡㅡ;; 첨에 알려준건디 ㅋ 저거랑 다른건줄;;.. ㅇㅇ ㅅㄱ 
			array.add(obj);
//			json.put("mapNum", mapBean.getMapNum()); 
		}
		
		json.put("array", array); 
		json.put("result", "success");//받는놈어디냐?
		
		out.print(json.toString());
		

	}

}
