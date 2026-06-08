package Controller;

import Dao.DepartmentDao;
import entity.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/testdb")
public class testServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DepartmentDao dao = new DepartmentDao();

        dao.checkDepartmentDAO();

        // 1. Lấy mảng dữ liệu từ Model
        List<Department> list = dao.findAll();

        // 2. Đóng gói vào Request Attribute để gửi cho View
        req.setAttribute("departments", list);

        // 3. Forward thẳng sang trang JSP hiển thị giao diện
        req.getRequestDispatcher("/view_dept/department-list.jsp").forward(req, resp);
    }
}
