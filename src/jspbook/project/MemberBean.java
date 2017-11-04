

package jspbook.project;

import java.sql.*;
import java.util.*;

public class MemberBean {
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
	//	 �Խù� ��� �޼���
	public boolean insertDB(Member member) {
		connect();
		
		String sql ="insert into member(userid,passwd,name,juminnum1,juminnum2,zip,address,phone,email) values(?,?,?,?,?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,member.getUserid());
			pstmt.setString(2,member.getPasswd());
			pstmt.setString(3,member.getName());
			pstmt.setString(4,member.getJuminnum1());
			pstmt.setString(5,member.getJuminnum2());
			pstmt.setString(6,member.getZip());
			pstmt.setString(7,member.getAddress());
			pstmt.setString(8,member.getPhone());
			pstmt.setString(9,member.getEmail());
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
	
	//ID �ߺ� üũ �޼ҵ�
	public boolean checkID(String userid){
		Boolean check = null;
		connect();
		String sql = "select userid from member where userid=?";
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next())
				check = true;
			else
				check = false;
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return check;
	}
	
	//��� ���� ��������.
	public Member getMember(String userid) {
		connect();
		
		Member member = new Member();
		String sql ="select * from member where userid=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			member.setUserid(rs.getString("userid"));
			member.setName(rs.getString("name"));
			member.setAddress(rs.getString("address"));
			member.setPhone(rs.getString("phone"));
			member.setPasswd(rs.getString("passwd"));
			member.setEmail(rs.getString("email"));
			member.setJuminnum1(rs.getString("juminnum1"));
			member.setJuminnum2(rs.getString("juminnum2"));
			member.setZip(rs.getString("zip"));
			rs.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return member;
	}
}
