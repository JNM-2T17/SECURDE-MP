var viewProduct = (function() {
	var setOnce = false;
	var prodId = 0;
	var username = null;
	
	$(document).ready(function() {
		prodId = $("#productId").val();
		username = $("#username").val();
		$("#addForm").hide();
		$("#addButton").click(viewProduct.addToCart);
		if( $("#review-content").text().length > 0 ) {
			$("#reviewForm").hide();	
		}
		$("#button-update").click(function() {
			$("#reviewForm").show();
			$("#user-review").hide();
		});
		$("input.reviewRating").click(function(){
			if($(this).is(':checked')){
				viewProduct.setRatings(this)
			}
		});
		var isChecked = $("input.reviewRating:checked");
		if(isChecked.length == 1){
			viewProduct.setRatings(isChecked);
		}
		
	});
	
	return {
		addToCart : function() {
			$("#addForm").show();
			$("#addButton").hide();
		},
		setRatings : function(elem){
			var rating = $(elem).attr("value");
			
			$(".reviewRating").each(function(){
				if(parseInt($(this).attr("value")) <= parseInt(rating)){
					$(this).addClass("active");
				}
				else{
					$(this).removeClass("active");
				}
			});
		},
		checkSubmit : function() {
			var quantity = $("#quantity").val();
			if( isNaN(quantity) ) {
				showError("Quantity must be a number.");
				return false;
			} else if( quantity <= 0 ){
				showError("Quantity must be greater than 0.");
				return false;
			} else if( quantity > 2000 ){
				showError("Quantity must be less than or equal to 2000.");
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
			if( !/^[1-5]$/.test(rating) ) {
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
						if( /^[1-5]$/.test(a) ) {
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
							
							if($("#reviews div").length == 0 ) {
								$("#reviews").html("<div class='productReview' id='activeReview'> \
									<div class='productReview-name'>" + escapeHtml(username) + "</div> \
									<div class='productReview-rating'>" + ratingStr + "</div>\
									<div class='productReview-review'>" + escapeHtml(review) + "</div>\
								</div>");
							} else if($("#activeReview").length == 0){
								$("#reviews").prepend("<div class='productReview' id='activeReview'> \
										<div class='productReview-name'>" + escapeHtml(username) + "</div> \
										<div class='productReview-rating'>" + ratingStr + "</div>\
										<div class='productReview-review'>" + escapeHtml(review) + "</div>\
									</div>");
							} else {
								$("#activeReview .productReview-rating").html(ratingStr);
								$("#activeReview .productReview-review").html(escapeHtml(review));
							}
							ratingStr = "";
							for(i = 0; i < 5; i++) {
								if( i < a ) {
									ratingStr += "&#9733;";
								} else {
									ratingStr += "&#9734;";
								}
							}
							$("#productRating").html(ratingStr);
							showMessage("Review submitted.");
						} else {
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