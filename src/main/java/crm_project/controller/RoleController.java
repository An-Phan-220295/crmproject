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

@WebServlet(name = "RoleController", urlPatterns = { "/role-add", "/roles" })
public class RoleController extends HttpServlet {
	private RoleService roleService = new RoleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getServletPath().equals("/role-add"))
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
		else {		
			req.setAttribute("listRole", roleService.getAllRole());
			req.getRequestDispatcher("/role-table.jsp").forward(req, resp);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String roleName = req.getParameter("roleName");
		String description = req.getParameter("description");
		
		boolean isSuccess = roleService.addRole(roleName, description);
		
		req.setAttribute("isSuccess", isSuccess);
		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	}
}
