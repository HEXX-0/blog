package com.cos.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Script {
	public static void moving(HttpServletResponse response, String msg) throws ServletException, IOException{
		try {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('"+msg+"')");
			script.println("history.back();");
			script.println("</script>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moving(HttpServletResponse response, String msg, String url) throws ServletException, IOException{
		try {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('"+msg+"')");
			script.println("location.href='"+url+"'");
			script.println("</script>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
