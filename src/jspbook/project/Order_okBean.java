package jspbook.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order_okBean {
	// �����ͺ��̽� ������� ���� ����
	Connection conn = null;
	PreparedStatement pstmt = null;

	// �����ͺ��̽� ������������� ���ڿ��� ����
	String jdbc_driver = "oracle.jdbc.driver.OracleDriver";
	String jdbc_url = "jdbc:oracle:thin:@localhost:1521:orcl";

	// �����ͺ��̽� ���� �޼���
	void connect() {
		// JDBC ����̹� �ε�
		try {
			Class.forName(jdbc_driver);
			// �����ͺ��̽� ���������� �̿��� Connection �ν��Ͻ� Ȯ��
			conn = DriverManager.getConnection(jdbc_url, "project", "project");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �����ͺ��̽� ���� ���� �޼���
	void disconnect() {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//

	/*
	 * public boolean insertDB(Order_ok order_ok) { connect();
	 * 
	 * //String strSQL = "SELECT Max(order_id) FROM goods"; String sql =
	 * "insert into order_ok(order_id, code, amount) values(?,?,?)";
	 * 
	 * try {
	 * 
	 * pstmt = conn.prepareStatement(sql); pstmt.setInt(1,
	 * order_ok.getOrder_id()); //goods���� num���� �ִ밪�� �޾ƿ� �� ���� 1���Ѱ���
	 * �� ���� num���� ��� pstmt.setString(2, order_ok.getCode());
	 * pstmt.setInt(3, order_ok.getAmount()); pstmt.executeUpdate(); } catch
	 * (SQLException e) { e.printStackTrace(); return false; } finally {
	 * disconnect(); } return true; }
	 */

	public int getOrder_id() {
		connect();

		String sql = "SELECT Max(order_id) FROM order_ok";

		int order_id = 0;

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			order_id = rs.getInt(1);
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return order_id;
	}

	public Order_ok getDB(int order_id, String code) {
		connect();

		String sql = "SELECT * FROM order_ok where order_id=? and code=?";

		Order_ok order_ok = new Order_ok();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_id);
			pstmt.setString(2, code);
			ResultSet rs = pstmt.executeQuery();

			rs.next();
			order_ok.setOrder_id(rs.getInt("order_id"));
			order_ok.setCode(rs.getString("code"));
			order_ok.setAmount(rs.getInt("amount"));
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return order_ok;
	}

	public ArrayList getDBList(int order_id) {
		connect();

		ArrayList<Order_ok> datas = new ArrayList<Order_ok>();

		String sql = "select * from Order_ok where order_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_id);
			ResultSet rs = pstmt.executeQuery();
			// ������ ���� ResultSet �� �̵��ϸ鼭 ��� ������ row �� �ϳ��� ������
			// �ͼ� GuestBook ��ü�� �ְ� �̸� �ٽ� ArrayList �� �ִ� �۾��� �ݺ�.
			while (rs.next()) {
				Order_ok order_ok = new Order_ok();
				order_ok.setOrder_id(rs.getInt("order_id"));
				order_ok.setCode(rs.getString("code"));
				order_ok.setAmount(rs.getInt("amount"));
				datas.add(order_ok);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		// ó���� ���� ArrayList �� ������.
		return datas;
	}

	// ������ ������ ���� �޼���
	public boolean updateDB(Order_ok order_ok) {
		connect();

		String sql = "update order_ok set amount=? where order_id=? and code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_ok.getAmount());
			pstmt.setInt(2, order_ok.getOrder_id());
			pstmt.setString(3, order_ok.getCode());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

	// ������ ������ ���� �޼���
	public boolean deleteDB(int order_id) {
		connect();

		try {

			String sql = "delete from order_ok where order_id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_id);
			pstmt.executeUpdate();

			String sql2 = "delete from order_user where order_id=?";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, order_id);
			pstmt.executeUpdate();
			// String sql2 = "select order_id from order_ok where order_id=?";

			// pstmt = conn.prepareStatement(sql2);
			// pstmt.setInt(1, order_id);
			// pstmt.executeUpdate();

			// String sql3 ="delete from order_user where order_id=?";

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

}
