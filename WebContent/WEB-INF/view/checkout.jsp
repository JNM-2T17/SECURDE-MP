<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>
		<div id="content">
			
			<div id="narrowContent">
				<img src="<c:url value="resources/images/registerbg.png"/>" />
				<jsp:include page="sidefooter.jsp"/>
			</div>
			<script src="<c:url value="resources/js/checkout.js"/>"></script>
			<div id="wideContent">
				<h2 id="regTitle">Checkout</h2>
				
				<form action="checkout" method="POST" onsubmit="return checkout.checkSubmit();">
					<input type="hidden" name="token" value="${sessionToken }"/>
					
					<table id="regForm">
						<tr>
							<td>Credit Card Number</td>
							<td>
								<input type="text" name="ccno" id="ccno"/>
							</td>
						</tr>
						<tr>
							<td>Type</td>
							<td>
								<input type="radio" name="cardtype" id="visa" value="visa"/><label for="visa">Visa</label>
								<input type="radio" name="cardtype" id="mastercard" value="mastercard"/><label for="mastercard">MasterCard</label>
							</td>
						</tr>
						<tr>
							<td>Expiration Date</td>
							<td>
								<div class="regRow regRowHalf">
									<input type="number" min="1" max="12" step="1" name="expmm" id="expmm" placeholder="mm"/><input type="number"  min="2016" step="1" name="expyy" id="expyy" placeholder="yyyy"/>
								</div>
							</td>
						</tr>
						<tr>
							<td>Card Verification Code</td>
							<td><input type="number" min="0" max="9999" step="1" name="cvc" id="cvc"/></td>
						</tr>
					</table>
					
					<input type="submit" value="Confirm Transaction &gt;" id="regButton"/>
				</form>
			</div>

			<div class="clear"></div>
			
			
		</div>
<jsp:include page="footer.jsp"/>