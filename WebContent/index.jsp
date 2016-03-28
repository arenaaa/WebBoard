<%@page import="board.vo.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=application.getContextPath() %>/static/js/jquery-1.11.3.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/static/css/bootstrap.css"/>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/static/js/bootstrap.js"></script>
</head>
<body>
<div>
<!-- START: header -->
<jsp:include page="/common/header.jsp"></jsp:include>
<!-- END: header -->

<!-- 
el문
UserVO user = request.getAttribute("user");
if ( user != null ) {
   out.print( user );
   return;
}
user = session.getAttribute("user");
if ( user != null ) {
   out.print( user );
}
application.getAttribute("user");
if ( user != null ) {
 ....
 }
 
 -->
 
</div>
<div class="container">
	<div class="alert alert-success">메인페이지 입니다.</div>
	<div>
	<button type="button" class="btn btn-primary" onclick="location.href='<%=application.getContextPath() %>/join' ">회원가입</button>
	</div>
</div>

</body>
</html>