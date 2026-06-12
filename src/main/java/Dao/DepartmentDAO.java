package Dao;

import entity.Department;
import util.JdbcV1;
import util.JdbcV2;
import util.JdbcV3;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    //JDBC V1
    private String stmSELECT = "SELECT [Id], [Name], [Description] FROM [dbo].[Departments]";

    //JDBC V2
    private String stmSELECT_byId =
            "SELECT [Id], [Name], [Description] " +
                    "FROM [dbo].[Departments] " +
                    "WHERE [Id] = ?";
    private String stmSELECT_byName =
            "SELECT [Id], [Name], [Description] " +
                    "FROM [dbo].[Departments] " +
                    "WHERE [Name] = ?";

    // Câu lệnh SQL phục vụ CRUD
    private String stmINSERT = "INSERT INTO [dbo].[Departments] ([Id], [Name], [Description]) VALUES (?, ?, ?)";
    private String stmUPDATE = "UPDATE [dbo].[Departments] SET [Name] = ?, [Description] = ? WHERE [Id] = ?";
    private String stmDELETE = "DELETE FROM [dbo].[Departments] WHERE [Id] = ?";

    //JDBC V3
    // Câu lệnh call stored procedure
    private String callSELECT = "exec spSelectAll";
    private String callSELECT_byId = "exec spSelectById(?)";
    // Bổ sung thêm : callSELECT_byName ??
    private String callINSERT = "exec spInsert(?,?,?)";
    private String callUPDATE = "exec spUpdate(?,?,?)";
    private String callDELETE_byId = "exec spDeleteById(?)";

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
//            ResultSet resultSet = JdbcV1.executeQuery(stmSELECT);
            ResultSet resultSet = JdbcV2.executeQuery(stmSELECT);
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

    public Department findById(String id) {
        Department dept = new Department();
        try {
            // Gọi hàm truy vấn
//            ResultSet resultSet = JdbcV1.executeQuery(stmSELECT);
            ResultSet resultSet = JdbcV2.executeQuery(stmSELECT_byId, id);
//            ResultSet resultSet = JdbcV3.executeQuery(callSELECT_byId, id);
            if (resultSet.next()) {
                dept.setId(resultSet.getString("Id"));
                dept.setName(resultSet.getString("Name"));
                dept.setDescription(resultSet.getString("Description"));
            }
            // Lưu ý: Nhớ đóng kết nối sau khi duyệt xong để tránh rò rỉ tài nguyên
            resultSet.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dept; // Trả mảng về cho Servlet sử dụng
    }

    public List<Department> findByName(String name) {
        List<Department> list = new ArrayList<>();
        try {
            // Gọi hàm truy vấn
            ResultSet resultSet = JdbcV2.executeQuery(stmSELECT_byName, '%' + name + '%');
            while (resultSet.next()) {
                Department dept = new Department();
                dept.setId(resultSet.getString("Id"));
                dept.setName(resultSet.getString("Name"));
                dept.setDescription(resultSet.getString("Description"));
                list.add(dept);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. THÊM MỚI - Sử dụng executeUpdate
    public int insert(Department dept) {
        try {
//            return JdbcV2.executeUpdate(stmINSERT, dept.getId(), dept.getName(), dept.getDescription());
            return JdbcV3.executeUpdate(callINSERT, dept.getId(), dept.getName(), dept.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 4. CẬP NHẬT - Sử dụng executeUpdate
    public int update(Department dept) {
        try {
//            return JdbcV2.executeUpdate(stmUPDATE, dept.getName(), dept.getDescription(), dept.getId());
            return JdbcV3.executeUpdate(callUPDATE, dept.getName(), dept.getDescription(), dept.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 5. XÓA - Sử dụng executeUpdate
    public int delete(String id) {
        try {
//            return JdbcV2.executeUpdate(stmDELETE, id);
            return JdbcV3.executeUpdate(callDELETE_byId, id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //NÊN VIẾT KHỐI TRY (...) NHƯ SAU
    // GOM CÁC NỘI DUNG CẦN THIẾT ĐẶT TRONG KHỐI TRY
    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
        // Java sẽ tự động đóng toàn bộ các tài nguyên khai báo trong ngoặc tròn này
        try (java.sql.Connection conn = JdbcV3.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(callSELECT);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Department dept = new Department(
                        resultSet.getString("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description")
                );
                list.add(dept);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
