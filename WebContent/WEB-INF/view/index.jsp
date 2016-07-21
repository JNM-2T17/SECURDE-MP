<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<jsp:include page="header.jsp"/>
			<div id="content">

				<div id="sectionTitle">
					<h2>Welcome!</h2>
				</div>

				<div id="narrowContent">
					<p>To begin, search for an item above, or simply choose one of the categories on the right to start browsing.</p>

					<p>Remember to register or log into your Talaria&#8482; account in order to purchase our products.</p>

					<div id="footer">
						<a href="">Contact Us</a> :: <a href="">About Us</a> <span class="copyright-text">&copy; 2016</span>
					</div>
				</div>

				<div id="wideContent">
					<ul id="iconMenu">
						<li>
							<a href="">
								<img src="<c:url value="resources/images/iconmenu-boots.jpg"/>" />
								<div class="iconMenu-text">Boots</div>
							</a>
						<li>
						<li>
							<a href="">
								<img src="<c:url value="resources/images/iconmenu-flipflops.jpg"/>" />
								<div class="iconMenu-text">Slippers</div>
							</a>
							
						<li>
						<li>
							<a href="">
								<img src="<c:url value="resources/images/iconmenu-sandals.jpg"/>" />
								<div class="iconMenu-text">Sandals</div>
							</a>
							
						<li>
						<li>
							<a href="">
								<img src="<c:url value="resources/images/iconmenu-shoes.jpg"/>" />
								<div class="iconMenu-text">Shoes</div>
							</a>
							
						<li>
					</ul>
				</div>

				<div class="clear"></div>

			</div>
<jsp:include page="footer.jsp"/>