<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>

		<input type="hidden" value="${sessionToken }" id="token" name="token"/>
		<script src="<c:url value="resources/js/pmhome.js"/>"></script>

		<div id="content">
			<div id="sectionTitle">
				<h2>Products</h2>
			</div>
			
			<div id="narrowContent" class="narrower">
				<h3>Product Manager Actions</h3>
				<a href="addProduct" class="regButton">Add Product</a>
				<jsp:include page="sidefooter.jsp"/>
			</div>
			
			<div id="wideContent" class="wider">
				<c:choose>
				<c:when test="${empty products }">
				<h2>No Products</h2>
				</c:when>
				<c:otherwise>
				<table class="manageTable">
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
						<td class="alignRight">&#8369;<fmt:formatNumber pattern="0.00" value="${p.price }"/></td>
						<td><a href="editProduct?id=<c:out value="${p.id }"/>"><i class="fa fa-edit"></i></a></td>
						<td><button onclick="submit(<c:out value="${p.id }"/>)"><i class="fa fa-trash"></i></button></td>
					</tr>
					</c:forEach>
				</table>
				</c:otherwise>
				</c:choose>
			</div>
			
			<div class="clear"></div>
		</div>
<jsp:include page="footer.jsp"/>