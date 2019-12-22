<%--
  Created by IntelliJ IDEA.
  Date: 22.12.2019
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="ua.nure.kn.shashko.usermanagment.domain.User" scope="session"/>
<html>
<head>
    <title>User management/ Edit</title>
</head>
<body>
<form action="<%request.getContextPath();%>/edit" method="post">
    <input type="hidden" name="id" value="${user.id}">
    First name <input type="text" name="firstName" value="${user.firstName}"><br>
    Last name <input type="text" name="lastName" value="${user.lastName}"><br>
    <fmt:parseDate value="${user.dateOfBirth}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
    Date of Birth <input type="text" name="date"
                         value="<fmt:formatDate value="${parsedDate}" type="date" pattern="yyyy-MM-dd"/>"><br>
    <input type="submit" name="okButton" value="Ok">
    <input type="submit" name="cancelButton" value="Cancel">
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert('${requestScope.error}');
    </script>
</c:if>
</body>
</html>