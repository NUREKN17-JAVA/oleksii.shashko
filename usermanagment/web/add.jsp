<%--
  Created by IntelliJ IDEA
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User management/ Add</title>
</head>
<body>
<form action="<%request.getContextPath();%>/add" method="post">
    First name <input type="text" name="firstName" value=""><br>
    Last name <input type="text" name="lastName" value=""><br>
    Date of Birth <input type="text" name="date" value=""><br>
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
