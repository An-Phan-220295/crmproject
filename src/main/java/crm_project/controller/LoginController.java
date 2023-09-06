package crm_project.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project.config.MysqlConfig;
import crm_project.entity.Users;

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

@WebServlet(name = "loginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int maxAge = 8 * 60 * 60;
		//Cookie cookie = new Cookie("hoten", "NguyenVanA");
		Cookie cookie = new Cookie("hoten", URLEncoder.encode("Nguyễn Văn A", "UTF-8"));
		cookie.setMaxAge(maxAge);

		// Yêu cầu client tạo cookie
		resp.addCookie(cookie);

		req.getRequestDispatcher("login.html").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Bước 1: lấy dữ liệu người dùng nhập vào ô email và password
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		//Bước 2: viết câu query kiểm tra email và password người dùng nhập có nằm trong CSDL hay không?
		//?: đại diện cho tham số được truyền vào khi sử dụng JDBC.
		String query = "SELECT * FROM Users u\r\n"
				+ "WHERE u.email  = ?\r\n"
				+ " AND u.pwd =?";
		
		//Bước 3:dùng DJBC kết nối tới CSDL và truyền câu query ở bước 2 cho CSDL thực thi
		//Mở kết nối tới CSDL
		Connection conn = MysqlConfig.getConnect();
		try {
			//Chuẩn bị câu query cho truyền xuống CSDL thông qua PreparedStatement
			PreparedStatement statement = conn.prepareStatement(query);
			
			//Gán giá trị cho tham số trong câu query có dấu (?)
			statement.setString(1, email);
			statement.setString(2, password);
			
			//Thực thi câu query
			/*
			 * executeUpdate:Nếu câu query khác SELECT =>INSERT, UPDATE, DELETE.
			 * executeQuery: nếu như câu query là SELECT
			 */
			
			ResultSet resultSet = statement.executeQuery();
			List<Users> listUser = new ArrayList<Users>();
			
			//Khi nào biến resulttSet còn qua dòng được thì làm.
			while(resultSet.next()) {
				// Duyệt qua từng dòng dữ liệu truy vấn được trong CSDL.
				Users users = new Users();
				//Lấy dữ liệu từ cột duyệt được và lưu vào thuộc tính của đối tượng
				users.setId(resultSet.getInt("id"));
				users.setEmail(resultSet.getString("email"));
				listUser.add(users);
			}
			if(listUser.size()>0)
				//User tồn tại => đăng  nhập thành công
				System.out.println("Đăng nhập thành công");
			else {
				//User không tồn tại=>đăng nhập thất bại.
				System.out.println("Đăng nhập thất bại");
			}
				
		} catch (Exception e) {
			System.out.println("Lỗi thực thi truy vấn"+ e.getLocalizedMessage());
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Lỗi đóng kết nối "+ e.getLocalizedMessage());
			}
		}
	}

}
