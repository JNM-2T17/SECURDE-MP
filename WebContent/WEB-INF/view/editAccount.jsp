<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>

	<script src="<c:url value="resources/js/editAccount.js"/>"></script>
	
	<div id="content">
		
			<div id="sectionTitle">
				<h2>Edit Account</h2>
			</div>
			
			<div id="narrowContent">
				<img src="<c:url value="resources/images/registerbg.png"/>" />
				<jsp:include page="sidefooter.jsp"/>
			</div>
			
			<div id="wideContent">
				<form action="editAccount" method="POST" onsubmit="return editAccount.checkSubmit();">
					<table id="regForm">
						<tr>
							<td>Old Password</td>
							<td>
								<input type="password" name="oldPassword" id="oldPass"/>
							</td>
						</tr>
						<tr>
							<td>New Password</td>
							<td>
								<input type="password" name="newPassword" id="newPass"/>
							</td>
						</tr>
						<tr>
							<td>Confirm New<br/>Password</td>
							<td>
								<input type="password" name="confirmPassword" id="confirmPass"/>
							</td>
						</tr>
					</table>
					<input type="hidden" value="${sessionToken }" name="token"/>
					<input type="submit" value="Change Password &gt;" id="regButton"/>
				</form>
			</div>
			
			<div class="clear"></div>
		</div>
		
		
<jsp:include page="footer.jsp"/>