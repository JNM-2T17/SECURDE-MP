var search = (function(){
	var priceRegex = /^([0-9]+([.][0-9]{2})?|[0-9]*[.][0-9]{2})$/;
	
	$(document).ready(function() {
		$("#searchType").val($("#sType").val());
		$("#sType-" + $("#sType").val()).addClass("active");
		$("#searchForm").submit(ret.checkSubmit);
		
		$("#filterButton").click(function(){
			console.log("filter");
			$("#searchForm").submit();
		});
	});
	
	var ret = {
		checkSubmit : function() {
			console.log("ERE");
			var startRange = $("#minRange").val();
			var endRange = $("#maxRange").val();
			var message = "";
			
			if( startRange.length > 0 && !priceRegex.test(startRange) ) {
				message = appendMessage(message,"Start Range is invalid");
			}
			
			if( endRange.length > 0 && !priceRegex.test(endRange) ) {
				message = appendMessage(message,"End Range is invalid");
			}
			
			if( message.length == 0 && startRange.length > 0 && endRange.length > 0 && 
					startRange * 1.0 > endRange * 1.0 ) {
				message = appendMessage(message,"Start Range must be less than End Range.");
			}
			console.log(message + " is the message");
			if( message.length > 0 ) {
				showError(message);
				return false;
			} else {
				if( startRange.length > 0 ) {
					$("#searchForm").append("<input type='hidden' name='minRange' value='" + startRange + "'/>");
				}
				if( endRange.length > 0 ) {
					$("#searchForm").append("<input type='hidden' name='maxRange' value='" + endRange + "'/>");
				}
				
				$(".ratingSearch:checked").each(function(){
					$("#searchForm").append("<input type='hidden' name='ratings[]' value='" + $(this).data('rating') + "'/>");
				});
				return true;
			}
		}
	};
	return ret;
})();