<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>
		<script src="<c:out value="resources/js/editProduct.js"/>"></script>
		<form action="editProduct" method="POST" onsubmit="return editProduct.checkSubmit();">
			<input type="hidden" value="${sessionToken }" name="token"/>
			<input type="hidden" name="id" value="<c:out value="${item.id }"/>"/>
			<input type="hidden" id="typeId" value="${item.typeId }"/>
			<input placeholder="Name" id="name" name="name" value="<c:out value="${item.name }"/>"/><br/>
			<select name="itemtype" id="itemtype">
				<option value="1">Boots</option>
				<option value="2">Shoes</option>
				<option value="3">Sandals</option>
				<option value="4">Slippers</option>
			</select><br/>
			Description: <textarea id="description" name="description"><c:out value="${item.description }"/></textarea><br/>
			<input placeholder="Price" id="price" step="0.01" name="price" type="number" value="<fmt:formatNumber pattern="0.00" value="${item.price }"/>"/><br/>
			<input type="submit" value="Edit Product"/><br/>
		</form>
<jsp:include page="footer.jsp"/>