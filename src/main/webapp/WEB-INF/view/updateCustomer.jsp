<!DOCTYPE HTML>
<html>
  <head>
    <title>${name}</title>
  </head>
  <body>
    <form action="updateCustomer" method="post">
      <table border="0">
        <tr>
          <td>Id:</td>
          <td><input type="text" name="id" value="${customer.id}"/></td>
        </tr>
        <tr>
          <td>First Name:</td>
          <td><input type="text" name="firstName" value="${customer.firstName}"/></td>
        </tr>
        <tr>
          <td>Surname:</td>
          <td><input type="text" name="surname" value="${customer.surname}"/></td>
        </tr>
        <tr>
          <td>Balance:</td>
          <td><input type="text" name="balance" value="${customer.balance}"/></td>
        </tr>
      </table>
      <input type="submit" value="Update">
    </form>
  </body>
</html>