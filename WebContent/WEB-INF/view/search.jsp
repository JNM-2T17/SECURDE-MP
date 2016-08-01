<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<script src="<c:url value="resources/js/pmhome.js"/>"></script>
		<!-- <a href="addProduct">Add Product</a> -->
		<div id="content">

			<div id="sectionTitle">
					<c:if test="${not empty query}" >
					<h2 class="resultsHeader"><span class="resultsText">Results for</span> <span class="resultsQuery"><c:out value="${query }"/></span></h2>
 					</c:if>
				<ul id="typeList">
					<li <c:if test="${empty type }">class="active"</c:if>><a href="search?type=0&query=<c:out value="${query }"/>&start=<c:out value="${start }"/>">All</a></li>
					<li <c:if test="${type == 1 }">class="active"</c:if>><a href="search?type=1&query=<c:out value="${query }"/>&start=<c:out value="${start }"/>">Boots</a></li>
					<li <c:if test="${type == 4 }">class="active"</c:if>><a href="search?type=4&query=<c:out value="${query }"/>&start=<c:out value="${start }"/>">Slippers</a></li>
					<li <c:if test="${type == 3 }">class="active"</c:if>><a href="search?type=3&query=<c:out value="${query }"/>&start=<c:out value="${start }"/>">Sandals</a></li>
					<li <c:if test="${type == 2 }">class="active"</c:if>><a href="search?type=2&query=<c:out value="${query }"/>&start=<c:out value="${start }"/>">Shoes</a></li>
				</ul>
			</div>

			<div id="narrowContent" class="narrower">
			
				<script src="<c:url value="resources/js/filter.js"/>"></script>
				<h3>Filter By</h3>
				
				<ul id="filterList">
					<li><div class="filterCat">Price Range</div>
						<ul>
							<li><input type="number" min="0" step="0.01"/> - <input type="number" min="0" step="0.01"/></li>
						</ul>
					</li>
					<li><div class="filterCat">Customer Ratings</div>
						<ul>
							<li><input type="checkbox"/> &#9733; &#9734; &#9734; &#9734; &#9734;</li>
							<li><input type="checkbox"/> &#9733; &#9733; &#9734; &#9734; &#9734;</li>
							<li><input type="checkbox"/> &#9733; &#9733; &#9733; &#9734; &#9734;</li>
							<li><input type="checkbox"/> &#9733; &#9733; &#9733; &#9733; &#9734;</li>
							<li><input type="checkbox"/> &#9733; &#9733; &#9733; &#9733; &#9733;</li>
						</ul>
					</li>
				</ul>
				<jsp:include page="sidefooter.jsp"/>
			</div>

			<div id="wideContent" class="wider">
				<c:forEach var="p" items="${products }" begin="0" end="24">
				<div id="product-<c:out value="${p.id }"/>" class="product">
					<div class="product-name">
						<a href="viewProduct?id=<c:out value="${p.id }"/>"><c:out value="${p.name }"/></a>
					</div>
					<div class="product-description">
						<c:out value="${p.description }"/>
					</div>
					<div class="product-price">
						&#8369;<fmt:formatNumber pattern="#.00" value="${p.price }"/>
					</div>
					<div class="product-add"></div>
				</div>
				</c:forEach>
				<c:if test="${more }">
				<a href="search?type=<c:out value="${type }"/>&query=<c:out value="${query }"/>&start=<c:out value="${start + 25 }"/>">Load More</a>
				</c:if>
			</div>

			<div class="clear"></div>

		</div>
<jsp:include page="footer.jsp"/>