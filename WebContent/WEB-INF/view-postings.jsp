<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${p.title }</title>
<script type="text/javascript" src="<%=application.getContextPath() %>/static/js/jquery-1.11.3.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/static/css/bootstrap.css"/>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/static/js/bootstrap.js"></script>
<script type="text/javascript">
$(document).ready ( function(){
	// callback 함수라고 합니다.
	$('#btnGoEdit').on('click', function(){
		var uri = '/board' + '/update';
		uri += '?id=' + ${p.seq};
		document.location.href = uri;
	});
	/*
	$('#btnDelete').on('click', function() {
		var uri = '/board'+'/dodelete';
		uri += '?id=' + ${p.seq} ;   // /board/dodelete?id=12
		document.location.href = uri; // GET
	});
	*/
});
</script>
</head>
<body>
<jsp:include page="/common/header.jsp"></jsp:include>
<div class="container">

	<h2>${p.title }</h2>
	<table class="table">
		<thead>
			
<tr>
	<td>글번호</td>
	<td>${p.seq}</td>
</tr>
<tr>
	<td>조회수</td>
	<td>${p.viewcount}</td>
</tr>
<tr>
	<td>추천수</td>
	<td>${p.recocount}</td>
</tr>
<tr>
	<td>이름</td>
	<td>${p.writer.userId}</td>
<tr>
	<td>내 용</td>
	<td>${p.contents}</td>
</tr>
	<!-- 
	    UserVO tmp = p.getWriter();
	    tmp.getUserId();
	 -->
<tr>
	<td>작성일</td>
	<td>${p.ctime}</td>
</tr>
<tr>
	<td>첨부파일</td>
	<td> <a href="<%=application.getContextPath() %>/file?seq=${p.attachedFile.seq }">${p.attachedFile.originFileName }</a></td>
</tr>
<tr>
	<td colspan="5">
	<!--  session.getAttribute("user").getSeq() -->
	<c:if test="${p.writer.seq eq sessionScope.user.seq }">
	<input type="button" class="btn btn-primary" id="btnGoEdit" name="update" value="수정">
	
	<form action="<%=application.getContextPath()%>/dodelete" method="post">
		<input type="hidden" name="pid" value="${p.seq }">
		<input type="submit" class="btn btn-primary" id="btnDelete" value="삭제">
	</form>
	</c:if>
	<!-- 로그인을 한 상태 && 현재 보고 있는 글의 작성자가 아니어야 함. -->
	<%--
	<c:if test="${(not empty sessionScope.user)  and (p.writer.seq ne sessionScope.user.seq) }">
	 --%>
	<form action="<%=application.getContextPath()%>/dorecommend" method="post">
		<input type="hidden" name="pid" value="${p.seq }">
		<input type="submit" class="btn btn-primary" value="추천" >
	</form>
	<%--
	</c:if>	
	 --%>
	</td>

</table>
</div>
</body>
<c:if test="${ not empty sessionScope.rcmd_error }">
<script type="text/javascript">
	<c:if test="${ sessionScope.rcmd_error eq 'RCMD500' }">
	alert ('자기 자신의 글을 추천할 수 없습니다.');
	</c:if>
	<c:if test="${ sessionScope.rcmd_error eq 'RCMD510' }">
	alert ('이미 추천했습니다.');
	</c:if>
	
</script>
<c:remove var="rcmd_error" scope="session"/> <!-- session.removeAttribute("rcmd_error"); -->
</c:if>
</html>