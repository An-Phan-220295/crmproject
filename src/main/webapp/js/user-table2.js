//khi nào file html được load thì thực hiện gì đó
$(document).ready(function() {
	//Đăng ký sự kiện click: $("tên_thẻ||tên_class||id").click()
	//class => .
	//id => #
	$(".btn-xoa").click(function() {
		//Lấy giá trị thuộc tính (attr) bên thẻ cso class là btn-xoa
		//$(this): đại diện cho function đang thực thi
		var id = $(this).attr('id-user')
		var This = $(this)
		
		//RestTemplate, HttpClient
		
		$.ajax({
			method: "GET",
			url: `http://localhost:8080/crm_project/api/user/delete?id=${id}`,//String literals
			//data: { name: "John", location: "Boston" }// Tham số Data chỉ giành cho phương thức post
		})
			.done(function(result) {
				//result đại diện cho kết quả từ link url trả về
				//Lấy giá trị kiểu object trong js thì tenbien.key
				if(result.data == true){
					//.parent => đi ra một thẻ cha
					//.remove => xóa thẻ hiện tại
					//.closest=> đi ra thằng cha được chỉ định.
					//This.parent().parent().remove()
					This.closest('tr').remove()
				}
				console.log(result.data)
			});
	})
})