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
    <table id="userTable" border="1">
        <tr>
            <th></th>
            <th>First name</th>
            <th>Last name</th>
            <th>Date of birth</th>
        </tr>
        <c:forEach var="user" items="${sessionScope.users}">
            <tr>
                <th><input type="radio" name="id" id="id" value="${user.id}"></th>
                <th>${user.firstName}</th>
                <th>${user.lastName}</th>
                <th>${user.dateOfBirth}</th>
            </tr>
        </c:forEach>
    </table>
</body>
</html>