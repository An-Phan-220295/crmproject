package crm_project.service;

import java.util.List;

import crm_project.entity.Status;
import crm_project.repository.StatusRepository;

public class StatusService {
	StatusRepository statusRepository = new StatusRepository();
	public List<Status> findAll() {
		return statusRepository.findAll();
	}
}
