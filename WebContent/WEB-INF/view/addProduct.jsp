<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
		<form action="addProduct" method="POST">
			<input placeholder="Name" name="name"/><br/>
			<select name="itemtype">
				<option value="1">Boots</option>
				<option value="2">Shoes</option>
				<option value="3">Sandals</option>
				<option value="4">Slippers</option>
			</select><br/>
			Description: <textarea name="description"></textarea><br/>
			<input placeholder="Price" name="price" type="number" /><br/>
			<input type="submit" value="Add Product"/><br/>
		</form>
<jsp:include page="footer.jsp"/>