<%-- 
    Document   : delete_attendance_processing
    Created on : 11 20, 23, 10:41:18 PM
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
    <form action="index.html">
        <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session" />
        <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>
        <jsp:useBean id="E" class="enrollmentmgt.attendance" scope="session" />
            <%  
                int v_attendance_report_id = Integer.parseInt(request.getParameter("attendance_report_id"));
                
                E.attendance_report_id = v_attendance_report_id;
                
                int status = E.deleteAttendance(E.attendance_report_id);

                if (status == 1)
                {
            %>

            <h1>Successfully Deleted Attendance Information!</h1>
            <% } else {%>
            <h1>Failed to Delete Attendance Information.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
    </form>
</body>
</html>
