<%-- 
    Document   : logattendance
    Created on : 11 19, 23, 11:55:55 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Enrollment Information</title>
    </head>
    <body>
        <form action="log_attendance_processing.jsp" method="post">
            <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session" />
            <jsp:useBean id="B" class="enrollmentmgt.attendance" scope="session" />
            
            Trainee ID:
            <select id="trainee_id" name="trainee_id" multiple>
                <%
                    A.listTrainees();
                    for (int i = 0; i < A.trainee_idList.size(); i++) {
                %>
                <option value="<%=A.trainee_idList.get(i)%>"><%= A.trainee_idList.get(i) %></option>
                <% } %>
            </select>

            <input type="submit" value="Submit">
        </form>
    </body>
</html>
