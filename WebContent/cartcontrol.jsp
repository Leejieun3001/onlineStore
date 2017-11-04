
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" errorPage="goods_error.jsp"
	import="jspbook.project.*"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean id="cart" class="jspbook.project.Cart" />
	<jsp:useBean id="cb" class="jspbook.project.CartBean" />
	<jsp:setProperty name="cart" property="*" />
	<%
		// 사용자 요청을 구분하기 위한 파라미터를 저장할 변수
		String action = request.getParameter("action");

		// 상품 목록 요청인 경우
		if (action.equals("list")) {
			Cart cartList = cb.getCart(cart.getCart_id());
			request.setAttribute("cart", cartList);
			response.sendRedirect("cart_list.jsp");
		}
		// 상품 등록 요청인 경우
		else if (action.equals("insert")) {

			if (cb.insertCart(cart)) {
				response.sendRedirect("cart_list.jsp");
			} else
				throw new Exception("DB 입력오류");

		}
		// 상품 수정 요청인 경우
		else if (action.equals("edit")) {
			Cart cartEdit = cb.getCart(cart.getCode());
			request.setAttribute("cart", cartEdit);
			pageContext.forward("cart_edit.jsp");
		}
		// 상품 수정 처리인 경우
		else if (action.equals("update")) {
			if (cb.updateCart(cart)) {
				response.sendRedirect("cart_list.jsp");
			} else
				throw new Exception("DB 갱신오류");
		}
		// 상품 삭체 요청인 경우
		else if (action.equals("delete")) {
			if (cb.deleteCart(cart.getCode())) {
				response.sendRedirect("cart_list.jsp");
			} else
				throw new Exception("DB 삭제 오류");
		}
	%>
</body>
</html>