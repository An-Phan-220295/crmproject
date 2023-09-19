$(document).on('click', '.deleterole', function() {
	var id = $(this).attr('id-role')
	var This = $(this)
	$.ajax({
		method: "GET",
		url: `http://localhost:8080/crm_project/api/role/delete?id=${id}`,
	})
		.done(function(result) {
			if (result.data == true) {
				//This.parent().parent().remove()
				This.closest('tr').remove()
			}
			console.log(result);
		});
})
