<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<script src="<c:url value="resources/js/viewProduct.js"/>"></script>
		<div id="content">
			<h2><c:out value="${p.name }"/></h2>
			<h3><c:choose><c:when test="${p.rating > 0 }"><c:forEach begin="1" end="${p.rating }">&#9733;</c:forEach><c:forEach begin="${p.rating + 1 }" end="5">&#9734;</c:forEach></c:when><c:otherwise>Not Rated</c:otherwise></c:choose></h3>
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
				<c:when test="${not empty review || canReview}">
					<c:if test="${not empty review }">
						<div id="user-review">
						Your review: <p id="review-rating"><c:forEach begin="1" end="${review.rating }">&#9733;</c:forEach><c:forEach begin="${review.rating + 1 }" end="5">&#9734;</c:forEach></p>
						<p id="review-content"><c:out value="${review.review }"/></p>
						<button id="button-update">Update Review</button>
						</div>
					</c:if>
					<form id="reviewForm" onsubmit="return viewProduct.checkReview();">
						Add Review:
						<input type="hidden" value="${sessionToken }" id="review-token" name="token"/>
						Rating: <input type="radio" name="rating" id="rating-1" value="1" <c:if test="${review.rating == 1 }">checked</c:if>/>
						<label for="rating-1">1</label>
						<input type="radio" name="rating" id="rating-2" value="2" <c:if test="${review.rating == 2 }">checked</c:if>/>
						<label for="rating-2">2</label>
						<input type="radio" name="rating" id="rating-3" value="3" <c:if test="${review.rating == 3 }">checked</c:if>/>
						<label for="rating-3">3</label>
						<input type="radio" name="rating" id="rating-4" value="4" <c:if test="${review.rating == 4 }">checked</c:if>/>
						<label for="rating-4">4</label>
						<input type="radio" name="rating" id="rating-5" value="5" <c:if test="${review.rating == 5 }">checked</c:if>/>
						<label for="rating-5">5</label>
						<textarea id="review" name='review'><c:if test="${not empty review }"><c:out value="${review.review }"/></c:if></textarea>
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
				<h2>Reviews</h2>
				<c:forEach items="${reviews }" var="r">
				<div>
					<c:out value="${r.username }"/>
					Rating: 
					<c:forEach begin="1" end="${r.rating }">&#9733;</c:forEach><c:forEach begin="${r.rating + 1 }" end="5">&#9734;</c:forEach>
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