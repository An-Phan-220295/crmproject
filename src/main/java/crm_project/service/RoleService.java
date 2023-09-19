package crm_project.service;

import java.util.List;

import crm_project.entity.Role;
import crm_project.repository.RoleRepository;

public class RoleService {
	private RoleRepository roleRepository = new RoleRepository();
	
	public boolean addRole(String name, String desc) {
		int count = roleRepository.insert(name, desc);
		return count>0;
	}
	public List<Role> getAllRole(){
		return roleRepository.findAll();
	}
	public boolean deleteById(int id) {
		int result = roleRepository.deleteById(id);
		return result > 0;
	}
	public boolean updateById(int id, String name, String description) {
		return roleRepository.updateById(id, name, description) > 0;
	}
	public Role getById(int id) {
		return roleRepository.getById(id);
	}
}
