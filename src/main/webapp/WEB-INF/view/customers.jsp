<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>${name}</title>
  </head>
  <body>
    <table border="1">
        <tr>
        <th>Name</th>
        <th>Balance</th>
        </tr>
        <c:forEach var="customer" items="${customers}" varStatus="status">
            <tr>
                <td><a href="customer?id=${customer.id}">${customer.fullName}</a></td>
                <td>${customer.balance}</td>
            <tr>
        </c:forEach>
    </table>
  </body>
</html>