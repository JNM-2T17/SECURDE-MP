var register = (function(){
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
			
			if(!/^[A-Za-z0-9_\-]+$/.test(username)) {
				message = appendMessage(message,"Username is not valid.");
			}
			
			var passCheck = checkPass(password);
			if( passCheck !== true ) {
				message = appendMessage(message,passCheck);
			} else if(password != confirmPassword) {
				message = appendMessage(message,"Passwords don't match.");
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
			
			if(!/^[#]?[0-9]+[A-Za-z-]*$/.test(billHouseNo) ) {
				message = appendMessage(message,"Billing house number is invalid.");
			} 
			
			if(!/^[0-9a-z ,.'-]+$/i.test(billStreet) ) {
				message = appendMessage(message,"Billing street is invalid.");
			} 
			if(!/^[a-z ,.'-]+$/i.test(billSubd) ) {
				message = appendMessage(message,"Billing subdivision is invalid.");
			} 
			if(!/^[a-z ,.'-]+$/i.test(billCity) ) {
				message = appendMessage(message,"Billing city is invalid.");
			} 
			
			if(!/^[a-z0-9]+$/i.test(billPostCode)) {
				message = appendMessage(message,"Billing post code is invalid.");
			}
			
			if(!/^[a-z ]+$/i.test(billCountry) ) {
				message = appendMessage(message,"Billing country is invalid.");
			} 
			
			if(!/^[#]?[0-9]+[A-Za-z-]*$/.test(shipHouseNo) ) {
				message = appendMessage(message,"Shipping house number is invalid.");
			} 
			
			if(!/^[0-9a-z ,.'-]+$/i.test(shipStreet) ) {
				message = appendMessage(message,"Shipping street is invalid.");
			} 
			if(!/^[a-z ,.'-]+$/i.test(shipSubd) ) {
				message = appendMessage(message,"Shipping subdivision is invalid.");
			} 
			if(!/^[a-z ,.'-]+$/i.test(shipCity) ) {
				message = appendMessage(message,"Shipping city is invalid.");
			} 
			
			if(!/^[a-z0-9]+$/i.test(shipPostCode)) {
				message = appendMessage(message,"Shipping post code is invalid.");
			}
			
			if(!/^[a-z ]+$/i.test(shipCountry) ) {
				message = appendMessage(message,"Shipping country is invalid.");
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