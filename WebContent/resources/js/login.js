$(document).ready(function(){
	$("#button-login").click(function(){
		LoginNS.clickLogin();
	});
});

var LoginNS = {
	clickLogin : function(){
		if($("#loginForm").css("display") == "none"){
			$("#loginForm").css("display", "block");
		}
		else if($("#loginForm").css("display") == "block"){
			$("#loginForm").css("display", "none");
		}
	}
}