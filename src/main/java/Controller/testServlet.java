package Controller;

import Dao.DepartmentDAO;
import entity.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// Hứng toàn bộ các đầu URL bắt đầu bằng /departments hoặc /department/id/{x} hoặc /department/name/{x}
@WebServlet(urlPatterns = {"/departments", "/department/*"})
public class testServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        DepartmentDAO deptDAO = new DepartmentDAO();

        // Kiểm tra kết nối
        deptDAO.checkDepartmentDAO();

        // Lấy danh sách phòng ban
        List<Department> list = deptDAO.findAll();

        // In ra Console giống hình mẫu
        for (Department d : list) {
            System.out.println(d.getId());
            System.out.println(d.getName());
            System.out.println(d.getDescription());
            System.out.println("---------------------");
        }

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("Test DepartmentDAO Success!");
    }
}


