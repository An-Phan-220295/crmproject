package crm_project.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/user-add", "/users","/api/user/*" })
public class UserFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		String servletPath = request.getServletPath();

		switch (servletPath) {
		case "/users":
			if (session != null) {
				String role = (String) session.getAttribute("role");
				if (role.toLowerCase().equals("admin") || role.toLowerCase().equals("leader")) {
					chain.doFilter(request, response);
				} else {
					response.sendRedirect(request.getContextPath() + "/index.html");
				}
			} else {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			break;

		case "/user-add":
			if (session != null) {
				String role = (String) session.getAttribute("role");
				if (role.toLowerCase().equals("admin")) {
					chain.doFilter(request, response);
				} else {
					response.sendRedirect(request.getContextPath() + "/index.html");
				}
			} else {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			break;
			
		case "/api/user/*":
			if (session != null) {
				String role = (String) session.getAttribute("role");
				if (role.toLowerCase().equals("admin")) {
					chain.doFilter(request, response);
				} else {
					response.sendRedirect(request.getContextPath() + "/index.html");
				}
			} else {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			break;	
			
		default:
			break;
		}
	}
}
