package com.cos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.dto.MemberVO;
import com.cos.util.DBManager;
import com.mysql.fabric.xmlrpc.base.Member;

public class MemberDAO {
	private PreparedStatement ps;
	private ResultSet rs;
	
	//insert
	public int insert(MemberVO member) {
		String sql = "INSERT INTO member VALUES(?,?,?,?,?,false)";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, member.getId());
			ps.setString(2, member.getPassword());
			ps.setString(3, member.getUsername());
			ps.setString(4, member.getEmail());
			ps.setString(5, member.getSalt());
			ps.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, ps);
		}
		return -1;
	}
	
	//select_emailcheck
	public int select_emailcheck(String id) {
		String sql = "SELECT emailcheck FROM member where id = ?";
		Connection conn = DBManager.getConnection();

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				boolean emailCheck = rs.getBoolean("emailcheck");
				if(emailCheck == true) {
					return 1; // 인증
				}else {
					return 2; // 미인증
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, ps, rs);
		}
		return -1; //error
	}
	
	//select_email
	public String select_email(String id) {
		String sql = "SELECT email FROM member where id = ?";
		Connection conn = DBManager.getConnection();

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				String email = rs.getString("email");
				return email;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, ps, rs);
		}
		return null; //error
	}
	
	public String select_salt(String id) {
		String sql = "SELECT salt FROM member where id = ?";
		Connection conn = DBManager.getConnection();

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				String salt = rs.getString("salt");
				return salt;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, ps, rs);
		}
		return null; //error
	}
	
	//update_emailcheck
	public int update_emailcheck(String id) {
		String sql = "UPDATE member SET emailcheck = true WHERE id = ?";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, ps);
		}
		return -1; //error
	}
	//login_check
	public int loginCheck(MemberVO member) {
		String sql = "SELECT id FROM member WHERE id = ? AND password = ?";
		Connection conn  = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, member.getId());
			ps.setString(2, member.getPassword());
			rs = ps.executeQuery();
			if(rs.next()) return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, ps, rs);
		}
		return -1;
	}
	
}
