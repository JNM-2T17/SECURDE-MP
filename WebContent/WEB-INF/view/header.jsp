<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html>
	<head>
		<title>Talaria Footwear</title>
		<link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
		<link rel="stylesheet" href="<c:url value="resources/font-awesome/css/font-awesome.min.css"/>"/>
		<link rel="shortcut icon" href="<c:url value="resources/images/icon.png"/>"/>
		<script src="<c:url value="resources/js/jquery.min.js"/>"></script>
		<script src="<c:url value="resources/js/jquery-migrate-1.2.1.min.js"/>"></script>
		<script src="<c:url value="resources/js/login.js"/>"></script>
	</head>
	<body>
		<div id="container">
			<div id="header">
				<div id="header-left">
					<h1><span class="hidden">Talaria</span></h1>
					<c:if test="${empty sessionScope.sessionUser || sessionScope.sessionUser.searchProduct }">
					<input id="search" placeholder="search item"/>
					</c:if>
				</div>
				<div id="header-right">
					<c:choose>
					<c:when test="${empty sessionScope.sessionUser }">
					<button id="button-login">Login</button>
					</c:when>
					<c:otherwise>
					<a href="logout">Logout</a>
					<c:if test="${sessionScope.sessionUser.purchaseProduct }">
					<a id="button-shoppingCart">Shopping Cart</a>
					</c:if>
					</c:otherwise>
					</c:choose>
				</div>

				<div class="clear"></div>
			</div>