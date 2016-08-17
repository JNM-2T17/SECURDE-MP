$(document).ready(function(){
	$(".filterCat").click(function(){
		var listParent = $(this).closest("li");

		if(listParent.hasClass("active")){
			listParent.removeClass("active")
		}

		else{
			listParent.addClass("active")
		}
	});
});