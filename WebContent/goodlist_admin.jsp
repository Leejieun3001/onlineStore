<%-- 
상품 수정, 삭제 페이지
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.*, jspbook.project.*" errorPage="goods_error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language=JavaScript>
	// 삭제 확인을 위한 자바스크립트
	function delcheck() {
		// 메시지 창을 통해 YES/NO 확인
		result = confirm("정말로 삭제하시겠습니까 ?");
		if(result == true){
			document.form1.action.value="delete";
			document.form1.submit();
		}
		else
			return;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>상품 수정, 삭제</title>
</head>
<%	
	Goods good = (Goods)request.getAttribute("good"); 
%>
<body>
<center>
<h2>상품 수정,삭제</h2>
<hr>
[<a href=goodscontrol_admin.jsp?action=list>상품목록으로</a>]
<form name=form1 method=post action=goodscontrol_admin.jsp>
<input type=hidden name="num" value="<%=good.getNum()%>">
<input type=hidden name="action" value="update">
<table cellpadding=5 cellspacing=0 border="1">
  <tr>
    <td bgcolor="#99CCFF">상품명</td>	
    <td ><input type="text" name="title" size="40"  value="<%=good.getTitle()%>"></td>
  </tr>
  <tr>
    <td bgcolor="#99CCFF">가격</td>
    <td ><input type="text" name="price" size="40" value="<%=good.getPrice()%>"></td>
  </tr>
    <tr>
    <td bgcolor="#99CCFF">상품코드</td>
    <td ><input type="text" name="code" size="40" value="<%=good.getCode()%>"></td>
  </tr>
  <tr>
    <td bgcolor="#99CCFF">비고</td>
    <td><textarea rows="5" name="contents" cols="40" ><%=good.getContents() %></textarea></td>
  </tr>
  <tr>
    <td colspan=2 align=center><input type=submit value="저장"><input type=reset value="취소"><input type="button" value="삭제" onClick="delcheck()"></td>
</tr>
</table>
</form>
</center>
</body>
</html>