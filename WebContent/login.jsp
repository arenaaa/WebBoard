<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
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
<jsp:include page="/common/header.jsp"></jsp:include>
<div class="container-fluid">
	<c:if test="${fail }">
	<div class="row">
		<div class="col-xs-12">
		<div>ID/Password 를 확인하세요</div>
		</div>
	</div>
	<c:remove var="fail" scope="session"/> <!-- session.removeAttribute("fail"); -->
	</c:if>
	
	<div class="row">
		<div class="col-xs-12">
		
			<form action="/board/doLogin" method="post">
				<div class="form-group">
					<label>ID</label>
					<input type="text" class="form-control" placeholder="user id" name="uid" value="tom"></div>
				<div class="form-group">
					<label>Password</label>
					<input type="password" class="form-control" placeholder="user password" name="pass" value="2222"></div>
				<div>
					<button type="submit" class="form-control">로그인</button>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>
