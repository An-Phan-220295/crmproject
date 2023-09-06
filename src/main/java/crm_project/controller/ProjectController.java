package crm_project.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project.service.ProjectService;

@WebServlet(name = "GruopWorkController" , urlPatterns = {"/groupwork-add", "/groupwork"})
public class ProjectController extends HttpServlet{
	ProjectService projectService = new ProjectService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getServletPath().equals("/groupwork-add"))
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
		
		else {
			req.setAttribute("listWork", projectService.getAllProject());
			req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy thông tin người nhập.
		String name = req.getParameter("name");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");

		req.setAttribute("result", projectService.projectAdd(name, startDate, endDate));
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
	}
}