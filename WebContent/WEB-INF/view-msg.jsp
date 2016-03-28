<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/static/css/bootstrap.css"/>
<script type="text/javascript" src="<%=application.getContextPath() %>/static/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/static/js/bootstrap.js"></script>
</head>

<body>
<jsp:include page="/common/header.jsp"></jsp:include>
<!-- 
	List<PostingVO> postings = request.getAttribute("postings");
	for ( PostingVO p : postings) {
		out.print(p.toString() );
	}
 -->
 <div class="container-fluid">
 <div class="row">
 	<div class="col-xs-12">
	<div class="container">
	<div class="panel panel-warning">
  <div class="panel-heading">
    <h3 class="panel-title">보낸 사람 : ${msg.sender.userId}</h3>
  </div>
  <div class="panel-body">
  ${msg.message}
  </div>
 
 
</div>
	 	</div>
	<div>
	</div>
</div>
 	</div>
 </div>
 </div>
 </body>
</html>