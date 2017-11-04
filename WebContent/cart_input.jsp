<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.*,jspbook.project.*"
	errorPage="cart_error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language=JavaScript>
	function check() {
		document.form1.action.value = "list";
		document.form1.submit();
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>

<body>
	<center>
		<h2>선택한 물품 정보</h2>
		<hr>
		<%
			Cart cart = new Cart();

			String cartid = (String) session.getAttribute("userid");
			String strAmount = (String) request.getParameter("amount");
			String code = (String) request.getParameter("code");
			String title = (String) request.getParameter("title");
			String price = (String) request.getParameter("price");


			int amount = Integer.parseInt(strAmount);
			cart.setCart_id(cartid);
			cart.setCode(code);
			cart.setAmount(amount);
			
			GoodsBean gb = new GoodsBean();	
			Goods good = gb.getGoods(cart.getCode());
		%>
		<form name=form1 method=post action=cartcontrol.jsp>
			<input type=hidden name="action" value="insert"> 
			<input type=hidden name="cart_id" value="<%=cart.getCart_id()%>">
			<table cellpadding=5 cellspacing=0 border="1">
				<tr bgcolor="#99CCFF">
					<td>수량</td>
					<td>상품코드</td>
					<td>상품명</td>
					<td>단가</td>
				</tr>
				<tr>
					<td><input type=hidden name="amount"
						value="<%=cart.getAmount()%>"><%=cart.getAmount()%></td>
					<td><input type=hidden name="code" value="<%=cart.getCode()%>"><%=cart.getCode()%></td>
					<td><input type=hidden name="title" value="<%=good.getTitle()%>"><%=good.getTitle()%></td>
					<td><input type=hidden name="price" value="<%=good.getPrice()%>"><%=good.getPrice()%></td>
					<td><%=good.getPrice()*cart.getAmount()%></td>
				<tr>
					<td colspan=5 align=center><input type=submit value="저장">
						<input type="button" value="카트 목록으로 가기" onClick="check()">
				</tr>

			</table>
		</form>

	</center>
</body>
</html>