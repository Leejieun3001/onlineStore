<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 목록 확인</title>
</head>
<body>
	<%--This is made by Lee Ji Eun --%>
	<%--date:  2016. 11. 8.  --%>
	<%@ page
		import="java.util.ArrayList, jspbook.project.BoardEntity, java.text.SimpleDateFormat"%>
	<jsp:useBean id="brddb" class="jspbook.project.Board" scope="page" />
	<%
		//게시 목록을 위한 배열리스트를 자바빈즈를 이용하여 확보 
		ArrayList<BoardEntity> list = brddb.getBoardList();
		int counter = list.size();
		int row = 0;

		if (counter > 0) {
	%>
	<table width=800 border=0 cellpadding=1 cellspacing=3>

		<tr>
			<th><font color=blue><b>번호</b></font></th>
			<th><font color=blue><b>제목</b></font></th>
			<th><font color=blue><b>작성자</b></font></th>
			<th><font color=blue><b>작성일</b></font></th>
			<th><font color=blue><b>전자메일</b></font></th>
		</tr>
		<%
			//게시 등록일을 2010-3-15 10:33:21 형태로 출력하기 위한 클래스 
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (BoardEntity brd : list) {
					//홀짝으로 다르게 색상 지정
					String color = "papayawhip";
					if (++row % 2 == 0)
						color = "white";
		%>
		<tr bgcolor=<%=color%>
			onmouseover="this.style.backgroundColor='SkyBlue'"
			onmouseout="this.style.backgroundColor='<%=color%>'">
			<!-- 수정과 삭제를 위한 링크로 id를 전송 -->
			<td align=center><a href="editboard.jsp?id=<%=brd.getId()%>"><%=brd.getId()%></a></td>
			<td align=left><%=brd.getTitle()%></td>
			<td align=center><%=brd.getName()%></td>
			<!-- 게시 작성일을 2010-3-15 10:33:21 형태로 출력 -->
			<td align=center><%=df.format(brd.getRegdate())%></td>
			<td align=center><%=brd.getEmail()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>
	<hr width=90%>
	<p>
		조회된 게시판 목록 수가
		<%=counter%>개 입니다.
	</center>
	<hr>
	<center>
		<form name=form method=post action=editboard.jsp>
			<input type=submit value="게시등록">
		</form>
	</center>

</body>
</html>