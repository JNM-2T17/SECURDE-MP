function showError(message) {
    if(message.length > 0){
        $("#message-display").css("background-color"
                                    , "rgba(190, 70, 70, 0.8)");
        $("#message-display .head").html("Error");
        $("#message-display .content").html(message);
        $("#message-display")
            .css("bottom", "10px")
            .css("display", "inline-block")
            .animate(
                { bottom: "30px", opacity: 1 },
                800
             );
        setTimeout(function(){
            closeMessage();
        }, 5000);
    }
}

function closeMessage(){
	$("#message-display")
	    .animate(
	        { bottom: "10px", opacity: 0 },
	        800
	     );
	setTimeout(function(){
	    $("#message-display").css("display", "none")
	}, 800);
}

function showMessage(message) {
	$("#message-display").css("background-color", "rgba(0, 172, 0, 0.95)");
    $("#message-display .head").html("Success");
    $("#message-display .content").html(message);
    $("#message-display")
        .css("bottom", "10px")
        .animate(
            { bottom: "30px", opacity: 1 },
            800
         );
    setTimeout(function(){
        closeMessage();
    }, 5000);
}

function appendMessage(message, add) {
	return message + (message.length == 0 ? "" : "\n") + add;
}

function checkPass(pass) {
	var message = "";
	if( pass.length < 8 ) {
		message = appendMessage(message,"Password must be at least 8 characters long.");
	}
	
	var cap = false;
	var low = false;
	var num = false;
	var spec = false;
	
	for(var i = 0; i < pass.length; i++) {
		if( /[A-Z]/.test(pass.substring(i,i + 1))) {
			cap = true;
		} else if(/[a-z]/.test(pass.substring(i,i + 1))) {
			low = true;
		} else if(/[0-9]/.test(pass.substring(i,i + 1))) {
			num = true;
		} else {
			spec = true;
		}
	}
	
	if( !cap ) {
		message = appendMessage(message,"Password must contain at least one uppercase letter.");
	}
	if( !low ) {
		message = appendMessage(message,"Password must contain at least one lowercase letter.");
	}
	if( !num ) {
		message = appendMessage(message,"Password must contain at least one number.");
	}
	if( !spec ) {
		message = appendMessage(message,"Password must contain at least one non-alphanumeric character.");
	}
	
	return message.length == 0 ? true : message;
}

$(document).ready(function() {
	console.log("Hello world!");
	var error = $("#error").val() ? $("#error").val() : "";
	if( error.length > 0 ) {
		showError(error);
	}
	
	var message= $("#message").val() ? $("#message").val() : "";
	if( message.length > 0 ) {
		showMessage(message);
	}
});