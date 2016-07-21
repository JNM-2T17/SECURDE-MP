function submit(id) {
	$.ajax({
		url:"deleteProduct",
		method : "POST",
		data : {
			id : id
		},
		success : function(a) {
			if( a ) {
				$("#product-" + id).remove();
			} else {
				showError("An unexpected error occured.");
			}
		}
	});
}