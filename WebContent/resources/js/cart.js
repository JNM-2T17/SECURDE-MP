var cart = (function() {
	return {
		deleteItem : function(id) {
			$.ajax({
				url : "deleteCartItem",
				method : "POST",
				data : {
					token : $("#token").val(),
					productId : id
				},
				success : function(a) {
					switch(a) {
						case "true":
							$("#product-" + id).remove();
							break;
						case "false":
							showError("Failed to delete product");
							location = "shoppingCart";
							break;
						case "null":
							location = ".";
							break;
						default:
					}
				}
			});
		}
	}
})();