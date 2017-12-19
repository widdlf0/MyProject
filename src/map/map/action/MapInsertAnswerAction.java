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

public class MapInsertAnswerAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		
		MapDAO mapDAO = new MapDAO();
		
		int Average;
		int Num = Integer.parseInt(request.getParameter("mapNumAns"));
		String Content = request.getParameter("mapContent");
		String Nick = request.getParameter("mapNickcheck");
		
		if(request.getParameter("star-input").equals("null")) {
			Average = 0;
		}else {
			Average = Integer.parseInt(request.getParameter("star-input"));
		}
		
		mapDAO.setAverage(Num, Average, Content, Nick);
		
		mapDAO.setStarAverage(Num);
		
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		json.put("mapNum", Num);
		out.print(json.toString());
		
	}

}

