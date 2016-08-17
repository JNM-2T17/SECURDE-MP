<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>
		<div id="content">
	
			<div id="sectionTitle"><h2>Sales</h2></div>
			
			<div id="fullContent">
				<div class="thirdDiv">
					<h3 class="accentText">Total: &#8369;<fmt:formatNumber pattern="0.00" value="${total }"/></h3>
				</div>
			
				<div class="thirdDiv">
					<h3>Sales by Type</h3>
					<table class="manageTable amTable">
						<tr>
							<th>Type</th>
							<th class="alignRight">Sales</th>
						</tr>
						<c:forEach items="${byType }" var="sm">
						<tr>
							<td><c:out value="${sm.label }"/></td>
							<td class="alignRight">&#8369;<fmt:formatNumber pattern="0.00" value="${sm.total }"/></td>
						</tr>
						</c:forEach>
					</table>
				</div>
				
				<div class="thirdDiv">
					<h3>Sales by Item</h3>
					<table class="manageTable amTable">
						<tr>
							<th>Item</th>
							<th class="alignRight">Sales</th>
						</tr>
						<c:forEach items="${byItem }" var="sm">
						<tr>
							<td><c:out value="${sm.label }"/></td>
							<td class="alignRight">&#8369;<fmt:formatNumber pattern="0.00" value="${sm.total }"/></td>
						</tr>
						</c:forEach>
					</table>
				</div>
				
				<div class="clear"></div>
			</div>

		</div>
	</div>
<jsp:include page="footer.jsp"/>