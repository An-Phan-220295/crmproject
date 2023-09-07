package crm_project.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project.service.UserService;

@WebServlet(name = "userController", urlPatterns = { "/user-add", "/users","/user-edit" })
public class UserController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		UserService userService = new UserService();

		switch (path) {
		case "/user-add":
			req.setAttribute("listRole", userService.getAllRole());
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);
			break;

		case "/users":
			req.setAttribute("listUser", userService.getAllUsers());
			req.getRequestDispatcher("user-table.jsp").forward(req, resp);
			break;
			
		case "/user-edit":
			
			int id =Integer.parseInt(req.getParameter("id"));
			req.setAttribute("user", userService.getById(id));
			req.setAttribute("listRole", userService.getAllRole());
			req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
			break;

		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		UserService userService = new UserService();
		switch (servletPath) {
		case "/user-add":
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html; charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");

			String fullName = req.getParameter("fullname");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String phone = req.getParameter("phone");
			int idRole = Integer.parseInt(req.getParameter("role"));

			boolean isSuccess = userService.insertUser(fullName, email, password, phone, idRole);

			req.setAttribute("listRole", userService.getAllRole());
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);
			break;

		case "/user-edit":
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html; charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			
			int id =Integer.parseInt(req.getParameter("id"));
			String fullNameEdited = req.getParameter("fullname");
			String userNameEdited = req.getParameter("username");
			int idRoleEdited = Integer.parseInt(req.getParameter("role"));
			boolean result = userService.updateById(id, fullNameEdited, userNameEdited, idRoleEdited);
			req.setAttribute("result", result);
			req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
			
			break;

		default:
			break;
		}
		
		
		
	}
}
