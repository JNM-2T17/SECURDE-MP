<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<div id="content">
		
			<div id="sectionTitle"><h2>Shopping Cart</h2></div>
			
			<c:choose>
			<c:when test="${empty sessionCart.purchases }">
				<div id="narrowContent">
					No products in cart
				</div>
				<div class="clear"></div>
			</c:when>
			<c:otherwise>
				<div id="narrowContent" class="narrower">
					<script src="<c:url value="resources/js/filter.js"/>"></script>
					Total: &#8369; <fmt:formatNumber pattern="#.00" value="${sessionCart.total }"/>
					<button onclick="window.location='checkout';">Proceed to Checkout</button>
					<jsp:include page="sidefooter.jsp"/>
				</div>
				
				<div id="wideContent" class="wider">
					<c:forEach var="p" items="${sessionCart.purchases }">
					<div id="product-<c:out value="${p.item.id }"/>" class="product">
						<div class="product-name">
							<a href="viewProduct?id=<c:out value="${p.item.id }"/>"><c:out value="${p.item.name }"/></a>
						</div>
						<div class="product-description">
							&#8369;<fmt:formatNumber pattern="#.00" value="${p.item.price }"/> x <c:out value="${p.quantity }"/> = &#8369;<fmt:formatNumber pattern="#.00" value="${p.total }"/>
						</div>
						<div class="product-price">
							<button onclick="cart.delete(<c:out value="${p.item.id }"/>);"><i class="fa fa-trash"></i></button>
						</div>
					</div>
					</c:forEach>
				</div>
	
				<div class="clear"></div>
			</c:otherwise>
			</c:choose>

		</div>
<jsp:include page="footer.jsp"/>