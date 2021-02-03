<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<%@ include file="../include/inc_header.jsp" %>        
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%> 

<form name ="memoWriteForm">
	<input type = "hidden" name = "Id" value = "">		
	<table border="1">
		<tr>
			<td colspan = "100">
				<h2>메모장</h2>
			</td>
		</tr>
		
		<tr>
			<td>이름 : </td>
			<td><input type="text" name="name"></td>
		</tr>
		
		<tr>
			<td>메모 : </td>
			<td>
				<input type="text" name="content">
				&nbsp;&nbsp;&nbsp;
				<input type="button" value="확인" onclick="save();">
			</td>
		</tr>
</form>
		<tr>
			<td colspan ="100">
				<table border="1">
					<tr>
						<th>ID</th>
						<th>제목</th>
						<th>메모</th>
						<th>날짜</th>
					</tr>
				<c:if test="${list.size() == 0}">
					<tr>
						<td colspan ="7" height="200">
							등록된 메모가 없습니다.
						</td>
					</tr>
				</c:if>
				
				<c:forEach var="dto" items="${list}">
					<tr>
						<td>${dto.id}</td>
						<td>${dto.name}</td>
						<td>${dto.content}</td>
						<td>${fn:substring(dto.regi_date,0,19)}</td>
					</tr>
				</c:forEach>
					<c:if test ="${totalRecord > 0}">
					<tr>
						<td colspan="100" height="50" align="right">
							<a href="#" onclick="GoPage('memo_list','1','');">[첫페이지]</a>
							&nbsp;&nbsp;
							<c:if test="${startPage > blockSize }">
								<a href="#" onclick="GoPage('memo_list','${startPage - blockSize}','');">[이전10개]</a>				
							</c:if>     
							
							<c:if test="${startPage <= blockSize}">
								[이전10개]
							</c:if>
							&nbsp;
							
							<c:forEach var="i" begin="${startPage }" end="${lastPage }" step="1">
								<c:if test="${i == pageNumber }">
									[${i }]
								</c:if>
								
								<c:if test="${i != pageNumber }">
									<a href="#" onclick="GoPage('memo_list','${i}','');">${i}</a>
								</c:if>
							</c:forEach>
							&nbsp;
							
							<c:if test="${lastPage < totalPage }">
								<a href="#" onclick="GoPage('memo_list', '${startPage + blockSize}', '');">[다음10개]</a>
							</c:if>
							<c:if test="${lastPage >= totalPage }">
								[다음10개]
							</c:if>
							&nbsp;&nbsp;
							<a href="#" onclick="GoPage('memo_list', '${totalPage}', '');">[끝페이지]</a>
						</td>
					</tr>
				</c:if>
				
				<c:if test ="${totalRecord == 0}">
					<tr>
						<td colspan="100" height="50" align="right">
							등록된 메모가 없습니다.
						</td>
					</tr>
				</c:if>
			
			</table>
		</td>
	</tr>
</table>

<script>
function save(){
	if (confirm('등록?')){
		memoWriteForm.method = "post";
		memoWriteForm.action = "${path}/memo_servlet/memoProc.do";
		memoWriteForm.submit();
	}
}
</script>

<script>
	function GoPage(value1, value2, value3){
		if (value1 == 'memo_list') {
			location.href = '${path}/memo_servlet/memo.do?pageNumber=' + value2;
		}  else if (value1 == 'memo_view'){
			location.href = '${path}/memo_servlet/view.do?pageNumber=' + value2 + '&id=' + value3;
		}
	}
</script>
