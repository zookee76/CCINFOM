<%-- 
    Document   : searchtraineespresent_attendance
    Created on : 11 21, 23, 9:23:44 AM
    Author     : ccslearner
--%>

<%@page import="enrollmentmgt.attendance"%>
<%@page import="enrollmentmgt.trainees"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Searched Trainees by Attendance Report ID</title>
    </head>
    <body>
        <form action = "index.html">
            <jsp:useBean id="a" class="enrollmentmgt.trainees" scope="session"/>
            <jsp:useBean id="E" class="enrollmentmgt.attendance" scope="session"/>
            <%  
                int v_attendance_report_id = Integer.parseInt(request.getParameter("attendance_report_id"));
                
                E.attendance_report_id = v_attendance_report_id;
                
                int status = E.searchTraineePresent(E.attendance_report_id); 

                if (status == 1)
                {
            %>

            <h2>Trainees Present Details:</h2>
            <%
                    for (int i = 0; i < E.trainees_presentList.size(); i++) 
                    {   int trainee_id = E.trainees_presentList.get(i);
            %>
                        <p>Trainee ID: <%= trainee_id %></p>
                        <br/>
            <%
                    }
            %>
            <% } else {%>
            <h1>Failed to Display Trainees Present.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>
