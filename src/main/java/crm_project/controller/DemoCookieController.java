package crm_project.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "demoCookieController", urlPatterns = {"/demo-cookie"})
public class DemoCookieController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//Lấy giá trị đã lưu trữ trong cookie
		Cookie[] arraycookies = req.getCookies();
		
		for (Cookie cookie : arraycookies) {
			if(cookie.getName().equals("hoten")){
				String value = cookie.getValue();
				System.out.println("Kiemtra " +URLDecoder.decode(value,"UTF-8"));
			}
		}
	}
}
