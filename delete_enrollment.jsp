<%-- 
    Document   : delete_enrollment
    Created on : 11 13, 23, 5:39:02 PM
    Author     : ccslearner
--%>
<%@page import="java.util.*, enrollmentmgt.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Enrollment</title>
</head>
<body>
    <form action="delete_enrollment_processing.jsp" method="post">
        <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session"/>
        Trainee ID:<select id="trainee_id" name ="trainee_id">
            <%
                A.listTrainees();
                for (int i = 0; i < A.trainee_idList.size(); i++) {
            %>
            <option value="<%=A.trainee_idList.get(i)%>"><%= A.trainee_idList.get(i) %></option>
            <% } %>
        </select><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>

