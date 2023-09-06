package crm_project.service;

import java.util.List;

import crm_project.entity.Task;
import crm_project.repository.TaskRepository;

public class TaskService {
	TaskRepository taskRepository = new TaskRepository();

	public int insertTask(String name, String content, String startDate, String endDate, int idUser, int idProject ) {
		int result[] = taskRepository.insertTask(name, content, startDate, endDate, idUser, idProject);
		int queryResult1 = result[0];
		int queryResult2 = result[2]; 
		int dateCheckingResult = result[1];
		
		if (queryResult1 != 0 && dateCheckingResult == 0 && queryResult2 != 0)
			// Return 0 represent the succession
			return 0;

		if (queryResult1 == 0 && dateCheckingResult == 0)
			// Return 1 represent the fail due to Query1.
			return 1;
		
		if (queryResult2 == 0 && dateCheckingResult == 0)
			// Return 2 represent the fail due to Query2.
			return 2;

		// Return -1 represent the fail due to wrong format date.
		return -1;
	}
	public List<Task> getAllTask(){
		return taskRepository.getAllTask();
	}
	public boolean deleteById(int idTask) {
		return taskRepository.deleteById(idTask) > 0;
	}
}
