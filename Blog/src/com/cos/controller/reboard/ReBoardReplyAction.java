package com.cos.controller.reboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.dao.ReBoardDAO;
import com.cos.dto.ReBoardVO;
import com.google.gson.Gson;
public class ReBoardReplyAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BufferedReader in = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line = in.readLine()) != null) {
			sb.append(line);
		}
		System.out.println("ajax data : "+sb.toString());
	
		Gson gson = new Gson();
		ReBoardVO reboard = gson.fromJson(sb.toString(), ReBoardVO.class);
		
		//DB저장
		ReBoardDAO dao = new ReBoardDAO();
		int auto_increment = dao.select_renum(reboard.getNum());
		reboard.setRenum(auto_increment);
		int result = dao.insert(reboard);
		
		String data = gson.toJson(reboard);
		
		if(result == 1) {
			PrintWriter out = response.getWriter();
			out.println(data);
		}
	}
}
