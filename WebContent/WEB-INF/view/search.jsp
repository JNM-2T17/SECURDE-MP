<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<script src="<c:url value="resources/js/pmhome.js"/>"></script>
		<a href="addProduct">Add Product</a>
		<c:choose>
		<c:when test="${empty products }">
		<h1>No Results for ${query }</h1>
		</c:when>
		<c:otherwise>
		<div id="item-list">
			<h1>Results for <c:out value="${query }"/></h1>
			<c:forEach var="p" items="${products }">
			<div id="product-<c:out value="${p.id }"/>" class="product">
				<c:out value="${p.name }"/><br/>
				<c:out value="${p.itemtype }"/><br/>
				<c:out value="${p.description }"/><br/>
				Price: <fmt:formatNumber pattern="#.00" value="${p.price }"/> Php<br/>
				<a href="viewProduct?id=<c:out value="${p.id }"/>">View Product</a>
			</div>
			</c:forEach>
			<c:if test="${not empty start}">
			<a href="search?type=<c:out value="${type }"/>&query=<c:out value="${query }"/>&start=<c:out value="${start }"/>">Load More</a>
			</c:if>
		</div>
		</c:otherwise>
		</c:choose>
<jsp:include page="footer.jsp"/>