package crm_project.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_project.config.MysqlConfig;
import crm_project.entity.Role;
import crm_project.entity.Users;

public class UserRepository {
	public int insertUser(String fullName, String email, String password, String phone, int idRole) {
		int count = 0;
		String[] splitString = fullName.split("\\s+");
		String firstName = splitString[splitString.length - 1];
		String lastName = splitString[0];
		String userName = "@" + firstName;
		String query = "INSERT INTO Users (fullName,email,pwd,phone,id_role,firstName,lastName,userName) VALUES (?,?,?,?,?,?,?,?)";
		Connection conn = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, fullName);
			statement.setString(2, email);
			statement.setString(3, password);
			statement.setString(4, phone);
			statement.setInt(5, idRole);
			statement.setString(6, firstName);
			statement.setString(7, lastName);
			statement.setString(8, userName);

			count = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi thêm dữ liệu user " + e.getLocalizedMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return count;
	}
	public List<Users> getAllUsers(){
		List<Users> list = new ArrayList<Users>();
		String query = "SELECT u.id, u.firstName ,u.lastName ,u.userName ,r.name, u.fullName \r\n"
				+ "FROM Users u \r\n"
				+ "JOIN `Role` r \r\n"
				+ "ON u.id_role = r.id \r\n"
				+ "ORDER BY u.id ASC\r\n";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Users users = new Users();
				users.setId(resultSet.getInt("id"));
				users.setFirstName(resultSet.getString("firstName"));
				users.setLastName(resultSet.getString("lastName"));
				users.setUserName(resultSet.getString("userName"));
				users.setFullName(resultSet.getString("fullName"));
				Role role = new Role();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				users.setRole(role);
				list.add(users);
			}
		} catch (SQLException e) {
			System.out.println("Loi lay du lieu " + e.getLocalizedMessage());
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public int deleteById(int id) {
		String query = "DELETE FROM Users WHERE id = ?";
		int result = 0;
		
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Loi truy van DB "+ e.getLocalizedMessage());
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Loi dong ket noi "+ e.getLocalizedMessage());
			}
		}
		return result;
	}
	public Users getById(int id) {
		Users users = new Users();
		
		String query = "SELECT u.id, u.fullName ,u.userName ,r.name, r.id \r\n"
				+ "FROM Users u \r\n"
				+ "JOIN `Role` r \r\n"
				+ "ON u.id_role = r.id \r\n"
				+ "WHERE u.id = ?";
		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			users.setId(resultSet.getInt("id"));
			users.setUserName(resultSet.getString("userName"));
			users.setFullName(resultSet.getString("fullName"));
			Role role = new Role();
			role.setId(resultSet.getInt("id"));
			role.setName(resultSet.getString("name"));
			users.setRole(role);
			
		} catch (SQLException e) {
			System.out.println("Loi truy van DB "+ e.getLocalizedMessage());
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Loi dong ket noi "+ e.getLocalizedMessage());
			}
		}
		return users;
	}
}
