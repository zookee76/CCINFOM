<%-- 
    Document   : delete_attendance
    Created on : 11 20, 23, 10:41:04 PM
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
    <title>Delete Attendance</title>
</head>
<body>
    <form action="delete_attendance_processing.jsp" method="post">
        <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session" />
        <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>
        <jsp:useBean id="E" class="enrollmentmgt.attendance" scope="session" />
         Attendance Report ID:<select id="attendance_report_id" name ="attendance_report_id">
            <%
                E.listAttendance();
                for (int i = 0; i < E.attendance_idList.size(); i++) {
            %>
            <option value="<%=E.attendance_idList.get(i)%>"><%= E.attendance_idList.get(i) %></option>
            <% } %>
         </select><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>