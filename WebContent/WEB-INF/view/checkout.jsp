<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
		<script src="<c:url value="resources/js/checkout.js"/>"></script>
		<div id="content">
			<form action="checkout" method="POST" onsubmit="return checkout.checkSubmit();">
				<input type="hidden" name="token" value="${sessionToken }"/>
				<input type="text" name="ccno" id="ccno" placeholder="Credit Card Number"/><br/>
				<input type="radio" name="cardtype" id="visa" value="visa"/>
				<label for="visa">Visa</label>
				<input type="radio" name="cardtype" id="mastercard" value="mastercard"/>
				<label for="mastercard">MasterCard</label><br/>
				Expiration date:<input type="number" name="expmm" id="expmm" placeholder="mm"/><br/>
				<input type="number" name="expyy" id="expyy" placeholder="yyyy"/><br/>
				<input type="number" name="cvc" id="cvc" placeholder="Card Verification Code"/><br/>
				<input type="submit" value="Confirm Transaction"/>
			</form>
		</div>
<jsp:include page="footer.jsp"/>