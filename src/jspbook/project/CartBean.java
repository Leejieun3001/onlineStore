package jspbook.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartBean {
	//	 �����ͺ��̽� ������� ���� ����
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
			conn = DriverManager.getConnection(jdbc_url,"project","project");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// �����ͺ��̽� ���� ���� �޼���
	void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//
	public ArrayList getCartList(String cartid) {
		connect();

		ArrayList<Cart> datas = new ArrayList<Cart>();
				
		String sql = "select code, amount from Cart where cart_id='"+cartid+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			// 루프를 돌면 ResultSet 을 이동하면서 모든 데이터 row 를 하나씩 가지고 와서 Cart 객체에 넣고
			//이를 다시 ArrayList 에 넣는 작업을 반복.
		
			while(rs.next()) {
				Cart cart = new Cart();
				
				cart.setCart_id(cartid);
				cart.setCode(rs.getString("code"));
				cart.setAmount(rs.getInt("amount"));
				datas.add(cart);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		// 처리가 끝나 ArrayList 를 리턴함.
		return datas;
	}
	
	public Cart getCart(String code) {
		connect();
		
		String sql = "select * from Cart where code=?";

		Cart cart = new Cart();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,code);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			cart.setCart_id(rs.getString("cart_id"));
			cart.setCode(rs.getString("code"));
			cart.setAmount(rs.getInt("amount"));
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return cart;
	}

	
	//��ٱ��Ͽ� ���
	
	public boolean insertCart(Cart cart) {
		connect();
		String sql ="insert into cart(cart_id, code, amount) values(?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,cart.getCart_id());
			pstmt.setString(2,cart.getCode());
			pstmt.setInt(3,cart.getAmount());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	//DB�� ��ǰ�� ��ϵǾ��ִ��� Ȯ��
	public boolean checkCart(String cart_id, String code){
		Boolean result = null;
		connect();
		String sql = "select cart_id from cart where cart_id=? and code=?";
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cart_id);
			pstmt.setString(2, code);
			ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next())
				result = false;
			else
				result = true;
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return result;
	}
	
	//��ٱ��� ������ ���� �޼���
	public boolean updateCart(Cart cart) {
		connect();
		
		String sql ="update cart set amount=? where cart_id=? and code=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(3,cart.getCode());
			pstmt.setInt(1,cart.getAmount());
			pstmt.setString(2,cart.getCart_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	//��ٱ����� ��ǰ  ������ ���� �޼���
	public boolean deleteCartP(Cart cart) {
		connect();
		
		try {
			
			String sql ="delete from cart where cart_id=? and code=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,cart.getCart_id());
			pstmt.setString(2, cart.getCode());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	//��ٱ��� ����
	public boolean deleteCart(String code) {
		connect();
		
		try {
			
			String sql ="delete from cart where code=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,code);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}

	
	
	//ȸ���� ���� ������ üũ�ϱ�
	public int checkOrder(String userid){
		connect();
		
		int order_id = 0;
		String sql = "select order_id from order_user where userid=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,userid);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())	//���� ���� ���� ����
				order_id = rs.getInt("order_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return order_id;
	}
	
	//order_user�� ���� ȸ�� ���� �ֱ� => order_ok�� ���� ���� �ֱ�
	public boolean insertToOrderUser(Member member, ArrayList order_list){
		connect();
		
		String strSQL = "SELECT Max(order_id) FROM order_user";
		String sql ="insert into order_user(userid,name,address,phone,order_id) values(?,?,?,?,?)";
		String sql2 ="insert into order_ok(order_id, code, amount) values(?,?,?)";
		try {
			
			pstmt = conn.prepareStatement(strSQL);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int  order_id = rs.getInt(1) + 1;	

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,member.getUserid());
			pstmt.setString(2,member.getName());
			pstmt.setString(3,member.getAddress());
			pstmt.setString(4,member.getPhone());
			pstmt.setInt(5,order_id);
			pstmt.executeUpdate();
			
			//pstmt = conn.prepareStatement(sql2);
			for(int i = 0; i < order_list.size();){
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1,order_id);
				pstmt.setString(2, (String) order_list.get(i));
				i++;
				pstmt.setInt(3, Integer.parseInt(order_list.get(i).toString()));
				i++;
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	//order_ok�� ���� ���� �ֱ�
	public boolean insertToOrderOk(ArrayList order_list){
		connect();
		String strSQL = "SELECT Max(order_id) FROM order_user";
		String sql ="insert into order_ok(order_id, code, amount) values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(strSQL);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int  order_id = rs.getInt(1);	
			for(int i = 0; i < order_list.size();){
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,order_id);
				pstmt.setString(2, (String) order_list.get(i));
				i++;
				pstmt.setInt(3, Integer.parseInt(order_list.get(i).toString()));
				i++;
				pstmt.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
}
