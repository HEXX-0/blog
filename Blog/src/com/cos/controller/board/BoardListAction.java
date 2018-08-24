package com.cos.controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.dao.BoardDAO;
import com.cos.dto.BoardVO;
import com.cos.util.MyUtil;
import com.cos.util.Script;

public class BoardListAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "main.jsp";
		
		BoardDAO dao = new BoardDAO();
		
		int pageNum = 0;
		if(request.getParameter("pageNum")!=null){
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		List<BoardVO> list = dao.select_paging(pageNum);
		List<BoardVO> hotpost = dao.select_hotpost();
		
		if(list == null) {
			Script.moving(response, "DB Error");
		}else {
			//크로스사이트 공격 방어
			for(int i=0; i<list.size(); i++) {
				String title = MyUtil.getReplace(list.get(i).getTitle());
				list.get(i).setTitle(title);
			}
			//main에 미리보기 content 만들기
			for(int i=0; i<list.size(); i++) {
				String content = list.get(i).getContent();
				list.get(i).setContent(MyUtil.preview(content));
			}
			
			
			request.setAttribute("list", list);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("hotpost", hotpost);
			RequestDispatcher dis = request.getRequestDispatcher(url);
			dis.forward(request, response);
			
		}

	}
}
