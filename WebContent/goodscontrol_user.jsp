<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
  import="jspbook.project.*, java.util.*"%>
 <% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="goods" class="jspbook.project.Goods"/>
<jsp:useBean id="gb" class="jspbook.project.GoodsBean"/>
<jsp:setProperty name="goods" property="*"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 예제</title>
</head>
<body>
<%--This is made by Lee Ji Eun --%>
<%--date:  2016. 11. 14.  --%>
<% 
//사용자 요청을 구분하기 위한 파라미터를 저장할 변수
	String action = request.getParameter("action");

	// 상품 목록 요청인 경우
	if(action.equals("list")) {
		response.sendRedirect("goodslist_user.jsp");
	}
	
	// 특정 상품 조회 요청인 경우
	else if(action.equals("edit")) {
		Goods good = gb.getGoods(goods.getCode());
		request.setAttribute("good",good);
		pageContext.forward("goodlist_user.jsp");
	}
	// 상품 수정 처리인 경우
	else if(action.equals("update")) {
			if(gb.updateGoods(goods)) {
				response.sendRedirect("goodslist_admin.jsp");
			}
			else
				throw new Exception("DB 갱신오류");
	}
	// 상품 삭체 요청인 경우
	else if(action.equals("delete")) {
		if(gb.deleteGoods(goods.getNum())) {
			response.sendRedirect("goodslist_admin.jsp");
		}
		else
			throw new Exception("DB 삭제 오류");
	}

%>
</body>
</html>