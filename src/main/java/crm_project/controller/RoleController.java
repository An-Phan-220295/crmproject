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
import crm_project.entity.Role;
import crm_project.service.RoleService;

@WebServlet(name = "RoleController", urlPatterns = { "/role-add", "/roles", "/role-edit" })
public class RoleController extends HttpServlet {
	private RoleService roleService = new RoleService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();

		switch (servletPath) {
		case "/role-add":
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			break;

		case "/roles":
			req.setAttribute("listRole", roleService.getAllRole());
			req.getRequestDispatcher("/role-table.jsp").forward(req, resp);
			break;

		case "/role-edit":
			int id = Integer.parseInt(req.getParameter("id"));
			req.setAttribute("role", roleService.getById(id));
			req.getRequestDispatcher("/role-edit.jsp").forward(req, resp);
			break;

		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();

		switch (servletPath) {
		case "/role-add":
			String roleName = req.getParameter("roleName");
			String description = req.getParameter("description");

			boolean isSuccess = roleService.addRole(roleName, description);

			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			break;

		case "/role-edit":
			String roleNameEdited = req.getParameter("roleName");
			String descriptionEdited = req.getParameter("description");
			int id = Integer.parseInt(req.getParameter("id"));
			
			boolean result = roleService.updateById(id, roleNameEdited, descriptionEdited);
			req.setAttribute("result", result);
			req.setAttribute("role", roleService.getById(id));
			req.getRequestDispatcher("/role-edit.jsp").forward(req, resp);
			break;

		default:
			break;
		}

	}
}
