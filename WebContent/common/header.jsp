<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
		<c:if test="${empty user }">
				<ul class="nav navbar-nav navbar-right">
					<li><a href= "<%=application.getContextPath() %>/login">LOGIN</a></li>
	 			</ul>
			</c:if>
			<c:if test="${not empty user }">
				<ul class="nav navbar-nav navbar-right">
				<li><a href="/board/myinfo">${user.userId }님</a></li>
				<!--
				    int unreadMemo = dao.
				    request.setAttribute("unread", unreadMemo ); 
				 -->
				<li><a href="/board/msgbox">쪽지함 <span class="badge">${unread}</span></a></li> 
				<li><a href="/board/logout">Log out</a></li>
				</ul>
			</c:if>
		</div>
		<div class="container-fluid">
			<!-- Logo -->
			<div class="navbar-header">
				<a href="#" class="navbar-brand">Jihyun</a>
			</div>
			<!-- Menu -->
			<div>
			<c:if test="${empty user }">
				<ul class="nav navbar-nav">
					<li class="active"><a href= "<%=application.getContextPath() %>">HOME</a></li> 
	 				<li><a href="/board/postings">글목록</a></li>	
	 			</ul>
			</c:if>
			<c:if test="${not empty user }">
				<ul class="nav navbar-nav">
					<li class="active"><a href= "<%=application.getContextPath() %>">HOME</a></li> 
	 				<li><a href="/board/postings">글목록</a></li>
	 				<li><a href="/board/write">글쓰기</a></li>				
				</ul>
			</c:if>
			</div>
		</div>
	</nav>
