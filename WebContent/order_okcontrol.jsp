
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"  errorPage="goods_error.jsp" import="jspbook.project.*"%>
<% request.setCharacterEncoding("euc-kr"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="order_ok" class="jspbook.project.Order_ok"/>
<jsp:useBean id="order_ob" class="jspbook.project.Order_okBean"/>
<jsp:setProperty name="order_ok" property="*"/>
<% 
	// 사용자 요청을 구분하기 위한 파라미터를 저장할 변수
	String action = request.getParameter("action");

/*	// 상품 목록 요청인 경우
	if(action.equals("list")) {
		response.sendRedirect("goodslist_admin.jsp");
	}
	// 상품 등록 요청인 경우
	
	if(action.equals("insert")) {		
		if(order_ob.insertDB(order_ok)) {
			response.sendRedirect("menu.jsp");
		}
		else
			throw new Exception("DB 입력오류");
	}
*/	// 상품 수정 요청인 경우
	if(action.equals("edit")) {	
		Order_ok ok_edit = order_ob.getDB(order_ok.getOrder_id(), order_ok.getCode());
		request.setAttribute("order_ok",ok_edit);
		pageContext.forward("order_edit.jsp");
	}
	// 상품 수정 처리인 경우
	else if(action.equals("update")) {
			if(order_ob.updateDB(order_ok)) {
				response.sendRedirect("orderslist_user.jsp");
			}
			else
				throw new Exception("DB 갱신오류");
	}
	// 상품 삭체 요청인 경우
	else if(action.equals("delete")) {
		int order_id = Integer.parseInt(request.getParameter("order_id"));
		if(order_ob.deleteDB(order_id)) {
			response.sendRedirect("orderslist_user.jsp");
		}
		else
			throw new Exception("DB 삭제 오류");
	}

%>
</body>
</html>