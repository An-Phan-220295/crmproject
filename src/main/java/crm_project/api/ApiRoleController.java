package crm_project.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import crm_project.payload.response.BaseResponse;
import crm_project.service.RoleService;

@WebServlet(name = "ApiRoleController", urlPatterns = {"/api/role/delete"})
public class ApiRoleController extends HttpServlet{
	private RoleService roleService = new RoleService();
	private Gson gson = new Gson();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		boolean result = roleService.deleteById(id);
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(200);
		baseResponse.setMesage(result ? "Xóa thành công" : "Xóa thất bại");
		baseResponse.setData(result);
		
		String dataJson = gson.toJson(baseResponse);
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		out.print(dataJson);
		out.flush();
	}
}
