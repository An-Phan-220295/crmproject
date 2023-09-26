const User = {
	// 			Start HANDLEDELETE --------------------------------------------
	handleDelete: function() {

		let id = this.getAttribute("id");

		$.ajax({
			method: "GET",
			url: http://localhost:8082/CRM_02/api/user/delete?id=${id},
		})
			.done(function(data) {
				let dataJSON = JSON.parse(data)
				if (dataJSON.data) {
					$(#${id}).closest("tr").remove();
				} else {
					alert("Đã xảy ra lỗi trong quá trình xoá")
				}

			});

	},
	// END Handle DELETE --------------------------------------------------------------------------------------
	// Start Updates ------------------------------------------

	handleUpdate: function() {
		console.log("Start Update")
		var row = $(this).closest("tr");
		var idRow = row.attr("id");
		// Hide table row
		row.addClass("hide");

		// Show form update
		$(.${idRow}).removeClass("hide")
		$(.${idRow}).addClass("d-table-row")

		new Promise(function(resolve, reject) {
			$(".submitUpdate").on("click", function() {
				let _this = $(this)
				resolve(_this)
			})
			$(".cancelUpdate").on("click", function() {
				reject();
			})
		})
			.then((_this) => {
				console.log(_this, "gach this")
				let newRow = $(_this).closest("tr");

				let id = $(_this).closest("tr").attr('class').split(' ')[0]
				let firstName = newRow.find('td:eq(1) input[name="firstName"]').val();
				let lastName = newRow.find('td:eq(2) input[name="lastName"]').val();
				let userName = newRow.find('td:eq(3) input[name="userName"]').val();
				let id_role = newRow.find(":selected").val();
				let role_name = newRow.find(":selected").text().trim();

				console.log(firstName, lastName, userName, id_role, id, " before run query")
				$.ajax({
					url: 'http://localhost:8082/CRM_02/api/update-user',
					type: 'POST',
					data: {
						firstName: firstName,
						lastName: lastName,
						userName: userName,
						id_role: id_role,
						id: id
					},
					success: function(data) {
						console.log("End then")
						row.find('td:eq(1)').text(firstName)
						row.find('td:eq(2)').text(lastName)
						row.find('td:eq(3)').text(userName)
						row.find('td:eq(4)').text(role_name)
						row.removeClass("hide");
						$(.${idRow}).addClass("hide")
						$(.${idRow}).removeClass("d-table-row")

					}
				})
				
			})
			.catch(function() {
				row.removeClass("hide");
				$(.${idRow}).addClass("hide")
				$(.${idRow}).removeClass("d-table-row")
			})
		
		console.log("end handleUpdate")
	},



	// 		END UPDATE------------------------------------------------	
	// START RENDER ----------------------------------------------------------------

	GetRoleList: function() {
		var RoleList = [];
		let APIRole = "http://localhost:8082/CRM_02/api/role";

		return $.ajax({
			method: "GET",
			url: APIRole,
		})
			.done(function(data) {
				data.data.forEach(item => {
					RoleList.push(item);
				})
			});


	},

	handleRender: function(data) {

		let HTMLS = data.userList.map((item, index) => {

			return `
					<tr id = "${item.id}" class= "d-table-row" >
						<td>${index + 1}</td>
						<td>${item.firstName}</td>
						<td>${item.lastName}</td>
						<td>${item.userName}</td>
						<td value="${item.role.id}">${item.role.name}</td>

						<td class="handle">
							<button type="button" class="btn btn-sm btn-primary update">Sửa</button>
							<button id="${item.id}" type="button" class="btn btn-sm btn-danger deleteUser">Xóa</button>
							<a href="user-details.html" class="btn btn-sm btn-info">Xem</a>
						</td>
					</tr >
					<tr class="${item.id} hide">
						<td>${index + 1}</td>
						<td><input name="firstName" value =${item.firstName}> </td>
						<td><input name="lastName" value =${item.lastName}></td>
						<td><input name="userName" value =${item.userName}></td>
						<td><select class="selectRole" value="${item.role.id}" selected="selected">
							
							${data.roleList[0]}
						</select></td>
						<td> 
							<button type="button" class="btn btn-sm btn-primary submitUpdate">Sửa </button> 
							<button type="button" class="btn btn-sm btn-danger cancelUpdate">Huỷ </button> 
						</td>					
					</tr >
				
	`
		}).join("");
		$("tbody").html(HTMLS);
		setTimeout(function() {
			return new Promise(function(resolve) {
				resolve();
			})
		}, 500)

	},

	ApiGetData: function() {
		let ArrayList = [];
		var RoleList = [];

		var API = "http://localhost:8082/CRM_02/api/user";

		User.GetRoleList()
			.then(function(list) {

				list = list.data.map(function(item) {
					return `
	<option value = "${item.id}" > ${item.name}</option >
		`
				}).join("");
				RoleList.push(list);
			})

		$(document).ready(function() {
			$.ajax({
				method: "GET",
				url: API,
			})
				.done(function(data) {
					data.data.forEach(item => {
						ArrayList.push(item);
					})

				});
		})
		let data = {
			userList: ArrayList,
			roleList: RoleList
		}
		return new Promise(function(resolve, reject) {

			setTimeout(() => {
				resolve(data);
			}, 500)


		})
	},

	// END RENDER -----------------------------------------------------------------------------------

	start: function() {
		console.log("Start.......")

		this.ApiGetData()
			.then((listUsers) => {
				console.log("Run.......")
				this.handleRender(listUsers)
			})
			.then(() => {
				$(".deleteUser").on("click", this.handleDelete);
				$(".update").on("click", this.handleUpdate);
			})


	}
}

User.start();