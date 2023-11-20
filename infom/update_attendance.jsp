<%-- 
    Document   : modify_attendance
    Created on : 11 20, 23, 9:48:32 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Attendance Information</title>
    </head>
    <body>
        <form action ="update_attendance_processing.jsp" method="post">
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
            Attendance Date: <input type="date" id="attendance_date" name="attendance_date" pattern="\d{4}-\d{2}-\d{2}" title="Enter a date in the format yyyy-mm-dd"><br>
            Trainees Present:
            <select id="trainee_id" name="trainee_id" multiple>
                <%
                    A.listTrainees();
                    for (int i = 0; i < A.trainee_idList.size(); i++) {
                %>
                <option value="<%=A.trainee_idList.get(i)%>"><%= A.trainee_idList.get(i) %></option>
                <% } %>
            </select><br>
            
            Mentor ID: <input type="number" id="mentor_id" name="mentor_id"><br>
            
            Mentor Present: <input type="checkbox" id="mentorPresent" name="mentorPresent"><br>
            Training Program:
               <select id="training_program_name" name="training_program_name">
                    <%
                        B.listProgramNames();
                        for (int i = 0; i < B.program_names.size(); i++)
                        {
                    %>
                        <option value="<%=B.program_names.get(i)%>"><%= B.program_names.get(i)%></option>
                    <%
                        }
                    %>
            <input type="Submit" value="Submit">
        </form>
    </body>
</html>

