/*
 *
 * 상품 관리의 빈즈 클래스
 * 
 */

package jspbook.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GoodsBean {
//	 데이터베이스 연결관련 변수 선언
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	// 데이터베이스 연결관련정보를 문자열로 선언
	String jdbc_driver = "oracle.jdbc.driver.OracleDriver";
	String jdbc_url = "jdbc:oracle:thin:@localhost:1521:orcl";
	
	// 데이터베이스 연결 메서드
	void connect() {
		// JDBC 드라이버 로드
		try {
			Class.forName(jdbc_driver);
			// 데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
			conn = DriverManager.getConnection(jdbc_url,"project","project");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 데이터베이스 연결 종료 메서드
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
	public boolean insertGoods(Goods goods) {
		connect();
		
		String strSQL = "SELECT Max(num) FROM goods";
		String sql ="insert into goods(num,title,price,code,contents) values(?,?,?,?,?)";
		
		try {
			
			PreparedStatement pstmt1 = conn.prepareStatement(strSQL);
			ResultSet rs = pstmt1.executeQuery();
			rs.next();
			int  num = rs.getInt(1) + 1;	
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,num);	//goods에서 num값의 최대값을 받아와 그 값에 1더한것을 그 다음 num으로 등록
			pstmt.setString(2,goods.getTitle());
			pstmt.setInt(3,goods.getPrice());
			pstmt.setString(4,goods.getCode());
			pstmt.setString(5,goods.getContents());
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
	public ArrayList getGoodsList() {
		connect();

		ArrayList<Goods> datas = new ArrayList<Goods>();
		
		String sql = "select * from Goods where num>0  order by num desc";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			// 루프를 돌면 ResultSet 을 이동하면서 모든 데이터 row 를 하나씩 가지고 와서 GuestBook 객체에 넣고 이를 다시 ArrayList 에 넣는 작업을 반복.
			while(rs.next()) {
				Goods goods = new Goods();
				goods.setNum(rs.getInt("num"));
				goods.setTitle(rs.getString("title"));
				goods.setPrice(rs.getInt("price"));
				goods.setCode(rs.getString("code"));
				goods.setContents(rs.getString("contents"));
				datas.add(goods);
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
	
	public Goods getGoods(String code) {
		connect();
		
		String sql = "select * from Goods where code=?";

		Goods goods = new Goods();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,code);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			goods.setNum(rs.getInt("num"));
			goods.setTitle(rs.getString("title"));
			goods.setPrice(rs.getInt("price"));
			goods.setCode(rs.getString("code"));
			goods.setContents(rs.getString("CONTENTS"));
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return goods;
	}
	// 데이터 갱신을 위한 메서드
	public boolean updateGoods(Goods goods) {
		connect();
		
		String sql ="update goods set title=?, price=?, code=?, contents=? where num=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			// 인자로 받은 GuestBook 객체를 이용해 사용자가 수정한 값을 가져와 SQL문 완성
			pstmt.setString(1,goods.getTitle());
			pstmt.setInt(2,goods.getPrice());
			pstmt.setString(3,goods.getCode());
			pstmt.setString(4,goods.getContents());
			pstmt.setInt(5,goods.getNum());
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
	
	// 데이터 삭제를 위한 메서드
	public boolean deleteGoods(int num) {
		connect();
		
		try {
			
			String sql ="delete from goods where num=?";
			
			pstmt = conn.prepareStatement(sql);
			// 인자로 받은 gb_id 프라이머리 키 값을 이용해 삭제
			pstmt.setInt(1,num);
			pstmt.executeUpdate();
		
			String sql2 = "UPDATE goods  SET num = num - 1 WHERE num > ?";
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1,num);
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
	
	// 상품 검색을 위한 메서드
	public ArrayList getsearchGoodslist(String strTitle, String strCont) {
		connect();

		ArrayList<Goods> datas = new ArrayList<Goods>();
		
		String sql = "SELECT num, title, price, code, contents from Goods WHERE " + strTitle + " LIKE '%" + strCont + "%'"; ;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			// 루프를 돌면 ResultSet 을 이동하면서 모든 데이터 row 를 하나씩 가지고 와서 GuestBook 객체에 넣고 이를 다시 ArrayList 에 넣는 작업을 반복.
			while(rs.next()) {
				Goods goods = new Goods();
				goods.setNum(rs.getInt("num"));
				goods.setTitle(rs.getString("title"));
				goods.setPrice(rs.getInt("price"));
				goods.setCode(rs.getString("code"));
				goods.setContents(rs.getString("contents"));
				datas.add(goods);
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
}
