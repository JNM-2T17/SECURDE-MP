<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
		<div id="content">
				<div id="sectionTitle">
					<h2>Users</h2>
				</div>
				
				<div id="narrowContent" class="narrower">
					<h3>Administrator Actions</h3>
					<a href="createAccount" class="regButton">Create Account</a>
					<jsp:include page="sidefooter.jsp"/>
				</div>
				
				<div id="wideContent" class="wider">
					<c:choose>
					<c:when test="${empty users }">
					<h2>No Users</h2>
					</c:when>
					<c:otherwise>
					<table class="manageTable">
						<tr>
							<th>Username</th>
							<th>Account Type</th>
							<th>Name</th>
							<th>Email Address</th>
						</tr>
						<c:forEach items="${users }" var="u">
						<tr>
							<td><c:out value="${u.username }"/></td>
							<td><c:out value="${u.roleName }"/></td>
							<td><c:out value="${u.fName } ${u.mi } ${u.lName }"/></td>
							<td><c:out value="${u.emailAddress }"/></td>
						</tr>
						</c:forEach>
					</table>
					</c:otherwise>
					</c:choose>
				</div>
				
				<div class="clear"></div>
		</div>
<jsp:include page="footer.jsp"/>