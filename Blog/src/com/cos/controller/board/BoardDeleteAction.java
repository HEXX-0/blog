package com.cos.controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.BoardDAO;
import com.cos.dao.MemberDAO;
import com.cos.dto.BoardVO;
import com.cos.util.MyUtil;
import com.cos.util.Script;
import com.cos.websocket.BroadSocket;
import com.google.gson.Gson;

public class BoardDeleteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "index.jsp"; 

		int num = Integer.parseInt(request.getParameter("num"));
		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("id");

		BoardDAO dao = new BoardDAO();
		
		//hotpost체크
		boolean change = false;
		List<BoardVO> hotpost1 = dao.select_hotpost();
		
		int checkId = dao.checkId(num, sessionId);
		if (checkId == 1) {
			int result = dao.delete(num);
			if (result == 1) {
				Script.moving(response, "게시글 삭제 완료!", url);
			} else {
				Script.moving(response, "DB error");
			}
		}else {
			Script.moving(response, "잘못된 접근입니다.");
		}
		
		List<BoardVO> hotpost2 = dao.select_hotpost(); 
		change = MyUtil.getBoardChange(hotpost1, hotpost2);
		
		if(change) {
			Gson gson = new Gson();
			String json = gson.toJson(hotpost2);
			BroadSocket.serverMessage(json);
		}

	}
}
