<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
		<a href="createAccount">Create Account</a>
		<c:choose>
		<c:when test="${empty users }">
		<h1>No Users</h1>
		</c:when>
		<c:otherwise>
		<h1>Users</h1>
		<table>
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
<jsp:include page="footer.jsp"/>