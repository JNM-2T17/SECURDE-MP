<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<script src="<c:url value="resources/js/viewProduct.js"/>"></script>
		<div id="content">
			<h2><c:out value="${p.name }"/></h2>
			<h3><c:out value="${p.price }"/> Php</h3>
			<p><c:out value="${p.description }"/></p>
			<c:if test="${not empty sessionUser }">
				<button id="addButton">Add to Cart</button>
				<form id="addForm" action="addToCart" method="post" onsubmit="return viewProduct.checkSubmit();">
					<input type="hidden" value="${sessionToken }" name="token"/>
					<input type="hidden" id="productId" name="productId" value="<c:out value="${p.id }"/>"/>
					<input type="number" name="quantity" id="quantity"/>
					<input type="submit" value="Add to Cart"/>
				</form>
				<c:choose>
				<c:when test="${not empty review }">
				<div>
					Your review: <c:out value="${review }"/>
				</div>
				</c:when>
				<c:when test="${canReview }">
					<form onsubmit="viewProduct.checkReview">
						Add Review:
						<textarea id="review"></textarea>
						<input type="submit" value="Send Review"/>
					</form>
				</c:when>
				<c:otherwise>
					<p>You must buy this product before reviewing.</p>
				</c:otherwise>
				</c:choose>
			</c:if>
			<c:choose>
			<c:when test="${empty reviews }">
				<h3>No Reviews Yet</h3>
			</c:when>
			<c:otherwise>
				<c:forEach items="${reviews }" var="r">
				<div>
					<c:out value="${r.username }"/>
					Rating: 
					<c:forEach begin="1" end="${r.rating }">*</c:forEach>
					<c:forEach begin="${r.rating + 1 }" end="5">_</c:forEach>
					<c:out value="${r.review }"/>
				</div>
				</c:forEach>
				<c:if test="${loadMore }">
					<button id="button-load">Load More</button>
				</c:if>
			</c:otherwise>
			</c:choose>
		</div>
<jsp:include page="footer.jsp"/>