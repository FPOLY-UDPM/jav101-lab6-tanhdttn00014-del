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

@WebServlet("/department")
public class DepartmentServlet extends HttpServlet {

    private final DepartmentDAO deptDAO = new DepartmentDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // JDBC V3 - findAll()
        List<Department> list = deptDAO.findAll();

        // Kiểm tra dữ liệu Console
        System.out.println("===== FIND ALL =====");
        for (Department d : list) {
            System.out.println(d.getId());
            System.out.println(d.getName());
            System.out.println(d.getDescription());
            System.out.println("------------------");
        }

        request.setAttribute("departments", list);

        request.getRequestDispatcher("/view_dept/department-list.jsp")
                .forward(request, response);
    }
}

