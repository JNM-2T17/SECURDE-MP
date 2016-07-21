<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
			<script src="<c:url value="resources/js/script.js" />"></script>
			
			<div id="loginForm">
				<form action="login" method="POST">
					<div class="form-group">
						<div class="form-label">Username</div>
						<input class="form-input" name="username"/>
					</div>
					<div class="form-group">
						<div class="form-label">Password</div>
						<input class="form-input" type="password" name="password"/>
					</div>
					<input class="loginButton" type="submit" value="Login"/>
				</form>
			</div>
		</div>
	</body>
</html>