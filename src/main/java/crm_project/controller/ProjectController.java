package crm_project.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project.service.ProjectService;

@WebServlet(name = "GruopWorkController", urlPatterns = { "/groupwork-add", "/groupwork", "/groupwork-edit" })
public class ProjectController extends HttpServlet {
	ProjectService projectService = new ProjectService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();

		switch (servletPath) {
		case "/groupwork-add":
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
			break;

		case "/groupwork":
			req.setAttribute("listWork", projectService.getAllProject());
			req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
			break;

		case "/groupwork-edit":
			int id = Integer.parseInt(req.getParameter("id"));
			req.setAttribute("project", projectService.getById(id));
			req.getRequestDispatcher("groupwork-edit.jsp").forward(req, resp);
			break;

		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		
		switch (servletPath) {
		case "/groupwork-add":
			String name = req.getParameter("name");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");

			req.setAttribute("result", projectService.projectAdd(name, startDate, endDate));
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
			break;
			
		case "/groupwork-edit":
			int id = Integer.parseInt(req.getParameter("id"));
			String nameEdited = req.getParameter("name");
			String startDateEdited = req.getParameter("startDate");
			String endDateEdited = req.getParameter("endDate");
			req.setAttribute("result", projectService.updateById(id, nameEdited, startDateEdited, endDateEdited));
			req.getRequestDispatcher("groupwork-edit.jsp").forward(req, resp);
			break;

		default:
			break;
		}	
	}
}