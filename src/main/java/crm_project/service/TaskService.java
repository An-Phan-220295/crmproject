package crm_project.service;

import java.util.List;

import crm_project.entity.Task;
import crm_project.repository.TaskRepository;

public class TaskService {
	TaskRepository taskRepository = new TaskRepository();

	public int insertTask(String name, String content, String startDate, String endDate, int idUser, int idProject ) {
		int result[] = taskRepository.insertTask(name, content, startDate, endDate, idUser, idProject);
		int queryResult = result[0];
		int dateCheckingResult = result[1];
		
		if (queryResult != 0 && dateCheckingResult == 0)
			// Return 0 for the succession
			return 0;

		if (queryResult == 0 && dateCheckingResult == 0)
			// Return 1 represent the fail due to Query.
			return 1;
	
		// Return -1 represent the fail due to wrong format date.
		return -1;
	}
	public List<Task> getAllTask(){
		return taskRepository.getAllTask();
	}
	public boolean deleteById(int idTask) {
		return taskRepository.deleteById(idTask) > 0;
	}
	public Task getById(int id) {
		return taskRepository.getById(id);
	}
	public int currentIdStatus(int id) {
		return taskRepository.currentIdStatus(id);
	}
	public int updateStatusById(int id, int idUser, int idStatus) {
		return taskRepository.updateStatusById(id, idUser, idStatus);
	}
}
