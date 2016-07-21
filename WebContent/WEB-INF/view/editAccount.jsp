<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
			<form action="editAccount" method="POST">
				<input type="password" name="oldPassword" placeholder="Old Password" id="oldPass"/><br/>
				<input type="password" name="newPassword" placeholder="New Password" id="newPass"/><br/>
				<input type="password" name="confirmPassword" placeholder="Confirm Password" id="confirmPass"/><br/>
				<input type="submit" value="Change Password"/>
			</form>
<jsp:include page="footer.jsp"/>