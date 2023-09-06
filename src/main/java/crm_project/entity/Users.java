package crm_project.entity;

//Entity: là nơi khai báo ra các class đặt tên và thược tính giống với lại tên bảng trong CSDL.
//Nếu cột là khóa ngoại thì khônng khai báo biến mà sẽ chuyển thành đối tượng của bảng được tham chiếu tới
public class Users {
	private int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String userName;
	private String fullName;
	private Role role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
