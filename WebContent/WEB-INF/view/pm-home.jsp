<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<script src="<c:url value="resources/js/pmhome.js"/>"></script>
		<a href="addProduct">Add Product</a>
		<c:choose>
		<c:when test="${empty products }">
		<h1>No Products</h1>
		</c:when>
		<c:otherwise>
		<h1>Products</h1>
		<table>
			<tr>
				<th>Name</th>
				<th>Type</th>
				<th>Description</th>
				<th>Price</th>	
				<th>Edit</th>	
				<th>Delete</th>				
			</tr>
			<c:forEach var="p" items="${products }">
			<tr id="product-<c:out value="${p.id }"/>">
				<td><c:out value="${p.name }"/></td>
				<td><c:out value="${p.itemtype }"/></td>
				<td><c:out value="${p.description }"/></td>
				<td><fmt:formatNumber pattern="#.00" value="${p.price }"/> Php</td>
				<td><a href="editProduct?id=<c:out value="${p.id }"/>">Edit</a></td>
				<td><button onclick="submit(<c:out value="${p.id }"/>)"><i class="fa fa-trash"></i></button></td>
			</tr>
			</c:forEach>
		</table>
		</c:otherwise>
		</c:choose>
<jsp:include page="footer.jsp"/>