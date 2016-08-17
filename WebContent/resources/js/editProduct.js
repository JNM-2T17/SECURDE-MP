var editProduct = (function() {
	return {
		checkSubmit : function() {
			var name = $("#name").val();
			var itemtype = $("#itemtype").val();
			var description = $("#description").val();
			var price = $("#price").val();
			
			var message = "";
			
			if( !/^[A-Za-z0-9!., \-'&]+$/.test(name) ) {
				message = appendMessage(message,"Product Name is invalid.");
			}
			
			if(!/^[1-4]$/.test(itemtype) ) {
				message = appendMessage(message,"Item Type is invalid.");
			}
			
			if( description.length == 0 ) {
				message = appendMessage(message,"Description cannot be empty.");
			}
			
			console.log(price + " " + typeof(price));
			console.log(/^([0-9]+([.][0-9]{2})?|[0-9]*[.][0-9]{2})$/.test(price));
			if( !/^([0-9]+([.][0-9]{2})?|[0-9]*[.][0-9]{2})$/.test(price) ) {
				message = appendMessage(message,"Price is invalid.");
			}
			
			if( message.length > 0 ) {
				showError(message);
				return false;
			}
			return true;
		}
	};
})();

$(document).ready(function() {
	var typeId = $("#typeId").val();
	console.log(typeId);
	$("#itemtype").val(typeId + "");
});