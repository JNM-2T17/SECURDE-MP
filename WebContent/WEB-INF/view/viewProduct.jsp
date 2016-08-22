<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<script src="<c:url value="resources/js/viewProduct.js"/>"></script>
		<div id="content">
				<div id="sectionTitle">
					<h2></h2>
				</div>
				<input type="hidden" id="username" value="<c:out value="${sessionUser.username }"/>"/>
				<div id="narrowContent" class="narrower">
					<h2 id="viewProductTitle"><c:out value="${p.name }"/></h2>
					<div id="productPrice">
						&#8369; <fmt:formatNumber pattern="#,##0.00" value="${p.price }"/>
					</div>
					<div id="productRating">
						<c:choose><c:when test="${p.rating > 0 }"><c:forEach begin="1" end="${p.rating }">&#9733;</c:forEach><c:forEach begin="${p.rating + 1 }" end="5">&#9734;</c:forEach></c:when><c:otherwise>Not Rated</c:otherwise></c:choose>
					</div>
					<div id="productOptions">
						<c:if test="${not empty sessionUser }">
							<c:if test="${sessionUser.role == 1 }">
							<form id="addForm" action="addToCart" method="post" onsubmit="return viewProduct.checkSubmit();">
								<input type="hidden" value="${sessionToken }" name="token"/>
								<input type="hidden" id="productId" name="productId" value="<c:out value="${p.id }"/>"/>
								<input class="addToCart-quantity" type="number" name="quantity" min="1" step="1" id="quantity" placeholder="quantity"/>
								<input class="addToCartButton" type="submit" value="Confirm"/>
							</form>
							<button id="addButton" class="addToCartButton">Add to Cart</button>
							<div class="clear"></div>
							<c:choose>
							<c:when test="${not empty review || canReview}">
								<div id="userReview">
								<c:choose>
								<c:when test="${not empty review }">
									<div id="user-review">
									<b>Your Review</b> <span id="userReview-rating"><c:forEach begin="1" end="${review.rating }">&#9733;</c:forEach><c:forEach begin="${review.rating + 1 }" end="5">&#9734;</c:forEach></span>
									<p id="review-content"><c:out value="${review.review }"/></p>
									<button id="button-update" class="regButton">Update Review</button>
									</div>
								</c:when>
								<c:otherwise>
									<div style="display: none" id="user-review">
									<b>Your Review</b> <span id="userReview-rating"></span>
									<p id="review-content"></p>
									<button id="button-update" class="regButton">Update Review</button>
									</div>
								</c:otherwise>
								</c:choose>
								
								<form id="reviewForm" onsubmit="return viewProduct.checkReview();">
									<b>Add Review</b>
									<input type="hidden" value="${sessionToken }" id="review-token" name="token"/>
									<input class="reviewRating" type="radio" name="rating" id="rating-1" value="1" <c:if test="${review.rating == 1 }">checked</c:if>/>
									<input class="reviewRating" type="radio" name="rating" id="rating-2" value="2" <c:if test="${review.rating == 2 }">checked</c:if>/>
									<input class="reviewRating" type="radio" name="rating" id="rating-3" value="3" <c:if test="${review.rating == 3 }">checked</c:if>/>
									<input class="reviewRating" type="radio" name="rating" id="rating-4" value="4" <c:if test="${review.rating == 4 }">checked</c:if>/>
									<input class="reviewRating" type="radio" name="rating" id="rating-5" value="5" <c:if test="${review.rating == 5 }">checked</c:if>/>
									<textarea id="review" name='review'><c:if test="${not empty review }"><c:out value="${review.review }"/></c:if></textarea>
									<input class="regButton" type="submit" value="Send Review"/>
								</form>
								</div>
							</c:when>
							<c:otherwise>
							</c:otherwise>
							</c:choose>
							</c:if>
						</c:if>
					</div>
					
					<jsp:include page="sidefooter.jsp"/>
				</div>
				
				<div id="wideContent" class="wider">
				
					<div id="viewProduct">
						<h3>Details</h3>
						<p><c:out value="${p.description }"/></p>
						
						<h3>Customer Ratings</h3>
						<div id="reviews">
						<c:choose>
							<c:when test="${empty reviews }">
								No reviews yet
							</c:when>
							<c:otherwise>
								<c:forEach items="${reviews }" var="r">
								<div class="productReview" <c:if test="${sessionUser.username == r.username }"> id="activeReview"</c:if> >
									<div class="productReview-name"><c:out value="${r.username }"/></div>
									<div class="productReview-rating"><c:forEach begin="1" end="${r.rating }">&#9733;</c:forEach><c:forEach begin="${r.rating + 1 }" end="5">&#9734;</c:forEach></div>
									<div class="productReview-review"><c:out value="${r.review }"/></div>
								</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
<jsp:include page="footer.jsp"/>