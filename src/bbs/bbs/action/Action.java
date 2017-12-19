package bbs.bbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.bbs.action.ActionForward;

public interface Action {
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception;

	public void executeAjax(HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	
}
