<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
		<div id="content">
			<script src="<c:url value="resources/js/register.js"/>"></script>
			
			<br /><br />
			
			<div id="narrowContent">
				<img src="<c:url value="resources/images/registerbg.png"/>" />
				<jsp:include page="sidefooter.jsp"/>
			</div>

			<div id="wideContent">
				<h2 id="regTitle">Register Now!</h2>
				<div id="regDesc">
					It's cool, hip, and trendy, not to mention quick, fun, and easy.
				</div>
				
				<form action="register" method="POST" onsubmit="return register.checkSubmit();">
					<table id="regForm">
						<tr>
							<td>Name</td>
							<td id="regName">
								<input type="text" name="fname" placeholder="First" id="fname"/><input type="text" name="mi" placeholder="M.I" id="mi"/><input type="text" name="lname" placeholder="Last Name" id="lname"/>
							</td>
						</tr>
						<tr>
							<td>Username</td>
							<td>
								<input type="text" name="username" id="username"/>
							</td>
						</tr>
						<tr>
							<td>Email</td>
							<td>
								<input type="text" name="email" id="email"/>
							</td>
						</tr>
						<tr>
							<td>Password</td>
							<td>
								<input type="password" name="password" id="password"/>
							</td>
						</tr>
						<tr>
							<td>Confirm</td>
							<td>
								<input type="password" name="confirmPassword" placeholder="Confirm Password" id="confirmPassword"/>
							</td>
						</tr>
						<tr>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>Billing<br/>Address</td>
							<td>
								<div class="regRow">
									<input type="text" name="billHouseNo" placeholder="House #" id="billHouseNo"/><input type="text" name="billStreet" placeholder="Street" id="billStreet"/>
								</div>
								<div class="regRow">
									<input type="text" name="billSubd" placeholder="Subdivision" id="billSubd"/>
								</div>
								<div class="regRow">
									<input type="text" name="billCity" placeholder="City / Municipality" id="billCity"/>
								</div>
								<div class="regRow">
									<input type="text" name="billCountry" placeholder="Country" id="billCountry"/><input type="text" name="billPostCode" placeholder="ZIP" id="billPostCode"/>
								</div>
							</td>
						</tr>
						<tr>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>Shipping<br/>Address</td>
							<td>
								<div class="regRow">
									<input type="text" name="shipHouseNo" placeholder="House #" id="shipHouseNo"/><input type="text" name="shipStreet" placeholder="Street" id="shipStreet"/>
								</div>
								<div class="regRow">
									<input type="text" name="shipSubd" placeholder="Subdivision" id="shipSubd"/>
								</div>
								<div class="regRow">
									<input type="text" name="shipCity" placeholder="City / Municipality" id="shipCity"/>
								</div>
								<div class="regRow">
									<input type="text" name="shipCountry" placeholder="Country" id="shipCountry"/><input type="text" name="shipPostCode" placeholder="ZIP" id="shipPostCode"/>
								</div>
							</td>
						</tr>
					</table>
					
					<input type="hidden" name="token" value="<c:out value="${sessionToken }"/>"/>

					<input type="submit" value="Register >" id="regButton"/>
				</form>
			</div>

			<div class="clear"></div>
			
			
		</div>
<jsp:include page="footer.jsp"/>