package bbs.image.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.image.action.ActionForward;

public interface Action {
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception;

	public void executeAjax(HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	
}
