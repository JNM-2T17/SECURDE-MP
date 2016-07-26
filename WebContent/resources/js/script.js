function showError(message) {
	alert(message);
}

$(document).ready(function() {
	console.log("Hello world!");
	var error = $("#error").val() ? $("#error").val() : "";
	if( error.length > 0 ) {
		showError(error);
	}
});