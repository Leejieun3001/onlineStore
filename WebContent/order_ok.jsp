<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR" import="java.util.*,jspbook.project.*"
	errorPage="cart_error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script language=JavaScript>
function confirm() {
			document.location.href="menu.jsp";
	}
</script>
<title>주문 완료</title>
</head>
<body>
	<center>
		<h2>주문 완료</h2>
		<hr>
		<%
			Order_okBean order_ob = new Order_okBean();
			int order_id = order_ob.getOrder_id();
			ArrayList<Order_ok> datas = order_ob.getDBList(order_id);
		%>

		<h3>주문이 성공적으로 완료되었습니다.</h3>

		<h4>결제 정보</h4>
		<form>
			<table cellpadding=5 cellspacing=0 border="1">
				<tr>
					<td colspan=2 bgcolor="#99CCFF">주문 번호</td>
					<td colspan=2><%=order_id%></td>
				</tr>
			</table>
			
			<br>
				
			<table cellpadding=5 cellspacing=0 border="1">
				<%	
				for (Order_ok order_ok : datas) {
				%>
					<tr>
					<td bgcolor="#99CCFF">코드</td>
					<td><%=order_ok.getCode()%></td>
					<td bgcolor="#99CCFF">수량</td>
					<td><%=order_ok.getAmount() %></td>
					</tr>
				<%
				} 
				%>
			</table>
		</form>


		<hr>
		<h4>결제 상품 정보</h4>

		<form>
			<table cellpadding=5 cellspacing=0 border="1">
				<tr bgcolor="#99CCFF">
				    <td>상품코드</td>
					<td>상품명</td>
					<td>단가</td>
					<td>수량</td>
					<td>총액</td>
				</tr>

				<%
					// ArrayList 객체를 통해 DB에서 가져온 목록을 출력함.
					ArrayList order_list = (ArrayList) session
							.getAttribute("order_list");
					int i = 0;
					while (i < order_list.size() - 1) {
				%>
				<tr>
					<td><%=order_list.get(i++)%></td>
					<td><%=order_list.get(i++)%></a></td>
					<td><%=order_list.get(i++)%></td>
					<td><%=order_list.get(i++)%></td>
					<td><%=order_list.get(i++)%></td>
				</tr>

				<%
					}
					String strSum = (String) order_list.get(order_list.size() - 1);
					int intSum = Integer.parseInt(strSum);
				%>
				
				<tr>
					<td bgcolor="#99CCFF" colspan=4 align=center>총액</td>
					<td><%=intSum%></td>
				</tr>
			</table>
		</form>
		<br>
		<input type="button" value="확인"  onClick="confirm()">

	</center>
</body>
</html>