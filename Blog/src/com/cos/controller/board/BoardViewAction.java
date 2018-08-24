package com.cos.controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.dao.BoardDAO;
import com.cos.dao.ReBoardDAO;
import com.cos.dto.BoardVO;
import com.cos.dto.ReBoardVO;
import com.cos.util.MyUtil;
import com.cos.util.Script;
import com.cos.websocket.BroadSocket;
import com.google.gson.Gson;

public class BoardViewAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "board/viewPage.jsp";
		
		BoardDAO dao = new BoardDAO();
		ReBoardDAO rdao = new ReBoardDAO();
		
		//조회가 일어나면 웹소켓 통신 실행!! +삭제,수정
		//조회가 일어나기전에 hotpost 를 먼저 검색해서 컬렉션에 담아둬야 새로운것과 비교가능
		//조회실행
		//조회 이후에 hotpost를 들고와서 이전 컬렉션과 for문을 돌면서 비교.
		//변경이 일어났으면 웹소켓 통신 실행 - 모든 클라이언트에게 보냄.
		boolean change = false;
		List<BoardVO> hotpost1 = dao.select_hotpost(); 
		
		int num = Integer.parseInt(request.getParameter("num"));
		BoardVO board = dao.select(num);
		int result = dao.readcount(num);
		
		
		if(result == -1 && board == null) {
			Script.moving(response, "DB Error");
		}else {
			List<ReBoardVO> reboards = rdao.select_list(num);
	        board.setTitle(MyUtil.getReplace(board.getTitle()));
	        board.setContent(MyUtil.makeYoutube(board.getContent()));
	        request.setAttribute("board", board);
	        request.setAttribute("reboards", reboards);
			RequestDispatcher dis = request.getRequestDispatcher(url);
			dis.forward(request, response);
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
