package crm_project.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_project.config.MysqlConfig;
import crm_project.entity.Project;
import crm_project.entity.Status;
import crm_project.entity.Task;
import crm_project.entity.Users;

public class TaskRepository {

	public int[] insertTask(String name, String content, String startDate, String endDate, int idUser, int idProject) {
		String query1 = "INSERT INTO Task (name,startDate,endDate,id_project) \r\n"
				+ "VALUES (?,STR_TO_DATE(?,\"%d/%m/%Y\"),STR_TO_DATE(?,\"%d/%m/%Y\"),?);";
		String query2 = "INSERT INTO Task_Status_Users (id_user,id_status ,id_task , createDate) \r\n"
				+ "VALUES (?,1,(SELECT MAX(id) FROM Task),CURDATE());";
		String query3 = "INSERT INTO Project_Users (id_user,id_project,id_task) VALUES (?,?,(SELECT MAX(id) FROM Task))";

		int[] result = new int[2];
		// The first element represent for result of the executeQuery
		result[0] = 0;
		// The second element represent for result of date format checking
		result[1] = 0;

		Connection connection = MysqlConfig.getConnect();

		try {
			PreparedStatement statement1 = connection.prepareStatement(query1);
			statement1.setString(1, name);
			statement1.setString(2, startDate);
			statement1.setString(3, endDate);
			statement1.setInt(4, idProject);
			int temp1 = statement1.executeUpdate();

			PreparedStatement statement2 = connection.prepareStatement(query2);
			statement2.setInt(1, idUser);
			int temp2 = statement2.executeUpdate();

			PreparedStatement statement3 = connection.prepareStatement(query3);
			statement3.setInt(1, idUser);
			statement3.setInt(2, idProject);
			int temp3 = statement3.executeUpdate();

			result[0] = temp1 * temp2 * temp3;
			result[0] = temp1 * temp2;

		} catch (SQLException e) {
			System.out.println("Lỗi thêm dữ liệu " + e.getLocalizedMessage());
			result[1] = 1;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return result;
	}

	public List<Task> getAllTask() {
		List<Task> list = new ArrayList<>();
		String query = "SELECT t.id as taskId,t.name as taskName,p.name as projectName,u.fullName as fullName,s.name as status,\r\n"
				+ "DATE_FORMAT(t.startDate ,'%d/%m/%Y') AS startDate ,DATE_FORMAT(t.endDate ,'%d/%m/%Y') AS endDate \r\n"
				+ "FROM Task_Status_Users tsu \r\n" + "JOIN Task t ON tsu.id_task = t.id \r\n"
				+ "JOIN Users u ON tsu.id_user = u.id \r\n" + "JOIN Status s ON tsu.id_status = s.id \r\n"
				+ "JOIN Project p ON t.id_project = p.id";
		Connection connection = MysqlConfig.getConnect();

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getInt("taskId"));
				task.setName(resultSet.getString("taskName"));
				task.setStartDate(resultSet.getString("startDate"));
				task.setEndDate(resultSet.getString("endDate"));

				Users users = new Users();
				users.setFullName(resultSet.getString("fullName"));
				task.setUsers(users);

				Project project = new Project();
				project.setProjectName(resultSet.getString("projectName"));
				task.setProject(project);

				Status status = new Status();
				status.setStatus(resultSet.getString("status"));
				task.setStatus(status);

				list.add(task);
			}

		} catch (SQLException e) {
			System.out.println("Lỗi truy xuất dữ liệu " + e.getLocalizedMessage());
		} finally {
			try {
				// Đóng kết nối.
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return list;
	}

	public int deleteById(int idTask) {
		String deleteUserTaskQuery = "DELETE FROM Task_Status_Users tsu WHERE tsu.id_task = ?";
		String deleteTaskQuery = "DELETE FROM Task t WHERE t.id = ?";
		int result = 0;

		Connection connection = MysqlConfig.getConnect();

		try {
			PreparedStatement statementDeleteUserTaskQuery = connection.prepareStatement(deleteUserTaskQuery);
			statementDeleteUserTaskQuery.setInt(1, idTask);

			PreparedStatement statementDeleteTaskQuery = connection.prepareStatement(deleteTaskQuery);
			statementDeleteTaskQuery.setInt(1, idTask);

			int temp1 = statementDeleteUserTaskQuery.executeUpdate();
			int temp2 = statementDeleteTaskQuery.executeUpdate();
			result = temp1 * temp2;
		} catch (Exception e) {
			System.out.println("Loi truy van DB " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Loi dong ket noi " + e.getLocalizedMessage());
			}
		}
		return result;
	}

	public Task getById(int id) {
		String query = "SELECT t.id as taskId,t.name as taskName,p.name as projectName,u.fullName as fullName,s.name as status,\r\n"
				+ "DATE_FORMAT(t.startDate ,'%d/%m/%Y') AS startDate ,DATE_FORMAT(t.endDate ,'%d/%m/%Y') AS endDate \r\n"
				+ "FROM Task_Status_Users tsu \r\n" + "JOIN Task t ON tsu.id_task = t.id \r\n"
				+ "JOIN Users u ON tsu.id_user = u.id \r\n" + "JOIN Status s ON tsu.id_status = s.id \r\n"
				+ "JOIN Project p ON t.id_project = p.id\r\n" + "WHERE t.id = ?";
		Task task = new Task();

		Connection connection = MysqlConfig.getConnect();

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				task.setId(resultSet.getInt("taskId"));
				task.setName(resultSet.getString("taskName"));
				task.setStartDate(resultSet.getString("startDate"));
				task.setEndDate(resultSet.getString("endDate"));

				Users users = new Users();
				users.setFullName(resultSet.getString("fullName"));
				task.setUsers(users);

				Project project = new Project();
				project.setProjectName(resultSet.getString("projectName"));
				task.setProject(project);

				Status status = new Status();
				status.setStatus(resultSet.getString("status"));
				task.setStatus(status);
			}
		} catch (SQLException e) {
			System.out.println("Loi truy van DB " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Loi dong ket noi " + e.getLocalizedMessage());
			}
		}
		return task;
	}

	public int currentIdStatus(int id) {
		int currentIdStatus = 0;
		String query = "SELECT tsu.id_status AS currentStatus FROM Task t \r\n"
				+ "JOIN Task_Status_Users tsu ON t.id = tsu.id_task \r\n"
				+ "WHERE t.id  = ?";

		Connection connection = MysqlConfig.getConnect();

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				currentIdStatus = resultSet.getInt("currentStatus");
			}
		} catch (SQLException e) {
			System.out.println("Loi truy van DB " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Loi dong ket noi " + e.getLocalizedMessage());
			}
		}
		return currentIdStatus;
	}

	public int[] updateById(int id, String name, String content, String startDate, String endDate, int idUser,
			int idProject, int idStatus) {
		String query1 = "UPDATE Task t SET t.name = ?,\r\n" + "t.startDate = (STR_TO_DATE(?,\"%d/%m/%Y\")), \r\n"
				+ "t.endDate = (STR_TO_DATE(?,\"%d/%m/%Y\")),\r\n" + "t.id_project = ?\r\n" + "WHERE id = 40";
		
		String query2 = ""; 
		int[] result = new int[2];
		// The first element represent for result of the executeQuery
		result[0] = 0;
		// The second element represent for result of date format checking
		result[1] = 0;

		Connection connection = MysqlConfig.getConnect();

		try {
			PreparedStatement statement1 = connection.prepareStatement(query1);
			statement1.setString(1, name);
			statement1.setString(2, startDate);
			statement1.setString(3, endDate);
			statement1.setInt(4, idProject);
			result[0] = statement1.executeUpdate();
//			int temp1 = statement1.executeUpdate();

//			PreparedStatement statement2 = connection.prepareStatement(query2);
//			statement2.setInt(1, idUser);
//			int temp2 = statement2.executeUpdate();

//			result[0] = temp1 * temp2;

		} catch (SQLException e) {
			System.out.println("Lỗi thêm dữ liệu " + e.getLocalizedMessage());
			result[1] = 1;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return result;
	}
	public int updateStatusById(int id, int idUser, int idStatus) {
		String query = "INSERT INTO Task_Status_Users (id_user,id_status ,id_task , createDate) \r\n"
				+ "VALUES (?,?,?,CURDATE())";

		int result = 0;

		Connection connection = MysqlConfig.getConnect();

		try {
			PreparedStatement statement1 = connection.prepareStatement(query);
			statement1.setInt(1, idUser);
			statement1.setInt(2, idStatus);
			statement1.setInt(3, id);
			result = statement1.executeUpdate();
			System.out.println(result);

		} catch (SQLException e) {
			System.out.println("Lỗi thêm dữ liệu " + e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
			}
		}
		return result;
	}
}
