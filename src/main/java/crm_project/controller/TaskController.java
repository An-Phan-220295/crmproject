package crm_project.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project.service.ProjectService;
import crm_project.service.StatusService;
import crm_project.service.TaskService;
import crm_project.service.UserService;

@WebServlet(name = "TaskController", urlPatterns = { "/task-add", "/task", "/task-edit" })
public class TaskController extends HttpServlet {
	ProjectService projectService = new ProjectService();
	UserService userService = new UserService();
	TaskService taskService = new TaskService();
	StatusService statusService = new StatusService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		switch (servletPath) {
		case "/task-add":
			req.setAttribute("projectList1", projectService.getAllProject());
			req.setAttribute("userList1", userService.getAllUsers());
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
			break;

		case "/task":
			req.setAttribute("taskList", taskService.getAllTask());
			req.getRequestDispatcher("task.jsp").forward(req, resp);
			break;

		case "/task-edit":
			int id = Integer.parseInt(req.getParameter("id"));
			req.setAttribute("task", taskService.getById(id));
			req.setAttribute("projectList1", projectService.getAllProject());
			req.setAttribute("userList1", userService.getAllUsers());
			req.setAttribute("statusList", statusService.findAll());
			req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
			break;

		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		switch (servletPath) {
		case "/task-add":
			int idProject = Integer.valueOf(req.getParameter("project"));
			String taskName = req.getParameter("task");
			int idUser = Integer.valueOf(req.getParameter("user"));
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");

			req.setAttribute("result",
					taskService.insertTask(taskName, taskName, startDate, endDate, idUser, idProject));
			req.setAttribute("projectList1", projectService.getAllProject());
			req.setAttribute("userList1", userService.getAllUsers());
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
			break;

		case "/task-edit":
			int idProjectEdited = Integer.valueOf(req.getParameter("project"));
			String taskNameEdited = req.getParameter("task");
			int idUserEdited = Integer.valueOf(req.getParameter("user"));
			String startDateEdited = req.getParameter("startDate");
			String endDateEdited = req.getParameter("endDate");
			int idStatusEdited = Integer.valueOf(req.getParameter("status"));
			int id = Integer.parseInt(req.getParameter("id"));
			int currentProjectId = Integer.parseInt(req.getParameter("currentProjectId"));

			int currentUserId = 0;
			currentUserId = Integer.parseInt(req.getParameter("currentUserId"));
			int currentStatus = 0;
			currentStatus = taskService.currentIdStatus(id);
			if (currentUserId == 0 && currentStatus == 0) {
				req.setAttribute("result",taskService.updateUserStatusById(id, idStatusEdited, taskNameEdited, startDateEdited,
								endDateEdited, idUserEdited, idProjectEdited, currentStatus, currentUserId,
								currentProjectId));
				req.setAttribute("task", taskService.getById(id));
				req.setAttribute("projectList1", projectService.getAllProject());
				req.setAttribute("userList1", userService.getAllUsers());
				req.setAttribute("statusList", statusService.findAll());
				req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
			} else {
				req.setAttribute("result", taskService.updateById(id, idStatusEdited, taskNameEdited, startDateEdited,
						endDateEdited, idUserEdited, idProjectEdited, currentStatus, currentUserId, currentProjectId));
				
				req.setAttribute("task", taskService.getById(id));
				req.setAttribute("projectList1", projectService.getAllProject());
				req.setAttribute("userList1", userService.getAllUsers());
				req.setAttribute("statusList", statusService.findAll());req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
			}
			break;

		default:
			break;
		}
	}
}
