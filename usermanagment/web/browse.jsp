<%--
  Created by IntelliJ IDEA.
  Date: 22.12.2019
  Time: 0:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User management/Browse</title>
</head>
<body>
    <form action="<%=request.getContextPath()%>/browse" method="post">
        <table id="userTable" border="1">
            <tr>
                <th></th>
                <th>First Name</th>
                <th>Last name</th>
                <th>Date of Birth</th>
            </tr>
            <c:forEach var="user" items="${sessionScope.users}">
                <tr>
                    <td><input type="radio" name="id" id="id" value="${user.id}"></td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.dateOfBirth}</td>
                </tr>
            </c:forEach>
        </table><br>
        <input type="submit" name="addButton" value="Add">
        <input type="submit" name="editButton" value="Edit">
        <input type="submit" name="deleteButton" value="Delete">
        <input type="submit" name="detailsButton" value="Details">
    </form>
</body>
</html>
