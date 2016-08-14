<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>
		<script src="<c:out value="resources/js/editProduct.js"/>"></script>
		
		<div id="content">
		
			<div id="sectionTitle">
				<h2>Edit Product</h2>
			</div>
			
			<div id="narrowContent">
				<img src="<c:url value="resources/images/registerbg.png"/>" />
				<jsp:include page="sidefooter.jsp"/>
			</div>
			
			<div id="wideContent">
				<form action="editProduct" method="POST" onsubmit="return editProduct.checkSubmit();">
					<input type="hidden" value="${sessionToken }" name="token"/>
					<input type="hidden" name="id" value="<c:out value="${item.id }"/>"/>
					<input type="hidden" id="typeId" value="${item.typeId }"/>
			
					<table id="regForm">
						<tr>
							<td>Name</td>
							<td>
								<input type="text" id="name" name="name" value="<c:out value="${item.name }"/>"/>
							</td>
						</tr>
						<tr>
							<td>Type</td>
							<td>
								<select name="itemtype" id="itemtype">
									<option value="1">Boots</option>
									<option value="2">Shoes</option>
									<option value="3">Sandals</option>
									<option value="4">Slippers</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Description</td>
							<td>
								<textarea id="description" name="description"><c:out value="${item.description }"/></textarea>
							</td>
						</tr>
						<tr>
							<td>Price</td>
							<td>
								<input placeholder="Price" id="price" step="0.01" name="price" type="number" value="<fmt:formatNumber pattern="0.00" value="${item.price }"/>"/>
							</td>
						</tr>
					</table>
					<input type="submit" value="Edit product &gt;" id="regButton"/>
				</form>
			</div>
			
			<div class="clear"></div>
		</div>
<jsp:include page="footer.jsp"/>