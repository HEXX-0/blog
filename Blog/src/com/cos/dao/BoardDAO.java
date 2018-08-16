package com.cos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.dto.BoardVO;
import com.cos.util.DBManager;

public class BoardDAO {
	
	private PreparedStatement ps;
	private ResultSet rs;
	
	
	//insert
		public int insert(BoardVO board) {
			String sql = "INSERT INTO board(title, content, writedate, id) VALUES(?,?,now(),?)";
			Connection conn = DBManager.getConnection();
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, board.getTitle());
				ps.setString(2, board.getContent());
				ps.setString(3, board.getId());
				ps.executeUpdate();
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				DBManager.close(conn, ps);
			}
			return -1;
		}
}
