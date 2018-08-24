package com.cos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.dto.ReBoardVO;
import com.cos.util.DBManager;

public class ReBoardDAO {
	PreparedStatement ps;
	ResultSet rs;
	
	public int select_renum(int num) {
		String SQL = "SELECT MAX(renum) FROM reboard WHERE num = ?";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				int auto_increment = rs.getInt(1);
				return auto_increment+1;
			}else {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps, rs);
		}
		return -1;
	}
	
	public int insert(ReBoardVO reboard) {
		String SQL = "INSERT INTO reboard VALUES(?,?,?,now(),?)";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, reboard.getRenum());
			ps.setString(2, reboard.getId());
			ps.setString(3, reboard.getRecontent());
			ps.setInt(4, reboard.getNum());
			ps.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps, rs);
		}
		return -1;
	}
	
	public List<ReBoardVO> select_list(int num) {
		String SQL = "SELECT * FROM reboard WHERE num = ? ORDER BY renum DESC";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			
			List<ReBoardVO> list = new ArrayList<>();
			while(rs.next()) {
				ReBoardVO reboard = new ReBoardVO();
				reboard.setRenum(rs.getInt("renum"));
				reboard.setId(rs.getString("id"));
				reboard.setRecontent(rs.getString("recontent"));
				reboard.setWritedate(rs.getString("writedate"));
				reboard.setNum(rs.getInt("num"));
				list.add(reboard);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps, rs);
		}
		return null;
	}
	
	//1번 쿼리문 변경
	//2번 ? 채우기
	public int delete(int num, int renum) {
		String SQL = "DELETE FROM reboard WHERE renum = ? and num = ?";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, renum); //문장이 완성됨
			ps.setInt(2, num);
			//DML 데이터 조작어 (DB변경) - DELETE, UPDATE, INSERT
			ps.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps);
		}
		
		return -1;
	}
}
