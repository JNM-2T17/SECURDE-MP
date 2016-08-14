var editAccount = (function(){
	return {
		checkSubmit : function() {
			var password = $("#newPass").val();
			var confirmPassword = $("#confirmPass").val();
			
			var message = "";
			
			var passCheck = checkPass(password);
			if( passCheck !== true ) {
				message = appendMessage(message,passCheck);
			} else if(password != confirmPassword) {
				message = appendMessage(message,"Passwords don't match.");
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