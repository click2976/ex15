<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<%@ include file ="../include/inc_header.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
	<table border="1" align="center">

		
		<tr>
			<td align="center" style="padding:50px 50px;">
				<c:choose>
					<c:when test="${menu_gubun == 'memo_memo'}">
						<jsp:include page="../example01/example01MemoMain.jsp"/>
					</c:when>
					
					
					<c:otherwise>
						bbb
					</c:otherwise>
				</c:choose>
			</td>
		</tr>

	</table>	
</body>
</html>