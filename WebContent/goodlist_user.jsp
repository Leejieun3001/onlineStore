<%-- 

사용자 상품 상세 화면 페이지
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.*, jspbook.project.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>선택 상품</title>
</head>
<body>
	<jsp:useBean id="goods" class="jspbook.project.Goods" />
	<jsp:useBean id="gb" class="jspbook.project.GoodsBean" />
	<jsp:setProperty name="goods" property="*" />
	<%
		Goods good = gb.getGoods(goods.getCode());
	%>
	<center>
		<h2>선택 상품</h2>
		<hr>
		[<a href="goodslist_user.jsp">상품 목록</a>]
		<form name=form1 method="post" action="cart_input.jsp">
			<input type=hidden name="code" value="<%=good.getCode()%>" />
			<table cellpadding=5 cellspacing=0 border="1">
				<tr bgcolor="#99CCFF">
					<td>상품번호</td>
					<td>상품명</td>
					<td>가격</td>
					<td>상품코드</td>
					<td>비고</td>
					<td>개수 선택</td>
				</tr>
				<tr>
					<td><%=good.getNum()%></td>
					<td><%=good.getTitle()%></td>
					<td><%=good.getPrice()%></td>
					<td><%=good.getCode()%></td>
					<td><%=good.getContents()%></td>
					<td><select name=amount>
							<option selected>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
							<option>6</option>
							<option>7</option>
							<option>8</option>
							<option>9</option>
							<option>10</option>
					</select></td>
				</tr>
			</table>
			<br> <input type="submit" value="장바구니에 담기" />
		</form>
		<form name=form2 method="post" action="searchlist_user.jsp">
			<select name="field">
				<option value="title" selected>상품명
				<option value="contents">상품정보
			</select> <input type="text" name="string"> <input type="submit"
				value="검색" />
		</form>
	</center>
</body>
</html>