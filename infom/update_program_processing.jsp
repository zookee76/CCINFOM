<%-- 
    Document   : update_program_processing
    Created on : 11 18, 23, 11:43:54 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, enrollmentmgt.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Updating Enrollment Information</title>
    </head>
    <body>
        <form action = "index.html">
            <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>

            <%  
                String v_programName = request.getParameter("training_program_name");
                String v_startDate = request.getParameter("start_date");
                String v_endDate = request.getParameter("end_date");
                int v_cost = Integer.parseInt(request.getParameter("cost"));
                String v_venue = request.getParameter("venue");
                int v_class_limit = Integer.parseInt(request.getParameter("class_limit"));
                
                B.program_name = v_programName;
                java.sql.Date va_startDate = java.sql.Date.valueOf(v_startDate);
                java.sql.Date va_endDate = java.sql.Date.valueOf(v_endDate);        
                B.start_date = va_startDate;
                B.end_date = va_endDate;
                B.cost = v_cost;
                B.venue = v_venue;
                B.class_limit = v_class_limit;

                
                int status = B.modifyTrainingProgram(B.program_name, B.start_date, B.end_date, B.cost, B.venue, B.class_limit);

                if (status == 1)
                {
            %>

            <h1>Successfully Updated Program Information!</h1>
            <% } else {%>
            <h1>Failed to Update Program Information.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>

