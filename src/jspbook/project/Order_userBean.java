package jspbook.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order_userBean {
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

	public boolean insertToUser(Order_user order_user) {
		connect();

		String strSQL = "SELECT Max(order_id) FROM order_user";
		String sql = "insert into order_user(order_id, userid, name, address, phone) values(?,?,?,?,?)";

		try {

			PreparedStatement pstmt1 = conn.prepareStatement(strSQL);
			ResultSet rs = pstmt1.executeQuery();
			rs.next();
			int num = rs.getInt(1) + 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, order_user.getUserid());
			pstmt.setString(3, order_user.getName());
			pstmt.setString(4, order_user.getAddress());
			pstmt.setString(5, order_user.getPhone());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}

		return true;
	}

	public boolean insertToOk(Order_user order_user) {
		connect();

		String strSQL = "SELECT Max(order_id) FROM order_ok";
		String sql = "insert into order_ok(order_id, amount, code) values(?,?,?)";

		try {
			PreparedStatement pstmt1 = conn.prepareStatement(strSQL);
			ResultSet rs = pstmt1.executeQuery();
			rs.next();
			int num = rs.getInt(1) + 1;

			CartBean cb = new CartBean();
			ArrayList<Cart> datas = cb.getCartList(order_user.getUserid());

			for (Cart cart : datas) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setInt(2, cart.getAmount());
				pstmt.setString(3, cart.getCode());
				pstmt.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}

		return true;
	}

	public boolean deleteToCart() {
		connect();

		try {

			String sql = "delete from cart";

			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

	public ArrayList getAllDBList() {
		connect();

		ArrayList<Order_user> datas = new ArrayList<Order_user>();

		String sql = "select * from Order_user order by order_id desc";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Order_user order_user = new Order_user();
				order_user.setOrder_id(rs.getInt("order_id"));
				order_user.setUserid(rs.getString("userid"));
				order_user.setName(rs.getString("name"));
				order_user.setAddress(rs.getString("address"));
				order_user.setPhone(rs.getString("phone"));
				order_user.setTotal_price(rs.getInt("total_price"));
				datas.add(order_user);
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

	public ArrayList getDBList(String userid) {
		connect();

		ArrayList<Order_user> datas = new ArrayList<Order_user>();

		String sql = "select * from Order_user  where userid=? order by order_id desc";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Order_user order_user = new Order_user();
				order_user.setOrder_id(rs.getInt("order_id"));
				order_user.setUserid(rs.getString("userid"));
				order_user.setName(rs.getString("name"));
				order_user.setAddress(rs.getString("address"));
				order_user.setPhone(rs.getString("phone"));
				order_user.setTotal_price(rs.getInt("total_price"));
				datas.add(order_user);
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

	public Order_user getDB(int order_id) {
		connect();

		String sql = "select * from order_user where order_id=?";

		Order_user order_user = new Order_user();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_id);
			ResultSet rs = pstmt.executeQuery();

			rs.next();
			order_user.setOrder_id(rs.getInt("order_id"));
			order_user.setUserid(rs.getString("userid"));
			order_user.setName(rs.getString("name"));
			order_user.setAddress(rs.getString("address"));
			order_user.setPhone(rs.getString("phone"));
			order_user.setTotal_price(rs.getInt("total_price"));
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return order_user;
	}

	// ������ ������ ���� �޼���
	public boolean updateDB(Order_user order_user) {
		connect();

		String sql = "update order_user set userid=?, name=?, address=?, phone=?, amount=? where order_id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			// ���ڷ� ���� GuestBook ��ü�� �̿��� ����ڰ� ������ ���� ������ SQL��
			// �ϼ�
			pstmt.setString(1, order_user.getUserid());
			pstmt.setString(2, order_user.getName());
			pstmt.setString(3, order_user.getAddress());
			pstmt.setString(4, order_user.getPhone());
			pstmt.setInt(5, order_user.getTotal_price());
			pstmt.setInt(6, order_user.getOrder_id());
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
			String sql = "delete from order_user where order_id=?";

			pstmt = conn.prepareStatement(sql);
			// ���ڷ� ���� gb_id �����̸Ӹ� Ű ���� �̿��� ����
			pstmt.setInt(1, order_id);
			pstmt.executeUpdate();

			String sql2 = "UPDATE order_user  SET order_id = order_id - 1 WHERE order_id > ?";

			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, order_id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

	// ��ǰ �˻��� ���� �޼���
	public ArrayList getsearchDBlist(String strTitle, String strCont) {
		connect();

		ArrayList<Order_user> datas = new ArrayList<Order_user>();
		String sql;

		sql = "SELECT order_id, userid, name, address, phone, price from order_user WHERE " + strTitle + " LIKE '%"
				+ strCont + "%'";

		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Order_user order_user = new Order_user();
				order_user.setOrder_id(rs.getInt("order_id"));
				order_user.setUserid(rs.getString("userid"));
				order_user.setName(rs.getString("name"));
				order_user.setAddress(rs.getString("address"));
				order_user.setPhone(rs.getString("phone"));
				order_user.setTotal_price(rs.getInt("total_price"));

				datas.add(order_user);
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

}
