<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<script src="<c:url value="resources/js/viewProduct.js"/>"></script>
		<div id="content">
				<div id="sectionTitle">
					<h2></h2>
				</div>
				
				<div id="narrowContent" class="narrower">
					<h2 id="viewProductTitle"><c:out value="${p.name }"/></h2>
					<div id="productPrice">
						&#8369; <c:out value="${p.price }"/>
					</div>
					<div id="productRating">
						<c:choose><c:when test="${p.rating > 0 }"><c:forEach begin="1" end="${p.rating }">&#9733;</c:forEach><c:forEach begin="${p.rating + 1 }" end="5">&#9734;</c:forEach></c:when><c:otherwise>Not Rated</c:otherwise></c:choose>
					</div>
					<div id="productOptions">
						<c:if test="${not empty sessionUser }">
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
							</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					
					<jsp:include page="sidefooter.jsp"/>
				</div>
				
				<div id="wideContent" class="wider">
				
					<div id="viewProduct">
				
						<%-- <div id="productRating">
							<c:choose><c:when test="${p.rating > 0 }"><c:forEach begin="1" end="${p.rating }">&#9733;</c:forEach><c:forEach begin="${p.rating + 1 }" end="5">&#9734;</c:forEach></c:when><c:otherwise>Not Rated</c:otherwise></c:choose>
						</div>
						
						<div id="productPrice">
							&#8369; <c:out value="${p.price }"/>
						</div>
						
						<div id="productOptions">
							
							<c:if test="${not empty sessionUser }">
								<div class="productOptions-option">
									<button id="addToCartButton">Add to Cart</button>
									<form id="addForm" action="addToCart" method="post" onsubmit="return viewProduct.checkSubmit();">
										<input type="hidden" value="${sessionToken }" name="token"/>
										<input type="hidden" id="productId" name="productId" value="<c:out value="${p.id }"/>"/>
										<input type="number" name="quantity" id="quantity"/>
										<input type="submit" value="Add to Cart"/>
									</form>
								</div>
								<c:choose>
								<c:when test="${not empty review || canReview}">
									<c:if test="${not empty review }">
										<div class="productOptions-option">
											<div id="user-review">
											Your review: <p id="review-rating"><c:forEach begin="1" end="${review.rating }">&#9733;</c:forEach><c:forEach begin="${review.rating + 1 }" end="5">&#9734;</c:forEach></p>
											<p id="review-content"><c:out value="${review.review }"/></p>
											<button id="button-update">Update Review</button>
											</div>
										</div>
									</c:if>
									
									<div class="productOptions-option">
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
									</div>
								</c:when>
								<c:otherwise>
									<div class="productOptions-option">You must buy this product before reviewing.</div>
								</c:otherwise>
								</c:choose>
							</c:if>
						</div> --%>
						
						<div class="clear"></div>
							
						
						<h3>Details</h3>
						<p><c:out value="${p.description }"/></p>
						
						<h3>Customer Ratings</h3>
						<c:choose>
							<c:when test="${empty reviews }">
								No reviews yet
							</c:when>
							<c:otherwise>
								<c:forEach items="${reviews }" var="r">
								<div class="productReview">
									<div class="productReview-name"><c:out value="${r.username }"/></div>
									<div class="productReview-rating"><c:forEach begin="1" end="${r.rating }">&#9733;</c:forEach><c:forEach begin="${r.rating + 1 }" end="5">&#9734;</c:forEach></div>
									<div class="productReview-review"><c:out value="${r.review }"/></div>
								</div>
								</c:forEach>
								<c:if test="${loadMore }">
									<button id="button-load">Load More</button>
								</c:if>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
<jsp:include page="footer.jsp"/>