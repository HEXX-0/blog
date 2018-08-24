package com.cos.controller.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.MemberDAO;
import com.cos.dto.MemberVO;
import com.cos.util.SHA256;
import com.cos.util.Script;

public class MemberUpdateProcAction2 implements Action{
	private static String naming = "MemberUpdateProcAction : ";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "Member?cmd=member_update";
		
		
		MemberDAO dao = new MemberDAO();
		MemberVO member = new MemberVO();
		
		String id=null;
		String password=null;
		String roadFullAddr=null;
		String email=null;
		String salt = null;
		Boolean emailcheck = null;
	
		if(request.getParameter("id")!=null) id = request.getParameter("id");
		if(request.getParameter("password")!=null) {
			salt = dao.select_salt(id);
			password = request.getParameter("password");
			password = SHA256.getEncrypt(password, salt);
			System.out.println("업뎃패스"+password);
		}
		if(request.getParameter("roadFullAddr")!=null) roadFullAddr = request.getParameter("roadFullAddr");
		if(request.getParameter("email")!=null) email = request.getParameter("email");
		
		member.setId(id);
		member.setPassword(password);
		member.setRoadFullAddr(roadFullAddr);
		member.setEmail(email);
		member.setSalt(salt);
		
		//패스워드 유효성 검사를 위해 셀렉트, 이메일 체크 초기화
		MemberVO check  = dao.select(id);
		System.out.println("체크패스"+check.getPassword());
		
		//update 함수 실행.
		if(password.equals(check.getPassword())) {
			
			if(email.equals(check.getEmail())) {
				emailcheck = true;
				member.setEmailcheck(emailcheck);
			}else {
				emailcheck = false;
				member.setEmailcheck(emailcheck);
			}
			
			//실행 체크
			int result = dao.update(member);
			
			if(result == -1) {
				Script.moving(response, "DB Error");
			}else {
				Script.moving(response, "정보수정 성공!", url);
			}
		}else {
			Script.moving(response, "정보수정을 위해서 비밀번호 입력을 해주세요.");
		}
		
	}
}
