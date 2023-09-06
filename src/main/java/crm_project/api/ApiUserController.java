package crm_project.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;

import com.google.gson.Gson;

import crm_project.entity.Users;
import crm_project.payload.response.BaseResponse;
import crm_project.service.UserService;

/*
 * payload:
 * 		- response: chứa các class liên quan tới format json trả ra cho client
 * 		- request: chứa các class liên quan tới format json request client truyền lên server 
 */
@WebServlet(name = "apiUserController", urlPatterns = {"/api/user","/api/user/delete"})
public class ApiUserController extends  HttpServlet{
	private UserService userService = new UserService();
	
	//Khởi tạo thư viện GSON để sử dụng.
	private Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if(path.equals("/api/user")) {
			List<Users> listUser = userService.getAllUsers();
			
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setStatusCode(200);
			baseResponse.setMesage("");
			baseResponse.setData(listUser);
			//Chuyển List hoặc mảng về JSON.
			String dataJson = gson.toJson(baseResponse);
			
			//Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			
			out.print(dataJson);
			
			out.flush();
			
		}else if(path.equals("/api/user/delete")){
			int id =Integer.parseInt(req.getParameter("id"));
			boolean isSuccess = userService.deleteById(id);
			
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setStatusCode(200);
			baseResponse.setMesage(isSuccess ? "Xóa thành công":"Xóa thất bại");
			baseResponse.setData(isSuccess);
			
			//Chuyển List hoặc mảng về JSON.
			String dataJson = gson.toJson(baseResponse);
			
			//Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			
			out.print(dataJson);
			out.flush();
		}
	
	}
}
