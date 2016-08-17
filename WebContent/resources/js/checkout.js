var checkout = (function(){
	return {
		checkSubmit : function() {
			var ccno = $("#ccno").val();
			var cardtype = $("input[name='cardtype']:checked").attr('id');
			var expmm = $("#expmm").val();
			var expyy = $("#expyy").val();
			var cvc = $("#cvc").val();
			
			var message = "";
			
			
			if( !/^([0-9]{16}|([0-9]{4}\-){3}[0-9]{4})$/.test(ccno)) {
				message = appendMessage(message,"Credit card number is invalid.");
			}
			if( !/^(visa|mastercard)$/.test(cardtype)) {
				message = appendMessage(message,"Card Type is invalid.");
			}
			if( !/^((0|)[1-9]|1[0-2])$/.test(expmm)) {
				message = appendMessage(message,"Expiration Month is invalid.");
			}
			if( !/^2[0-9]{3}$/.test(expyy)) {
				message = appendMessage(message,"Expiration Year is invalid.");
			}
			if( !/^[0-9]{3}$/.test(cvc)) {
				message = appendMessage(message,"Card verification code is invalid.");
			}
			
			if(message.length == 0 ) {
				return true;
			} else {
				showError(message);
				return false;
			}
		}
	};
})();