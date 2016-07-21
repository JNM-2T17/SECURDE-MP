<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
		<script src="<c:out value="resources/js/editProduct.js"/>"></script>
		<form action="editProduct" method="POST">
			<input type="hidden" name="id" value="<c:out value="${item.id }"/>"/>
			<input type="hidden" id="typeId" value="${item.typeId }"/>
			<input placeholder="Name" name="name" value="<c:out value="${item.name }"/>"/><br/>
			<select name="itemtype" id="itemtype">
				<option value="1">Boots</option>
				<option value="2">Shoes</option>
				<option value="3">Sandals</option>
				<option value="4">Slippers</option>
			</select><br/>
			Description: <textarea name="description"><c:out value="${item.description }"/></textarea><br/>
			<input placeholder="Price" name="price" type="number" value="<c:out value="${item.price }"/>"/><br/>
			<input type="submit" value="Edit Product"/><br/>
		</form>
<jsp:include page="footer.jsp"/>