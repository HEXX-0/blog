package com.cos.controller.reboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.dao.ReBoardDAO;
import com.cos.util.Script;

public class ReBoardDeleteAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		int renum = Integer.parseInt(request.getParameter("renum"));
		
		String url = "Board?cmd=board_view&num="+num;
		
		ReBoardDAO dao = new ReBoardDAO();
		int result = dao.delete(num, renum);
		
		if(result == 1) {
			Script.moving(response, "삭제됨", url);
		}else if(result == -1){
			Script.moving(response, "DB 에러");
		}
	}
}
