package com.cos.controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.BoardDAO;
import com.cos.dao.MemberDAO;
import com.cos.dto.BoardVO;
import com.cos.util.Script;

public class BoardWriteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "index.jsp";
		
		BoardVO board = new BoardVO();
		BoardDAO dao = new BoardDAO();
		MemberDAO mdao = new MemberDAO();
		
		String id = null;
		HttpSession session = request.getSession();

		if(session.getAttribute("id")!=null) {
			
			id = (String)session.getAttribute("id");
			int mResult = mdao.select_emailcheck(id);
			
			if(mResult != 1) {
				Script.moving(response, "이메일 인증이 필요합니다.","Member?cmd=member_update");
			}else {
				board.setId(id);
				board.setTitle(request.getParameter("title"));
				board.setContent(request.getParameter("content"));
				
				int result = dao.insert(board);
				
				if(result == 1) {
					Script.moving(response, "글쓰기 성공", url);
				}else if (result == -1) {
					Script.moving(response, "DB error");
				}
				
			}
			
		}else if(session.getAttribute("id")==null) {
			Script.moving(response, "글쓰기를 위해서는 로그인이 필요합니다.", "member/loginForm.jsp");
		}

	}
}
