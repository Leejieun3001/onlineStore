
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"   import="jspbook.project.*" %>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

<jsp:useBean id="order_user" class="jspbook.project.Order_user"/>
<jsp:useBean id="order_ub" class="jspbook.project.Order_userBean"/>
<jsp:setProperty name="order_user" property="*"/>

<% 
	// 사용자 요청을 구분하기 위한 파라미터를 저장할 변수
	String userid = order_user.getUserid();
	String action = request.getParameter("action");	
	
	if(action.equals("insert")) {		
		if(order_ub.insertToUser(order_user)) {
			if(order_ub.insertToOk(order_user)) {
				if(order_ub.deleteToCart()){
				response.sendRedirect("order_ok.jsp");
				}
				else
					throw new Exception("DB1 입력오류");
			}
			else
				throw new Exception("DB2 입력오류");
		}
		else
			throw new Exception("DB3 입력오류");
	}
%>
</body>
</html>