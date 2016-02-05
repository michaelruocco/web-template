<!DOCTYPE HTML>
<html>
  <head>
    <title>Add Customer</title>
  </head>
  <body>
    <form action="createCustomer" method="post">
      <table border="0">
        <tr>
          <td>Id:</td>
          <td><input type="text" name="id"/></td>
        </tr>
        <tr>
          <td>First Name:</td>
          <td><input type="text" name="firstName"/></td>
        </tr>
        <tr>
          <td>Surname:</td>
          <td><input type="text" name="surname"/></td>
        </tr>
        <tr>
          <td>Balance:</td>
          <td><input type="text" name="balance" /></td>
        </tr>
      </table>
      <input type="submit" value="Create">
    </form>
  </body>
</html>