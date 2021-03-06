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
    <title>Friend List App</title>
  </head>
  <body>
    <div class="navlinks">
      <h1>Welcome, <c:out value="${me.username}" />!</h1>
      <a href="/logout">Logout</a>
    </div>
    <h3>All Members</h3>
    <table
      class="table table-striped table-bordered table-hover table-condensed table-dark"
    >
      <thead>
        <th id="username">Username</th>
        <th id="status">Action/Status</th>
      </thead>
      <c:forEach items="${allOtherUsers}" var="user">
        <tr>
          <td>
            <a href="/friendlist/${user.id}"
              ><c:out value="${user.username}"
            /></a>
          </td>
          <c:choose>
            <c:when test="${me.requestsReceivedFrom.contains(user)}">
              <td>
                Received Requests From..<a href="/friendlist/${user.id}/reject"
                  >Decline</a
                >
              </td>
            </c:when>
            <c:when test="${me.usersRequestedTo.contains(user)}">
              <td>
                Sent Requests To..<a href="/friendlist/${user.id}/cancel"
                  >Cancel</a
                >
              </td>
            </c:when>
            <c:when test="${me.friends.contains(user)}">
              <td>
                Confirmed Friend
                <a href="/friendlist/${user.id}/unfriend">Unfriend</a>
              </td>
            </c:when>
            <c:otherwise>
              <td>
                <a href="/friendlist/${user.id}/request">Request Friend</a>
              </td>
            </c:otherwise>
          </c:choose>
        </tr>
      </c:forEach>
    </table>
    <h3>Confirmed Friends</h3>
    <h3>Pending Requests I Sent</h3>
    <h3>Requests Received From Other People</h3>
  </body>
</html>
