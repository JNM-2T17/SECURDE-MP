<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<script src="<c:url value="resources/js/pmhome.js"/>"></script>
		<!-- <a href="addProduct">Add Product</a> -->
		<div id="content">

			<div id="sectionTitle">
				<h2 class="resultsHeader"><span class="resultsText"><c:if test="${empty products }">No </c:if>Results for</span> <span class="resultsQuery"><c:out value="${query }"/></span></h2>
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
					<li><div class="filterCat">Special Offers</div>
						<ul>
							<li><input type="checkbox"/> Items on sale</li>
							<li><input type="checkbox"/> Items NOT on sale</li>
						</ul>
					</li>
					<li><div class="filterCat">Price</div>
						<ul>
							<li><input type="checkbox"/> &#8369;2000 and below</li>
							<li><input type="checkbox"/> &#8369;2000 - &#8369;5000</li>
							<li><input type="checkbox"/> &#8369;5000 - &#8369;10000</li>
							<li><input type="checkbox"/> &#8369;10000 - &#8369;20000</li>
							<li><input type="checkbox"/> &#8369;20000 and above</li>
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
					<c:out value="${p.name }"/><br/>
					<c:out value="${p.itemtype }"/><br/>
					<c:out value="${p.description }"/><br/>
					Price: <fmt:formatNumber pattern="#.00" value="${p.price }"/> Php<br/>
					<a href="viewProduct?id=<c:out value="${p.id }"/>">View Product</a>
				</div>
				</c:forEach>
				<c:if test="${more }">
				<a href="search?type=<c:out value="${type }"/>&query=<c:out value="${query }"/>&start=<c:out value="${start + 25 }"/>">Load More</a>
				</c:if>
			</div>

			<div class="clear"></div>

		</div>
<jsp:include page="footer.jsp"/>