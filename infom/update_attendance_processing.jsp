<%-- 
    Document   : modify_attendance_processing
    Created on : 11 20, 23, 9:48:46 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.*, java.sql.*, enrollmentmgt.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log Attendance Processing</title>
    </head>
    <body>
        <form action = "index.html">
            <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session" />
            <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>
            <jsp:useBean id="E" class="enrollmentmgt.attendance" scope="session" />
            <%  
                String v_attendance_date = request.getParameter("attendance_date");
                int v_mentor_id = Integer.parseInt(request.getParameter("mentor_id"));
                boolean v_mentor_present = "on".equals(request.getParameter("mentorPresent"));
                String v_training_program = request.getParameter("training_program_name");
                String[] traineeIdsStr = request.getParameterValues("trainee_id");
                int[] v_traineeIds = new int[traineeIdsStr.length];
                
                if (traineeIdsStr != null) 
                {
                    v_traineeIds = new int[traineeIdsStr.length];

                    for (int i = 0; i < traineeIdsStr.length; i++) 
                    {
                        v_traineeIds[i] = Integer.parseInt(traineeIdsStr[i]);
                    }
                }
                
                java.sql.Date va_attendance_date = java.sql.Date.valueOf(v_attendance_date);    
                
                E.trainee_ids = v_traineeIds;
                E.attendance_date = va_attendance_date;
                E.mentor_id = v_mentor_id;
                E.present_mentor = v_mentor_present;
                E.training_program = v_training_program;
                
                int status = E.modifyAttendance(E.attendance_report_id, E.attendance_date, E.mentor_id, E.present_mentor, E.training_program, E.trainee_ids);
                
                if (status == 1)
                {
            %>

            <h1>Attendance Updated Successfully!</h1>
            <% } else {%>
            <h1>Failed to Update Attendance.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>
