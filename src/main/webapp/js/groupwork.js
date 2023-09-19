$(document).on('click', '.project-delete', function load() {
	var id = $(this).attr('id-project')
	var This = $(this)
	$.ajax({
		method: "GET",
		url: `http://localhost:8080/crm_project/api/groupwork/delete?id=${id}`,
	})
		.done(function(result) {
			if (result.data == true) {
				//This.parent().parent().remove()
				This.closest('tr').remove()
			}
			console.log(result);
		});
})
