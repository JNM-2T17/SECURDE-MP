$(document).ready(function(){
	$("#button-login").click(function(){
		LoginNS.clickLogin();
	});
	
	$("#button-register").click(function(){
		window.location = "register";
	});
	
	$("#button-cart").click(function(){
		window.location = "shoppingCart";
	});
});

var LoginNS = (function() {
	var visible = false;
	return {
		clickLogin : function(){
			if(!visible){
				$("#loginForm").css("display", "block");
				visible = true;
			} else {
				$("#loginForm").css("display", "none");
				visible = false;
			}
		}
	};
})();