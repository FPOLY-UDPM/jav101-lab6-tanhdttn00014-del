<%--
  Created by IntelliJ IDEA.
  User: TuanTA
--%>
<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         isELIgnored="false" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Danh Sách Phòng Ban</title>

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>

<body class="container mt-5">

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2 class="text-primary m-0">QUẢN LÝ PHÒNG BAN</h2>

    <a href="${pageContext.request.contextPath}/departments2?action=new"
       class="btn btn-success">
        ➕ Thêm Phòng Ban Mới
    </a>
</div>

<table class="table table-bordered table-striped">

    <thead class="thead-dark">
    <tr>
        <th>Mã Phòng Ban</th>
        <th>Tên Phòng Ban</th>
        <th>Mô Tả</th>
        <th style="width:180px" class="text-center">Hành Động</th>
    </tr>
    </thead>

    <tbody>

    <c:if test="${empty department}">
        <tr>
            <td colspan="4" class="text-center text-danger">
                Không có dữ liệu phòng ban
            </td>
        </tr>
    </c:if>

    <c:if test="${not empty department}">
        <tr>

            <td>
                <strong>${department.id}</strong>
            </td>

            <td>
                    ${department.name}
            </td>

            <td>
                    ${department.description}
            </td>

            <td class="text-center">

                <a href="${pageContext.request.contextPath}/departments2?action=edit&id=${department.id}"
                   class="btn btn-warning btn-sm">
                    ✏️ Sửa
                </a>

                <a href="${pageContext.request.contextPath}/departments2?action=delete&id=${department.id}"
                   class="btn btn-danger btn-sm"
                   onclick="return confirm('Thầy/Cô có chắc chắn muốn xóa phòng ban này không?');">
                    🗑️ Xóa
                </a>

            </td>

        </tr>
    </c:if>

    </tbody>

</table>

</body>
</html>