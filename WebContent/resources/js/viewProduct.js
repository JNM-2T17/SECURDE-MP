var viewProduct = (function() {
	var setOnce = false;
	var prodId = 0;
	return {
		addToCart : function() {
			console.log("addToCart");
			$("#addForm").show();
			$("#addButton").hide();
		},
		setProdId : function(a) {
			if( !setOnce ) {
				setOnce = true;
				prodId = a;
			}
		},
		setRatings : function(elem){
			console.log("hi");
			var rating = $(elem).attr("value");
			
			$(".reviewRating").each(function(){
				if(parseInt($(this).attr("value")) <= parseInt(rating)){
					console.log("active: " + $(elem).attr("value") + " <= " + rating);
					$(this).addClass("active");
				}
				else{
					console.log("!active: " + $(elem).attr("value") + " > " + rating);
					$(this).removeClass("active");
				}
			});
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
		},
		checkReview : function() {
			var token = $("#review-token").val();
			var review = $("#review").val();
			var rating = $("input[name='rating']:checked").attr('id').substring(7);
			var message = "";
			
			if( review.length == 0 ) {
				message = appendMessage(message,"Review cannot be empty");
			}
			if( !/^(1|2|3|4|5)$/.test(rating) ) {
				message = appendMessage(message,"Please select a rating");
			}
			
			if( message.length == 0 ) {
				$.ajax({
					url : "review",
					method : "POST",
					data : {
						token : token,
						prodId : prodId,
						review : review,
						rating : rating
					}, 
					success : function(a) {
						console.log("viewProduct: " + a);
						if( a == "true" ) {
							console.log("viewProduct: if");
							$("#reviewForm").hide();
							var ratingStr = "";
							for(i = 0; i < 5; i++) {
								if( i < rating ) {
									ratingStr += "&#9733;";
								} else {
									ratingStr += "&#9734;";
								}
							}
							$("#review-content").text(review);
							$("#userReview-rating").html(ratingStr);
							$("#user-review").show();
							showMessage("Review submitted.");
						} else {
							console.log("viewProduct: else");
							showError(a);
						}
					}
				})
			} else {
				showError(message);
			}
			return false;
		}
	};
})();

$(document).ready(function() {
	viewProduct.setProdId($("#productId").val());
	$("#addForm").hide();
	$("#addButton").click(viewProduct.addToCart);
	console.log($("#review-content").length);
	if( $("#review-content").text().length > 0 ) {
		$("#reviewForm").hide();	
	}
	$("#button-update").click(function() {
		$("#reviewForm").show();
		$("#user-review").hide();
	});
	$("input.reviewRating").click(function(){
		console.log("hey");
		if($(this).is(':checked')){
			viewProduct.setRatings(this)
		}
	});
	var isChecked = $("input.reviewRating:checked");
	if(isChecked.length == 1){
		viewProduct.setRatings(isChecked);
	}
	
});