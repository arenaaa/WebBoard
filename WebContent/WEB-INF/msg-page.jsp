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
	<h3>쪽지 보내자</h3>
 	<form method="post" action="msg">
 		<input type="hidden" value="${user.seq }" name="sender">
 		<input type="hidden" value="${r.seq }" name="receiver">
 		<div class="form-group">
	 	<label>To</label>
	 	<input type="text" class="form-control" value="${r.userId }" disabled="disabled"> <!-- user.getUserid(); -->
 		</div>
 		<div class="form-group">
	 	<label>Msg</label>
	 	<textarea class="form-control" name="msg">일단 넣어보자!!!!!!!</textarea>
 		</div>
 		<div class="form-group">
 		<input type="submit" value="쪽지전송" class="form-control">
 		</div>
 	</form>
 	</div>
 </div>
 </div>
 </body>
</html>