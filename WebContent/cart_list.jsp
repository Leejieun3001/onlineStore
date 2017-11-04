<%-- 

장바구니 리스트 출력
--%>


<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.*,jspbook.project.*"
	errorPage="cart_error.jsp"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>장바구니 리스트</title>
<script language=JavaScript>
	function check(code) {
		document.location.href = "cartcontrol.jsp?action=edit&code=" + code;
	}
	function order() {
		document.location.href = "cartcontrol.jsp?action=order";
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>카트 상품 목록 화면</title>
</head>

<body>
	<%
		CartBean cb = new CartBean();
		ArrayList<Cart> datas = cb.getCartList((String) session.getAttribute("userid"));

		GoodsBean gb = new GoodsBean();
	%>

	<center>

		<h2>내 장바구니</h2>
		<hr>
		[<a href="goodslist_user.jsp">상품 목록으로(장바구니 추가 담기)</a>] <br>
		<br>
		<form name=form1 method=post action=order_user.jsp>

			<table cellpadding=5 cellspacing=0 border="1">
				<tr bgcolor="#99CCFF">

					<TD align='center' width='100'>상품코드</TD>
					<TD align='center' width='250'>상품명</TD>
					<TD align='center' width='100'>단가</TD>
					<TD align='center' width='50'>수량</TD>
					<TD align='center' width='100'>금액</TD>
					<TD align='center' width='100'>수정/삭제</TD>
				</TR>
				<%
					//장바구니의 물품 리스트를 담을 ArrayList --> 구매시에 order_ok에 물품을 담을 때 필요
					ArrayList<String> order_list = new ArrayList<String>();
					// ArrayList 객체를 통해 DB에서 가져온 목록을 출력함.

					//금액 합계
					int intSum = 0;
					//장바구니 리스트 가져오기
					for (Cart cart : datas) {
						Goods good = gb.getGoods(cart.getCode());

						int intPSum = cart.getAmount() * good.getPrice();
						intSum = intSum + intPSum;
				%>

				<tr>
					<td><%=cart.getCode()%></td>
					<td><a href="javascript:check('<%=cart.getCode()%>')"><%=good.getTitle()%></a></td>
					<td><%=good.getPrice()%>
					<td><%=cart.getAmount()%></td>
					<td><%=intPSum%></td>
					<td><a href="javascript:check('<%=cart.getCode()%>')">수정/삭제</a></td>
				</tr>
				<%
					order_list.add(cart.getCode());

						order_list.add(good.getTitle());
						order_list.add(Integer.toString(good.getPrice()));
						order_list.add(Integer.toString(cart.getAmount()));
						order_list.add(Integer.toString(intPSum));

					}
					order_list.add(Integer.toString(intSum));
					//cart에 있는 품목들만 session 속성인 "order_list"에 저장
					session.setAttribute("order_list", order_list);
				%>
				<tr>
					<td bgcolor="#99CCFF" colspan=4 align=center>#총액</td>
					<td><%=intSum%></td>
				</tr>
				<tr>
					<td colspan=5 align=center><input type=submit value="구매">
					</td>
				</tr>
			</table>
		</form>
		<br>
	</center>
</body>
</html>