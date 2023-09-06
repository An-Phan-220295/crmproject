package crm_project.service;

import java.util.List;

import crm_project.entity.Role;
import crm_project.entity.Users;
import crm_project.repository.RoleRepository;
import crm_project.repository.UserRepository;

public class UserService {
	RoleRepository roleRepository = new RoleRepository();
	UserRepository userRepository = new UserRepository();
	
	public List<Role> getAllRole(){
		return roleRepository.findAll();
	}
	
	public boolean insertUser(String fullName, String email, String password, String phone, int idRole) {
		int count = userRepository.insertUser(fullName, email, password, phone, idRole);
		return count > 0;
	}
	public List<Users> getAllUsers(){
		return userRepository.getAllUsers();
	}
	
	public boolean deleteById(int id) {
		int count = userRepository.deleteById(id);
		return count > 0;
	}
	public Users getById(int id) {
		return userRepository.getById(id);
	}
}
