<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
			<form action="createAccount" method="POST">
				<input type="password" name="authPassword" placeholder="Reenter your password" id="authPassword"/><br/>
				<input name="role" type="radio" id="pm" value="2" checked />
				<label for="pm">Product Manager</label>
				<input name="role" type="radio" id="am" value="3"/>
				<label for="am">Accounting Manager</label><br/>
				<input type="text" name="username" placeholder="Username" id="username"/><br/>
				<input type="password" name="password" placeholder="Password" id="password"/><br/>
				<input type="password" name="confirmPassword" placeholder="Confirm Password" id="password"/><br/>
				<input type="text" name="fname" placeholder="First Name" id="fname"/><br/>
				<input type="text" name="mi" placeholder="Middle Initial" id="mi"/><br/>
				<input type="text" name="lname" placeholder="Last Name" id="lname"/><br/>
				<input type="text" name="email" placeholder="Email Address" id="email"/><br/>
				<input type="submit" value="Create Account"/>
			</form>
<jsp:include page="footer.jsp"/>