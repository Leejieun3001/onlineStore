<%--
검색된 상품 목록 화면

수정
--%>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.*, jspbook.project.*" errorPage="goods_error.jsp"%>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language=JavaScript>
	function check(code) {
		document.location.href="goodscontrol_user.jsp?action=edit&code="+code;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>검색된 상품 목록 리스트</title>
</head>
<body>
<%
	String strTitle = request.getParameter("field");
	String strCont = request.getParameter("string");
	
	GoodsBean gb = new GoodsBean();
	ArrayList<Goods> datas = gb.getsearchGoodslist(strTitle, strCont);
%>
<center>
<h2>검색된 상품 목록</h2>
<hr>
[<a href="goodslist_user.jsp">상품 전체 목록</a>]

[<a href="main.jsp">메인 화면으로</a>]
<form>
<table cellpadding=5 cellspacing=0 border="1">
	<tr  bgcolor="#99CCFF" ><td>상품번호</td><td>상품명</td><td>가격</td><td>상품코드</td><td>비고</td></tr>
<%
	// ArrayList 객체를 통해 DB에서 가져온 목록을 출력함.
	for(Goods goods : datas) {
%>
	<tr>
		<td><%=goods.getNum()%></td>
		<td><a href="javascript:check('<%=goods.getCode()%>')"><%=goods.getTitle() %></a></td>
		<td><%=goods.getPrice() %></td>
		<td><%=goods.getCode() %></td>
		<td><%=goods.getContents() %></td>
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
	</select>
	<input type="text" name="string">
	<input type="submit" value="검색">
</form>
</center>
</body>