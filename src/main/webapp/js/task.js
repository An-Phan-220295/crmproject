$(document).ready(function() {
	$(".bt-delete").click(function() {
		var id = $(this).attr('id-task')
		var This = $(this)

		$.ajax({
			method: "GET",
			url: `http://localhost:8080/crm_project/api/task/delete?id=${id}`,
		})
			.done(function(result) {
				if (result.data == true) {
					This.closest('tr').remove()
				}
				console.log(result.data)
			});
	})
})