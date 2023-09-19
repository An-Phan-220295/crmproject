package crm_project.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm_project.config.MysqlConfig;
import crm_project.entity.Status;

public class StatusRepository {
	public List<Status> findAll() {
		List<Status> listStatus = new ArrayList<>();
		String query = "SELECT * FROM Status";

		Connection connection = MysqlConfig.getConnect();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Status status = new Status();
				status.setId(resultSet.getInt("id"));
				status.setStatus(resultSet.getString("name"));
				listStatus.add(status);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listStatus;
	}

}
