<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>${name}</title>
  </head>
  <body>
    <div>
        <c:choose>
        <c:when test="${empty customers}">
          No customers found
        </c:when>
        <c:otherwise>
          <table border="1">
            <tr>
                <th>Name</th>
                <th>Balance</th>
                <th>Action</th>
            </tr>
            <c:forEach var="customer" items="${customers}" varStatus="status">
              <tr>
                <td><a href="updateCustomer?id=${customer.id}">${customer.fullName}</a></td>
                <td>${customer.balance}</td>
                <td><a href="deleteCustomer?id=${customer.id}">delete</a></td>
              <tr>
            </c:forEach>
          </table>
        </c:otherwise>
        </c:choose>
    </div>
    <div>
        <a href="createCustomer">Add Customer</a>
        <a href="<c:url value = '/' />">Home</a>
    </div>
  </body>
</html>