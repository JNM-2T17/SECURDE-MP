<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html>
	<head>
		<title>Talaria Footwear</title>
		<link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
		<link rel="stylesheet" href="<c:url value="resources/font-awesome/css/font-awesome.min.css"/>"/>
		<link rel="shortcut icon" href="<c:url value="resources/images/logo-red.png"/>"/>
		<script src="<c:url value="resources/js/jquery.min.js"/>"></script>
		<script src="<c:url value="resources/js/jquery-migrate-1.2.1.min.js"/>"></script>
	</head>
	<body>
		<input type="hidden" value="${error }" id="error" />
		
		<div id="container">
			<div id="header">
				<div id="header-left">
					<a href="."><h1><span class="hidden">Talaria</span></h1></a>
					<form action="search">
					<input id="search" value="<c:out value="${query }"/>" name="query" placeholder="search item"/>
					<select id="searchType" name="type">
						<option value="0">All</option>
						<option value="1">Boots</option>
						<option value="2">Shoes</option>
						<option value="3">Sandals</option>
						<option value="4">Slippers</option>
					</select>
					</form>
				</div>
				<div id="header-right">
					<c:choose>
					<c:when test="${empty sessionScope.sessionUser }">
					<button id="button-register">Register</button>
					<button id="button-login">Login</button>
					</c:when>
					<c:otherwise>
					<h2>Welcome, <c:out value="${sessionUser.username }"/></h2>
					<a href="editAccount">Edit Account</a>
					<a href="logout">Logout</a>
					<c:if test="${sessionScope.sessionUser.purchaseProduct }">
					<a id="button-shoppingCart" href="shoppingCart">Shopping Cart</a>
					</c:if>
					</c:otherwise>
					</c:choose>
				</div>

				<div class="clear"></div>
			</div>
			<script src="<c:url value="resources/js/login.js"/>"></script>
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
			