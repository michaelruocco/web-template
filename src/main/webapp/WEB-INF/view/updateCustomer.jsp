<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>${name}</title>
  </head>
  <body>
    <form action="updateCustomer" method="post">
      <%@include file="../tags/customerForm.tag" %>
      <input type="submit" value="Update">
    </form>
    <%@include file="../tags/error.tag" %>
    <a href="<c:url value = '/listCustomers' />">Back</a>
  </body>
</html>