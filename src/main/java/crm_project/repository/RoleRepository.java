package crm_project.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_project.config.MysqlConfig;
import crm_project.entity.Role;

/*
 * RoleRepository: chứa toàn bộ câu truy vấn liên quan tới bảng role
 */
public class RoleRepository {
	public int insert(String name, String description) {
		String query = "INSERT INTO `Role`  (name,description) VALUES (?,?)";
		Connection connection = MysqlConfig.getConnect();
		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1,name);
			statement.setString(2, description);
			
			count = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi kết nối CSDL " + e.getLocalizedMessage());
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}
	
	//Đối với câu Select tên hàm bắt đầu bằng find
	//Nếu có điều kiện where :by (findByName)
	public List<Role> findAll(){
		List<Role> listRole = new ArrayList<Role>();
		String query = "SELECT * FROM `Role`";

		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Role role = new Role();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
				listRole.add(role);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listRole;
	}
	public int deleteById(int id) {
		String query = "DELETE FROM `Role` r WHERE r.id = ?";
		int result = 0;
		
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Loi truy van du lieu "+ e.getLocalizedMessage());
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Loi dong ket noi "+ e.getLocalizedMessage());
			}
		}
		return result;
	}
}
