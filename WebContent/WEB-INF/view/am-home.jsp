<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>
	<div id="content">
		<h1>Total Sales: <fmt:formatNumber pattern="0.00" value="${total }"/> Php</h1>
		<h1>Sales by Type</h1>
		<table>
			<tr>
				<th>Type</th>
				<th>Sales</th>
			</tr>
			<c:forEach items="${byType }" var="sm">
			<tr>
				<td><c:out value="${sm.label }"/></td>
				<td><fmt:formatNumber pattern="0.00" value="${sm.total }"/> Php</td>
			</tr>
			</c:forEach>
		</table>
		<h1>Sales by Item</h1>
		<table>
			<tr>
				<th>Item</th>
				<th>Sales</th>
			</tr>
			<c:forEach items="${byItem }" var="sm">
			<tr>
				<td><c:out value="${sm.label }"/></td>
				<td><fmt:formatNumber pattern="0.00" value="${sm.total }"/> Php</td>
			</tr>
			</c:forEach>
		</table>
	</div>
<jsp:include page="footer.jsp"/>