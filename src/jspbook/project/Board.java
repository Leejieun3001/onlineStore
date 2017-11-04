package jspbook.project;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.*;

public class Board {
	//
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

	private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:orcl";

	private static final String USER = "project";
	private static final String PASSWD = "project";

	//
	private Connection con = null;
	private PreparedStatement pstmt = null;
	// private DataSource ds = null;

	//
	public Board() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	public void connect() {
		try {
			con = DriverManager.getConnection(JDBC_URL, USER, PASSWD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	public void disconnect() {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//
	public ArrayList<BoardEntity> getBoardList() {
		connect();
		ArrayList<BoardEntity> list = new ArrayList<BoardEntity>();

		String SQL = "select * from board order by id desc";
		try {
			pstmt = con.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardEntity brd = new BoardEntity();
				brd.setId(rs.getInt("id"));
				brd.setName(rs.getString("name"));
				brd.setPasswd(rs.getString("passwd"));
				brd.setTitle(rs.getString("title"));
				brd.setEmail(rs.getString("email"));
				brd.setRegdate(rs.getTimestamp("regdate"));
				brd.setContent(rs.getString("content"));
				//
				list.add(brd);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	//
	public BoardEntity getBoard(int id) {
		connect();
		String SQL = "select * from board where id = ?";
		BoardEntity brd = new BoardEntity();

		try {
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			brd.setId(rs.getInt("id"));
			brd.setName(rs.getString("name"));
			brd.setPasswd(rs.getString("passwd"));
			brd.setTitle(rs.getString("title"));
			brd.setEmail(rs.getString("email"));
			brd.setRegdate(rs.getTimestamp("regdate"));
			brd.setContent(rs.getString("content"));
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return brd;
	}

	//
	public boolean insertDB(BoardEntity board) {
		boolean success = false;
		connect();
		try {
			String strSQL = "SELECT Max(id) FROM board";
			pstmt = con.prepareStatement(strSQL);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int num = rs.getInt(1) + 1;

			String sql = "insert into board values(?, ?, ?, ?, ?, sysdate, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getName());
			pstmt.setString(3, board.getPasswd());
			pstmt.setString(4, board.getTitle());
			pstmt.setString(5, board.getEmail());
			pstmt.setString(6, board.getContent());
			pstmt.executeUpdate();
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		} finally {
			disconnect();
		}
		return success;
	}

	//
	public boolean updateDB(BoardEntity board) {
		boolean success = false;
		connect();
		String sql = "update board set  title=?, email=?, content=? where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			//
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getEmail());
			pstmt.setString(4, board.getContent());
			pstmt.setInt(5, board.getId());
			int rowUdt = pstmt.executeUpdate();
			// System.out.println(rowUdt);
			if (rowUdt == 1)
				success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		} finally {
			disconnect();
		}
		return success;
	}

	//
	public boolean deleteDB(int id) {
		boolean success = false;
		connect();
		String sql = "delete from board where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			//
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		} finally {
			disconnect();
		}
		return success;
	}

	//
	public boolean isPasswd(int id, String passwd) {
		boolean success = false;
		connect();
		String sql = "select passwd from board where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String orgPasswd = rs.getString(1);
			if (passwd.equals(orgPasswd))
				success = true;
			System.out.println(success);
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		} finally {
			disconnect();
		}
		return success;
	}
}
