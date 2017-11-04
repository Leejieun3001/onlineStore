<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 예제</title>
</head>
<body>
	<%--This is made by Lee Ji Eun --%>
	<%--date:  2016. 11. 1.  --%>
	<h2>Menu</h2>

	<center>
		<hr>
		<a href=listboard.jsp>게시판으로</a> <br> <br> <a
			href=goodslist_user.jsp>상품구매 화면으로</a> <br> <br> <a
			href=orderlist_user.jsp>주문상품 화면으로</a> <br>
		<br> <a href=member_edit.jsp>회원정보수정 화면으로</a> <br>
		<br>

		<%
			if (session.getAttribute("userid").equals("admin")) {
		%>
		<a href=goodslist_admin.jsp>상품관리 화면으로</a> <br>
		<br>

		<%
			}
		%>

		<hr>




	</center>
</body>
</html>