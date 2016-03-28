<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${user.userId }님</title>
</head>
<body>
<jsp:include page="/common/header.jsp"></jsp:include>

<div class="container">
<h3>내 정보 보기</h3>
<div class="alert alert-success">
<h3>${user.userId}</h3>
<p>${user.email}</p>
</div>
</div>
</body>
</html>