<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
			<script src="<c:url value="resources/js/register.js"/>"></script>
			<form action="register" method="POST" onsubmit="return register.checkSubmit();">
				<input type="text" name="username" placeholder="Username" id="username"/><br/>
				<input type="password" name="password" placeholder="Password" id="password"/><br/>
				<input type="password" name="confirmPassword" placeholder="Confirm Password" id="confirmPassword"/><br/>
				<input type="text" name="fname" placeholder="First Name" id="fname"/><br/>
				<input type="text" name="mi" placeholder="Middle Initial" id="mi"/><br/>
				<input type="text" name="lname" placeholder="Last Name" id="lname"/><br/>
				<input type="text" name="email" placeholder="Email Address" id="email"/><br/>
				<h4>Billing Address</h4>
				<input type="text" name="billHouseNo" placeholder="House Number" id="billHouseNo"/><br/>
				<input type="text" name="billStreet" placeholder="Street" id="billStreet"/><br/>
				<input type="text" name="billSubd" placeholder="Subdivision" id="billSubd"/><br/>
				<input type="text" name="billCity" placeholder="City" id="billCity"/><br/>
				<input type="text" name="billPostCode" placeholder="Postal Code" id="billPostCode"/><br/>
				<input type="text" name="billCountry" placeholder="Country" id="billCountry"/><br/>
				<h4>Shipping Address<br/><button type="button" id="button-copy">Copy Billing Address</button></h4>
				<input type="text" name="shipHouseNo" placeholder="House Number" id="shipHouseNo"/><br/>
				<input type="text" name="shipStreet" placeholder="Street" id="shipStreet"/><br/>
				<input type="text" name="shipSubd" placeholder="Subdivision" id="shipSubd"/><br/>
				<input type="text" name="shipCity" placeholder="City" id="shipCity"/><br/>
				<input type="text" name="shipPostCode" placeholder="Postal Code" id="shipPostCode"/><br/>
				<input type="text" name="shipCountry" placeholder="Country" id="shipCountry"/><br/>
				<input type="submit" value="Register"/>
			</form>
<jsp:include page="footer.jsp"/>