<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<div id="content">
			<script src="<c:url value="resources/js/search.js"/>"></script>
			<input type="hidden" id="sType" value="${type }"/>
			<div id="sectionTitle">
					<c:if test="${not empty query}" >
					<h2 class="resultsHeader"><span class="resultsText">Results for</span> <span class="resultsQuery"><c:out value="${query }"/></span></h2>
 					</c:if>
				<ul id="typeList">
					<li id="sType-0"><a href="search?type=0&query=<c:out value="${query }"/>">All</a></li>
					<li id="sType-1"><a href="search?type=1&query=<c:out value="${query }"/>">Boots</a></li>
					<li id="sType-4"><a href="search?type=4&query=<c:out value="${query }"/>">Slippers</a></li>
					<li id="sType-3"><a href="search?type=3&query=<c:out value="${query }"/>">Sandals</a></li>
					<li id="sType-2"><a href="search?type=2&query=<c:out value="${query }"/>">Shoes</a></li>
				</ul>
			</div>

			<div id="narrowContent" class="narrower">
			
				<script src="<c:url value="resources/js/filter.js"/>"></script>
				<h3>Filter By</h3>
				
				<ul id="filterList">
					<li><div class="filterCat">Price Range</div>
						<ul>
							<li>&#8369;<input id="minRange" type="number" min="0" step="0.01"/> - &#8369;<input type="number" id="maxRange" min="0" step="0.01"/></li>
						</ul>
					</li>
					<li><div class="filterCat">Customer Ratings</div>
						<ul>
							<li><input class="ratingSearch" data-rating="1" type="checkbox"/> &#9733; &#9734; &#9734; &#9734; &#9734;</li>
							<li><input class="ratingSearch" data-rating="2" type="checkbox"/> &#9733; &#9733; &#9734; &#9734; &#9734;</li>
							<li><input class="ratingSearch" data-rating="3" type="checkbox"/> &#9733; &#9733; &#9733; &#9734; &#9734;</li>
							<li><input class="ratingSearch" data-rating="4" type="checkbox"/> &#9733; &#9733; &#9733; &#9733; &#9734;</li>
							<li><input class="ratingSearch" data-rating="5" type="checkbox"/> &#9733; &#9733; &#9733; &#9733; &#9733;</li>
						</ul>
					</li>
				</ul>
				<jsp:include page="sidefooter.jsp"/>
			</div>

			<div id="wideContent" class="wider">
				<c:if test="${empty products}" >
					<span class="noResults">No results</span>
				</c:if>
				<c:forEach var="p" items="${products }" begin="0" end="24">
				<div id="product-<c:out value="${p.id }"/>" class="product">
					<div class="product-name">
						<a href="viewProduct?id=<c:out value="${p.id }"/>"><c:out value="${p.name }"/></a>
						<c:choose><c:when test="${p.rating > 0 }"><c:forEach begin="1" end="${p.rating }">&#9733;</c:forEach><c:forEach begin="${p.rating + 1 }" end="5">&#9734;</c:forEach></c:when><c:otherwise>Not Rated</c:otherwise></c:choose>
					</div>
					<div class="product-description">
						<c:out value="${p.description }"/>
					</div>
					<div class="product-price">
						&#8369;<fmt:formatNumber pattern="0.00" value="${p.price }"/>
					</div>
				</div>
				</c:forEach>
				<!-- <c:if test="${more }">
				<a href="search?type=<c:out value="${type }"/>&query=<c:out value="${query }"/>&start=<c:out value="${start + 25 }"/>">Load More</a>
				</c:if> -->
			</div>

			<div class="clear"></div>

		</div>
<jsp:include page="footer.jsp"/>