<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
		<script src="<c:url value="resources/js/addProduct.js"/>"></script>
		<form action="addProduct" method="POST" onsubmit="return addProduct.checkSubmit();">
			<input type="hidden" value="${sessionToken }" name="token"/>
			<input placeholder="Name" id="name" name="name"/><br/>
			<select name="itemtype" id="itemtype">
				<option value="1">Boots</option>
				<option value="2">Shoes</option>
				<option value="3">Sandals</option>
				<option value="4">Slippers</option>
			</select><br/>
			Description: <textarea id="description" name="description"></textarea><br/>
			<input id="price" placeholder="Price" name="price" step="0.01" type="number" /><br/>
			<input type="submit" value="Add Product"/><br/>
		</form>
<jsp:include page="footer.jsp"/>