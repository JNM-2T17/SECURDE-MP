var register = (function(){
	function appendMessage(message, add) {
		return message + (message.length == 0 ? "" : "\n") + add;
	}
	return {
		copyBill : function() {
			$("#shipHouseNo").val($("#billHouseNo").val());
			$("#shipStreet").val($("#billStreet").val());
			$("#shipSubd").val($("#billSubd").val());
			$("#shipCity").val($("#billCity").val());
			$("#shipPostCode").val($("#billPostCode").val());
			$("#shipCountry").val($("#billCountry").val());
		},
		checkSubmit : function() {
			var username = $("#username").val();
			var password = $("#password").val();
			var confirmPassword = $("#confirmPassword").val();
			var fname = $("#fname").val();
			var mi = $("#mi").val();
			var lname = $("#lname").val();
			var email = $("#email").val();
			var billHouseNo = $("#billHouseNo").val();
			var billStreet = $("#billStreet").val();
			var billSubd = $("#billSubd").val();
			var billCity = $("#billCity").val();
			var billPostCode = $("#billPostCode").val();
			var billCountry = $("#billCountry").val();
			var shipHouseNo = $("#shipHouseNo").val();
			var shipStreet = $("#shipStreet").val();
			var shipSubd = $("#shipSubd").val();
			var shipCity = $("#shipCity").val();
			var shipPostCode = $("#shipPostCode").val();
			var shipCountry = $("#shipCountry").val();
			
			var message = "";
			
			if( password.length == 0 ) {
				message = appendMessage(message,"Password cannot be empty.");
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

$(document).ready(function(){
	$("#button-copy").click(register.copyBill);
});