<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/static/css/bootstrap.css"/>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/static/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/static/js/bootstrap.js"></script>
</head>
<body>
수정 페이지다!

<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">
		<form action="<%=application.getContextPath() %>/doupdate" method = "post">
			<input type="hidden" value="${user.seq }" name="writer">
			<input type="hidden" value="${posting.seq }" name="pid">
		
			<!--
			   request.getAttribute("user") == null
			   session.getAttribute("user") == null
			   application.getAttribute("user") == null
			    
			 -->
			<label class="">제목</label>
			<input type="text" class="form-control" name="title" value="${posting.title }">
			<label>내용</label>
			<textarea class="form-control" rows="6" name="contents">${posting.contents}</textarea>
			<button type="submit" class="form-control">수정하기</button>
		</form>
		</div>
	</div>
  
</div>
</body>
</html>