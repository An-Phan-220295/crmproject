package crm_project.entity;

import java.util.List;

public class TaskUser {
	private Users user;
	private List<Task> incompleteTask;
	private List<Task> inproccessTask;
	private List<Task> completedTask;
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public List<Task> getIncompleteTask() {
		return incompleteTask;
	}
	public void setIncompleteTask(List<Task> incompleteTask) {
		this.incompleteTask = incompleteTask;
	}
	public List<Task> getInproccessTask() {
		return inproccessTask;
	}
	public void setInproccessTask(List<Task> inproccessTask) {
		this.inproccessTask = inproccessTask;
	}
	public List<Task> getCompletedTask() {
		return completedTask;
	}
	public void setCompletedTask(List<Task> completedTask) {
		this.completedTask = completedTask;
	}
	
}
