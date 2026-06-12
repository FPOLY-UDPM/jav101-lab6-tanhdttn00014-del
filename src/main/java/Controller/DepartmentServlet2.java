package Controller;

import Dao.DepartmentDAO;
import entity.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/departments2", "/department2/*"})
public class DepartmentServlet2 extends HttpServlet {

    private DepartmentDAO deptDAO = new DepartmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Department> resultList = new ArrayList<>();

        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();

        try {

            // Hiển thị toàn bộ danh sách
            if ("/departments2".equals(servletPath)) {

                resultList = deptDAO.findAll();

            }

            // Tìm kiếm theo URL
            else if ("/department2".equals(servletPath)) {

                if (pathInfo != null) {

                    String[] pathParts = pathInfo.split("/");

                    if (pathParts.length >= 3) {

                        String fieldName = pathParts[1];
                        String keyword = pathParts[2];

                        switch (fieldName) {

                            case "id":

                                Department d = deptDAO.findById(keyword);

                                // CHỈ GIỮ 1 DỮ LIỆU
                                resultList = new ArrayList<>();

                                if (d != null) {
                                    resultList.add(d);
                                }

                                break;

                            case "name":

                                resultList = deptDAO.findByName(keyword);

                                break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Servlet Path = " + servletPath);
        System.out.println("Path Info    = " + pathInfo);
        System.out.println("Result Size  = " + resultList.size());

        Department department = null;

        if (!resultList.isEmpty()) {
            department = resultList.get(0);
        }

        req.setAttribute("department", department);
        req.setAttribute("list", resultList);

        req.getRequestDispatcher("/view_dept/department-list2.jsp")
                .forward(req, resp);
    }
}