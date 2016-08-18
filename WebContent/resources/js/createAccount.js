var createAccount = (function(){
	var usernameReady = true;
	var usernameValid = false;
	$(document).ready(function() {
		usernameReady = false;
		$("#username").change(function() {
			$.ajax({
				url : "checkUsername",
				method : "POST",
				data : {
					token : $("#token").val(),
					username : $("#username").val()
				},
				success : function(a) {
					usernameValid = a === "true";
					usernameReady = true;
				}
			});
		});
	});
	
	return {
		checkSubmit : function() {
			var username = $("#username").val();
			var password = $("#password").val();
			var role = $("input[name='role']:checked").val();
			var confirmPassword = $("#confirmPassword").val();
			var fname = $("#fname").val();
			var mi = $("#mi").val();
			var lname = $("#lname").val();
			var email = $("#email").val();
			
			var message = "";
			while(!usernameReady);
			if(!/^[A-Za-z0-9_\-]+$/.test(username)) {
				message = appendMessage(message,"Username is not valid.");
			} else if(!usernameValid ) {
				message = appendMessage(message,"Username is in use.");
			}
			
			var passCheck = checkPass(password);
			if( passCheck !== true ) {
				message = appendMessage(message,passCheck);
			} else if(password != confirmPassword) {
				message = appendMessage(message,"Passwords don't match.");
			}
			
			if( !/^(2|3)$/.test(role)) {
				message = appendMessage(message,"Please select a valid role");
			}
			
			if(!/^[a-z ,.'-]+$/i.test(fname)) {
				message = appendMessage(message,"First Name is invalid.");	
			}
			
			if(!/^[A-Za-z]{0,2}.?$/.test(mi)) {
				message = appendMessage(message,"Middle Initial is invalid.");	
			}
						
			if(!/^[a-z ,.'-]+$/i.test(lname)) {
				message = appendMessage(message,"Last Name is invalid.");	
			}
			
			if(!/^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/.test(email)) {
				message = appendMessage(message,"Email Address is invalid.");
			}
			
			if( message.length > 0 ) {
				showError(message);
				return false;
			} else {
				return true;
			}
		}
	};
})();