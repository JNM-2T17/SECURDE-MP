<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<c:choose>
		<c:when test="${empty sessionCart.purchases }">
		<h1>No Products in Cart</h1>
		</c:when>
		<c:otherwise>
		<h1>Shopping Cart</h1>
		<table>
			<tr>
				<th>Name</th>
				<th>Quantity</th>
				<th>Price</th>
				<th>Total</th>	
				<th>Delete</th>				
			</tr>
			<c:forEach var="p" items="${sessionCart.purchases }">
			<tr id="product-<c:out value="${p.item.id }"/>">
				<td><c:out value="${p.item.name }"/></td>
				<td><c:out value="${p.quantity }"/></td>
				<td><fmt:formatNumber pattern="#.00" value="${p.item.price }"/> Php</td>
				<td><fmt:formatNumber pattern="#.00" value="${p.total }"/> Php</td>
				<td><button onclick="cart.delete(<c:out value="${p.item.id }"/>);"><i class="fa fa-trash"></i></button></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="3">Total</td>
				<td><fmt:formatNumber pattern="#.00" value="${sessionCart.total }"/> Php</td>
				<td colspan="2"></td>
			</tr>
		</table>
		<button onclick="window.location='checkout';">Proceed to Checkout</button>
		</c:otherwise>
		</c:choose>
<jsp:include page="footer.jsp"/>