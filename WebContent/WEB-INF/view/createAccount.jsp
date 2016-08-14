<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
			<script src="<c:url value="resources/js/createAccount.js"/>"></script>
			
		<div id="content">
		
			<div id="sectionTitle">
				<h2>Create an Account</h2>
			</div>
			
			<div id="narrowContent">
				<img src="<c:url value="resources/images/registerbg.png"/>" />
				<jsp:include page="sidefooter.jsp"/>
			</div>
			
			<div id="wideContent">
				<form action="createAccount" method="POST" onsubmit="return createAccount.checkSubmit();">
					<table id="regForm">
						<tr>
							<th></th>
							<th>Authorization</th>
						</tr>
						<tr>
							<td>Your password</td>
							<td>
								<input type="password" name="authPassword" id="authPassword"/>
							</td>
						</tr>
						<tr>
							<th></th>
							<th>Account Details</th>
						</tr>
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
							<td>Role</td>
							<td>
								<input name="role" type="radio" id="pm" value="2" checked />
								<label for="pm">Product Manager</label>
								<input name="role" type="radio" id="am" value="3"/>
								<label for="am">Accounting Manager</label><br/>
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
					</table>
					<input type="hidden" value="${sessionToken }" name="token"/>
					<input type="submit" value="Create account &gt;" id="regButton"/>
				</form>
			</div>
			
			<div class="clear"></div>
			<!--  <form action="createAccount" method="POST" onsubmit="return createAccount.checkSubmit();">
				<input type="password" name="authPassword" placeholder="Reenter your password" id="authPassword"/><br/>
				<input name="role" type="radio" id="pm" value="2" checked />
				<label for="pm">Product Manager</label>
				<input name="role" type="radio" id="am" value="3"/>
				<label for="am">Accounting Manager</label><br/>
				<input type="text" name="username" placeholder="Username" id="username"/><br/>
				<input type="password" name="password" placeholder="Password" id="password"/><br/>
				<input type="password" name="confirmPassword" placeholder="Confirm Password" id="confirmPassword"/><br/>
				<input type="text" name="fname" placeholder="First Name" id="fname"/><br/>
				<input type="text" name="mi" placeholder="Middle Initial" id="mi"/><br/>
				<input type="text" name="lname" placeholder="Last Name" id="lname"/><br/>
				<input type="text" name="email" placeholder="Email Address" id="email"/><br/>
				<input type="submit" value="Create Account"/>
			</form> -->
		</div>
<jsp:include page="footer.jsp"/>