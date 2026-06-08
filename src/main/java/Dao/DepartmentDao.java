package Dao;

import util.JdbcV1;

import java.sql.ResultSet;

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

}
