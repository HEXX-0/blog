package com.cos.controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.MemberDAO;
import com.cos.dto.MemberVO;
import com.cos.util.SHA256;
import com.cos.util.Script;

public class MemberLoginAction implements Action {
	private static String naming = "MemberLoginAction : ";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "/Blog/index.jsp";

		MemberVO member = new MemberVO();
		MemberDAO dao = new MemberDAO();

		String id = null;
		String password = null;

		if (request.getParameter("id") != null)
			id = request.getParameter("id");
		if (request.getParameter("password") != null) {
			password = request.getParameter("password");
			String salt = dao.select_salt(id);
			password = SHA256.getEncrypt(password, salt);
		}

		member.setId(id);
		member.setPassword(password);
		
		//로그인 체크
		int result = dao.loginCheck(member);
		System.out.println(naming + result);
		int checked = dao.select_emailcheck(id);
		System.out.println(naming + checked);
		if (result == 1 && checked == 1) {
			Script.moving(response, "로그인 성공!!", url);
		} else if (result == 1 && checked == 2) {
			Script.moving(response, "로그인 성공(이메일 미인증 회원입니다)", url);
		} else if (result == -1) {
			Script.moving(response, "DB ERROR");
		}
		//로그인 상태  아이디 세션 저장
		if (id != null) {
			HttpSession session = request.getSession();
			session.setAttribute("id", member.getId());
		}
		//쿠키설정
		Cookie cookie = null;
		String check = request.getParameter("idsave");
		if(check !=null && check.trim().equals("on")){
		cookie = new Cookie("cookieID",id);
		cookie.setMaxAge(600);
		response.addCookie(cookie);
		}else{
			cookie = new Cookie("cookieID",null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

	}
}
