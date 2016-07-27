var viewProduct = (function() {
	var setOnce = false;
	var prodId = 0;
	return {
		addToCart : function() {
			$("#addForm").show();
			$("#addButton").hide();
		},
		setProdId : function(a) {
			if( !setOnce ) {
				setOnce = true;
				prodId = a;
			}
		},
		checkSubmit : function() {
			var quantity = $("#quantity").val();
			if( isNaN(quantity) ) {
				showError("Quantity must be a number.");
				return false;
			} else {
				$("#productId").val(prodId);
				return true;
			}
		}
	};
})();

$(document).ready(function() {
	viewProduct.setProdId($("#productId").val());
	$("#addForm").hide();
	$("#addButton").click(viewProduct.addToCart);
});