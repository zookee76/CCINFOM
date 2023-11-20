<%-- 
    Document   : searchbyprogram_attendance
    Created on : 11 21, 23, 1:26:07 AM
    Author     : ccslearner
--%>

<%@page import="enrollmentmgt.attendance"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Searched Trainees by Program</title>
    </head>
    <body>
        <form action = "index.html">
            <jsp:useBean id="E" class="enrollmentmgt.attendance" scope="session"/>
            <%  
                String v_training_program = request.getParameter("program_name");
                
                E.training_program = v_training_program;
                
                int status = E.searchAttendanceReportProgram(E.training_program); 

                if (status == 1)
                {
            %>

            <h2>Attendance Report Details:</h2>
            <%
                    for (int i = 0; i < E.attendance_List.size(); i++) 
                    {   attendance attendance = E.getAttendanceList().get(i);
            %>
                        <p>Attendance Report ID: <%= attendance.getAttendanceReportId() %></p>
                        <p>Attendance Date: <%= attendance.getAttendanceDate() %></p>
                        <p>Mentor ID: <%= attendance.getMentorId() %></p>
                        <p>Present Mentor: <%= attendance.isPresentMentor() %></p>
                        <p>Training Program: <%= attendance.getTrainingProgram() %></p>
                        <p>Section ID: <%= attendance.getSectionId() %></p>
                        <br/>
            <%
                    }
            %>
            <% } else {%>
            <h1>Failed to Display Attendance Report Details.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>
