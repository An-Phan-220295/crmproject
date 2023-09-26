package crm_project.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crm_project.config.MysqlConfig;
import crm_project.entity.Users;
import crm_project.service.UserService;

/*
 * package
 * - Controller : là nơi chứa toàn bộ file liên quan tới khai báo đường dẫn và xử lý đường dẫn.
 * 
 * Tính năng login:
 * Bước 1: lấy dữ liệu người dùng nhập vào ô email và password
 * Bước 2: viết câu query kiểm tra email và password người dùng nhập có nằm trong CSDL hay không?
 * Bước 3: dùng DJBC kết nối tới CSDL và truyền câu query ở bước 2 cho CSDL thực thi
 * Bước 4: kiểm tra dữ liệu, nếu có dữ liệu thì đăng nhập thành công và ngược lại.
 * 
 */

@WebServlet(name = "loginController", urlPatterns = { "/login", "/logout" })
public class LoginController extends HttpServlet {
	UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		HttpSession session = req.getSession();
		switch (servletPath) {
		case "/login":
			req.getRequestDispatcher("login.jsp").forward(req, resp);

			break;

		case "/logout":
			session.invalidate();
			resp.sendRedirect(req.getContextPath() +"/login");
			break;

		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String messages = "";
		HttpSession session = req.getSession();

		if (email == null || email.isEmpty()) {
			messages = "Please enter username";
		}

		if (password == null || password.isEmpty()) {
			messages = "Please enter password";
		}

		if (messages.isEmpty()) {
			Users user = new Users();
			user = userService.login(email, password);
			if (user.getEmail() != null) {
				if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
					session.setAttribute("user", user.getEmail());
					session.setAttribute("role", user.getRole().getName());
					session.setMaxInactiveInterval(1800);
					resp.sendRedirect(req.getContextPath() + "/index.html");
					return;
				} else {
					messages = "Unknown login, please try again";
				}
			} else {
				messages = "Unknown login, please try again";
			}
		}
		req.setAttribute("messages", messages);
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
}
