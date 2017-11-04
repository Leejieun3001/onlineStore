<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.*, jspbook.project.*"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<html>
<head>
<script language=JavaScript>
	function check(code) {
		document.location.href = "goodscontrol_user.jsp?action=edit&code="
				+ code;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 예제</title>
</head>
<body>
	<%--This is made by Lee Ji Eun --%>
	<%--date:  2016. 11. 15.  --%>
	<%
		GoodsBean gb = new GoodsBean();
		ArrayList<Goods> datas = gb.getGoodsList();
	%>
	<center>
		<h2>상품 목록</h2>
		<hr>
		[<a href="main.jsp">메인 화면으로</a>]
		<form>
			<table cellpadding=5 cellspacing=0 border="1">
				<tr bgcolor="#99CCFF">
					<td>상품번호</td>
					<td>상품명</td>
					<td>가격</td>
					<td>상품코드</td>
					<td>비고</td>
				</tr>
				<%
					// ArrayList 객체를 통해 DB에서 가져온 목록을 출력함.
					for (Goods goods : datas) {
				%>
				<tr>
					<td><%=goods.getNum()%></td>
					<td><a href="javascript:check('<%=goods.getCode()%>')"><%=goods.getTitle()%></a></td>
					<td><%=goods.getPrice()%></td>
					<td><%=goods.getCode()%></td>
					<td><%=goods.getContents()%></td>
				</tr>
				<%
					}
				%>
			</table>
		</form>
		<br>
		<form method="post" action="searchlist_user.jsp">
			<select name="field">
				<option value="title" selected>상품명
				<option value="contents">상품정보
			</select> <input type="text" name="string"> <input type="submit"
				value="검색">
		</form>
	</center>
</body>
</html>