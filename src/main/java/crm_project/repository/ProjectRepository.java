package crm_project.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import crm_project.config.MysqlConfig;
import crm_project.entity.Project;

public class ProjectRepository {
	public int[] projectAdd(String name, String startDate, String endDate) {
		int[] result = new int[2];

		// The first element represent for result of the executeQuery
		result[0] = 0;
		// The second element represent for result of date format checking
		result[1] = 0;

		String query = "INSERT INTO Project (name,startDate,endDate)\r\n"
				+ "VALUES (?,STR_TO_DATE(?,\"%d/%m/%Y\"),STR_TO_DATE(?,\"%d/%m/%Y\"))";

		Connection connection = MysqlConfig.getConnect();

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, startDate);
			statement.setString(3, endDate);

			result[0] = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Lỗi thực thi câu query " + e.getLocalizedMessage());
			result[1] = 1;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Lỗi connection close " + e.getLocalizedMessage());
			}
		}
		return result;
	}

	public List<Project> getAllProject() {
		List<Project> list = new ArrayList<Project>();

		// Chuẩn bị câu query.
		String query = "SELECT * FROM Project p";

		// Mở connection lấy dữ liệu.
		Connection connection = MysqlConfig.getConnect();

		try {
			// Truyền câu query tới CSDL.
			PreparedStatement statement = connection.prepareStatement(query);

			// Lấy dữ liệu.
			ResultSet resultSet = statement.executeQuery();

			// Duyệt qua các kết quả và chuyển về List.
			while (resultSet.next()) {
				Project project = new Project();
				project.setId(resultSet.getInt("id"));
				project.setProjectName(resultSet.getString("name"));

				// Chuyển đổi yyyy-mm-dd từ database sang dd-mm-yyy để hiển thị
				DateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat dmy = new SimpleDateFormat("dd-MM-yyyy");
				Date startDate = new Date();
				Date endDate = new Date();
				try {
					startDate = ymd.parse(resultSet.getString("startDate"));
					endDate = ymd.parse(resultSet.getString("endDate"));

				} catch (ParseException e) {
					System.out.println("Lỗi chuyển đổi ngày " + e.getLocalizedMessage());
				}
				project.setStartDate(dmy.format(startDate));
				project.setEndDate(dmy.format(endDate));

				list.add(project);
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

	public int deleteById(int id) {
		int result = 0;
		String query = "DELETE FROM Project p WHERE p.id = ?";

		Connection connection = MysqlConfig.getConnect();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();

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
		return result;
	}
}
