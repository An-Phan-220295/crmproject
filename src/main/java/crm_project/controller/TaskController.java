package crm_project.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project.config.MysqlConfig;
import crm_project.entity.Project;
import crm_project.entity.Users;
import crm_project.service.ProjectService;
import crm_project.service.TaskService;
import crm_project.service.UserService;


@WebServlet(name = "TaskAddController",urlPatterns = {"/task-add","/task"})
public class TaskController extends HttpServlet{
	ProjectService projectService = new ProjectService();
	UserService userService = new UserService();
	TaskService taskService = new TaskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getServletPath().equals("/task-add")) {
			req.setAttribute("projectList1", projectService.getAllProject());
			req.setAttribute("userList1", userService.getAllUsers());
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
		}
		else{
			req.setAttribute("taskList", taskService.getAllTask());
			req.getRequestDispatcher("task.jsp").forward(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		int idProject =  Integer.valueOf(req.getParameter("project"));
		String taskName = req.getParameter("task");
		int idUser =  Integer.valueOf(req.getParameter("user"));
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		
		req.setAttribute("result", taskService.insertTask(taskName, taskName, startDate, endDate, idUser, idProject));
		req.setAttribute("projectList1", projectService.getAllProject());
		req.setAttribute("userList1", userService.getAllUsers());
		req.getRequestDispatcher("task-add.jsp").forward(req, resp);
	}
}
