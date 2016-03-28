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
 <div class="container">
 <h3>게시판 목록</h3>
 <h4>${curPage }/${totalPage }</h4>
 <table class="table table-hover">
 	<thead>
		<td>글번호</td>
		<td>글제목</td>
		<td>작성일</td>
		<td>조회수</td>
		<td>추천수</td>
		<td>작성자</td>
 	</thead>
 	<tbody>
 <!--  request.getAttribute("postings"); -->
<c:forEach items="${requestScope.xxx }" var="p">
	<tr>
		<td>${p.seq }</td> <!-- p.getSeq() -->
		<td><a href='<%=application.getContextPath() %>/posting?id=${p.seq}'>${p.title }</a></td><!-- p.getTitle() -->
		<td>${p.ctime }</td>
		<td>${p.viewcount}</td>
		<td>${p.recocount}</td>
		<c:if test="${not empty user }">
		<td><a href='<%=application.getContextPath() %>/message?receiver=${p.writer.seq}'>${p.writer.userId}</a></td><!-- p.getWriter().getUserId() -->
		</c:if>
		<c:if test="${empty user }">
		<td>${p.writer.userId}</td><!-- p.getWriter().getUserId() -->
		</c:if>
	</tr>
</c:forEach>
 </tbody>
 </table>
 <!-- page button -->
 <!-- curPage : 현재 보이고 있는 글들의 페이지 번호 -->
 <!-- totalPage : 전체 페이지 갯수 -->
 <nav>
  <ul class="pagination">
  <!-- 
    <li>
      <a href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
   -->
 	<c:forEach begin="1" end="${ totalPage }" var="pn">
    <li <c:if test="${ curPage eq pn }">class="active"</c:if> ><a href="<%=application.getContextPath() %>/postings?page=${ pn }">${pn}</a></li>
    </c:forEach>
    <!-- 
    <li>
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
     -->
    
  </ul>
</nav>
</div>
</body>
</html>