package crm_project.service;

import java.util.List;

import crm_project.entity.Project;
import crm_project.repository.ProjectRepository;

public class ProjectService {
	ProjectRepository projectRepository = new ProjectRepository();

	public int projectAdd(String name, String startDate, String endDate) {
		int[] result=projectRepository.projectAdd(name, startDate, endDate);
		int queryResult = result[0];
		int dateCheckingResult = result[1];
		if (queryResult != 0 && dateCheckingResult == 0)
			// Return 0 represent the succession
			return 0;

		if (queryResult == 0 && dateCheckingResult == 0)
			// Return 1 represent the fail due to Query.
			return 1;

		// Return -1 represent the fail due to wrong format date.
		return -1;
	}
	public List<Project> getAllProject() {
		return projectRepository.getAllProject();
	}
	public boolean deleteById(int id) {
		return projectRepository.deleteById(id)>0;
	}
}
