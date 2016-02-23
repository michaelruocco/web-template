<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>Create Customer</title>
  </head>
  <body>
    <form action="createCustomer" method="post">
      <%@include file="../tags/customerForm.tag" %>
      <input type="submit" value="Create">
    </form>
    <%@include file="../tags/error.tag" %>
    <a href="<c:url value = '/listCustomers' />">Back</a>
  </body>
</html>