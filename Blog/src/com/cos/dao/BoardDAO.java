package com.cos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.dto.BoardVO;
import com.cos.util.DBManager;

public class BoardDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	// insert
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
		} finally {
			DBManager.close(conn, ps);
		}
		return -1;
	}

	public List<BoardVO> select_paging(int pageNum) {
		String sql = "SELECT * FROM board ORDER BY num DESC limit ?,3";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pageNum);
			rs = ps.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				BoardVO board = new BoardVO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setWritedate(rs.getString("writedate"));
				board.setReadcount(rs.getInt("readcount"));
				list.add(board);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps, rs);
		}
		return null;
	}

	// nextPage 유무
	public int nextPage(int pageNum) {
		String sql = "SELECT * FROM board ORDER BY num DESC limit ?, 3";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pageNum);
			rs = ps.executeQuery();

			if (rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps, rs);
		}
		return -1;
	}

	public BoardVO select(int num) {
		String sql = "SELECT * FROM board WHERE num = ?";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			if (rs.next()) {
				BoardVO board = new BoardVO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setWritedate(rs.getString("writedate"));
				board.setReadcount(rs.getInt("readcount"));
				return board;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps, rs);
		}
		return null;
	}
	
	

	// delete
	public int delete(int num) {
		String sql = "DELETE FROM board WHERE num=?";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			ps.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps);
		}
		return -1;
	}
	
	//update
	public int update(BoardVO board) {
		String sql = "UPDATE board SET title=?, content=? WHERE num=?";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setInt(3, board.getNum());
			ps.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps);
		}
		return -1;
	}
	
	public int checkId(int num, String sessionId) {
		String sql="SELECT * FROM board WHERE id = ? AND num = ?";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, sessionId);
			ps.setInt(2, num);
			rs = ps.executeQuery();
			if(rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, ps, rs);
		}
		return -1;
	}
	//조회수증가
	public int readcount(int num) {
		String sql = "UPDATE board SET readcount = readcount+1 WHERE num=?";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			ps.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps);
		}
		return -1;
	}
	//hotpost
	public List<BoardVO> select_hotpost() {
		String sql = "SELECT num, title, readcount FROM board ORDER BY readcount DESC limit 3";
		Connection conn = DBManager.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				BoardVO board = new BoardVO();
				board.setNum(rs.getInt("num"));
				board.setTitle(rs.getString("title"));
				board.setReadcount(rs.getInt("readcount"));
				list.add(board);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, ps, rs);
		}
		return null;
	}
}
