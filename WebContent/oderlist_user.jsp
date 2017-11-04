<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="java.util.*, java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException, jspbook.project.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	request.setCharacterEncoding("utf-8");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>주문 확인</title>
</head>
<body>
	<jsp:useBean id="login" class="jspbook.project.LoginBean"
		scope="session" />
	<jsp:useBean id="cb" class="jspbook.project.CartBean" scope="session" />
	<center>
		<h2>나의 주문 리스트</h2>
		<hr>
		<%= login.getUserid()%>님의 주문 리스트 <br> <br> [<a
			href="menu.jsp">메인 화면으로</a>]
		<%
 	//데이터베이스 연결관련 변수 선언
 	Connection conn = null;
 	PreparedStatement pstmt = null;

 	// 데이터베이스 연결관련정보를 문자열로 선언
 	String jdbc_driver = "oracle.jdbc.driver.OracleDriver";
 	String jdbc_url = "jdbc:oracle:thin:@localhost:1521:orcl";

 	// 데이터베이스 연결
 	try {
 		Class.forName(jdbc_driver);
 		// 데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
 		conn = DriverManager.getConnection(jdbc_url, "project", "project");
 	} catch (Exception e) {
 		e.printStackTrace();
 	}
 %>
		<br> <br>
		<TABLE border='1' cellspacing=1 cellpadding=2 width='600'>
			<TR bgcolor='cccccc'>
				<TD align='center' width='100'>주문 ID</TD>
				<TD align='center' width='250'>상품 코드</TD>
				<TD align='center' width='50'>수량</TD>
			</TR>
			<%
				//장바구니 리스트 가져오기
				String user_id = login.getUserid();
				String sql = "select order_ok.order_id, code, amount  from order_ok,order_user  where order_user.order_id =order_ok.order_id  and userid = '"
						+ user_id + "' order by order_ok.order_id";
				try {
					pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						int order_id = rs.getInt("order_id");
						String code = rs.getString("code");
						int amount = rs.getInt("amount");
			%>
			<tr>
				<td><%=order_id%></td>
				<td><%=code%></td>
				<td><%=amount%></td>
			</tr>
			<%
				}
					rs.close();

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					// 데이터베이스 연결 종료
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
			%>
		</TABLE>
	</center>
</body>
</html>