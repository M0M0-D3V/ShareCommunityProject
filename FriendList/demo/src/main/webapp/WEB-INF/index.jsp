<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <!-- CSS only -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
      crossorigin="anonymous"
    />
    <link href="/css/style.css" rel="stylesheet" />
    <title>Events</title>
  </head>
  <body>
    <h1>Welcome to Events!</h1>
    <div class="row">
      <div class="col-md-6">
        <h3>Register</h3>
        <form:form
          method="POST"
          action="/registration"
          modelAttribute="registration"
          class="form-inline"
        >
          <div class="form-group">
            <p>
              <form:label path="username">Username:</form:label>
              <form:errors path="username" class="errors" />
              <form:input type="text" path="username" class="form-control" />
            </p>
            <p>
              <form:label path="firstName">First Name:</form:label>
              <form:errors path="firstName" class="errors" />
              <form:input type="text" path="firstName" class="form-control" />
            </p>
            <p>
              <form:label path="lastName">Last Name:</form:label>
              <form:errors path="lastName" class="errors" />
              <form:input type="text" path="lastName" class="form-control" />
            </p>
            <p>
              <form:label path="email">Email:</form:label>
              <form:errors path="email" class="errors" />
              <form:input type="email" path="email" class="form-control" />
            </p>
            <p>
              <form:label path="contact">Phone:</form:label>
              <form:errors path="contact" class="errors" />
              <form:input type="text" path="contact" class="form-control" />
            </p>
            <p>
              <form:label path="password">Password:</form:label>
              <form:errors path="password" class="errors" />
              <form:password path="password" class="form-control" />
            </p>
            <p>
              <form:label path="passwordConfirmation"
                >Password Confirmation:</form:label
              >
              <form:errors path="passwordConfirmation" class="errors" />
              <form:password path="passwordConfirmation" class="form-control" />
            </p>
            <input
              type="submit"
              value="Register!"
              class="btn btn-outline-primary btn-block"
            />
          </div>
        </form:form>
      </div>
      <div class="col-md-6">
        <h3>Login</h3>
        <form method="post" action="/login" class="form-inline">
          <div class="form-group">
            <p>
              <label name="email">Email</label>
              <input type="email" name="email" class="form-control" />
            </p>
            <p>
              <label name="password">Password</label>
              <input type="password" name="password" class="form-control" />
            </p>
            <input
              type="submit"
              value="Login!"
              class="btn btn-block btn-outline-primary"
            />
          </div>
        </form>
        <p class="errors"><c:out value="${error}" /></p>
      </div>
    </div>
  </body>
</html>
