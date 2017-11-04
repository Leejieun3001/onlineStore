
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" isErrorPage="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<body>
<center>
<H2>상품 구매 에러</H2>
<HR>
<table cellpadding=5 width=400>
<tr width=100% bgcolor="pink"><td>
상품 구매 처리중 에러가 발생 했습니다.<BR>
관리자에게 문의해 주세요..<BR>
빠른시일내 복구하겠습니다.
<HR>
에러내용 : <%= exception%>
<HR>

</td>
</tr>
</table>
</center>
</body>
</HTML>