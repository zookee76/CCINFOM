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
        <title>Log Attendance</title>
    </head>
    <body>
        <form action="logattendance_processing.jsp" method="post">
            <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session" />
            <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>
            <jsp:useBean id="E" class="enrollmentmgt.attendance" scope="session" />
            Attendance Date: <input type="date" id="attendance_date" name="attendance_date" pattern="\d{4}-\d{2}-\d{2}" title="Enter a date in the format yyyy-mm-dd"><br>
            Trainees Present:
            <select id="trainee_id" name="trainee_id" multiple>
                <%
                    A.listTrainees();
                    for (int i = 0; i < A.trainee_idList.size(); i++) {
                %>
                <option value="<%=A.trainee_idList.get(i)%>"><%= A.trainee_idList.get(i) %></option>
                <% } %>
            </select>
            
            Mentor ID: <input type="number" id="mentor_id" name="mentor_id"><br>
            
            Mentor Present: <input type="checkbox" id="mentorPresent" name="mentorPresent">
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
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
