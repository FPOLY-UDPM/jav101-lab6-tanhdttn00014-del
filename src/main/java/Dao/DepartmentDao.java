package Dao;

import entity.Department;
import util.JdbcV1;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    private String stmSELECT = "SELECT [Id], [Name], [Description] FROM [dbo].[Departments]";

    // Cách viết này chỉ để test nhanh, VI PHẠM QUY ĐỊNH MÔ HÌNH MVC
    public void checkDepartmentDAO() {
        try {
            String sql = stmSELECT;
            ResultSet resultSet = JdbcV1.executeQuery(sql);
            while (resultSet.next()) {
                // Phần lấy dữ liệu (M)
                String maPhong = resultSet.getString("Id");
                String tenPhong = resultSet.getString("Name");
                String motaPhong = resultSet.getString("Description");

                // Phần hiển thị dữ liệu (V), VI PHẠM MÔ HÌNH MVC
                System.out.println(maPhong);
                System.out.println(tenPhong);
                System.out.println(motaPhong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        try {
            // Gọi hàm truy vấn
            ResultSet resultSet = JdbcV1.executeQuery(stmSELECT);
//            ResultSet resultSet = JdbcV2.executeQuery(stmSELECT);
//            ResultSet resultSet = JdbcV3.executeQuery(callSELECT);
            while (resultSet.next()) {
                Department dept = new Department();
                dept.setId(resultSet.getString("Id"));
                dept.setName(resultSet.getString("Name"));
                dept.setDescription(resultSet.getString("Description"));

                list.add(dept); // Nạp vào mảng tổng
            }
            // Lưu ý: Nhớ đóng kết nối sau khi duyệt xong để tránh rò rỉ tài nguyên
            resultSet.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // Trả mảng về cho Servlet sử dụng
    }
}
