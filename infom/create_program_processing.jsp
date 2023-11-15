<%-- 
    Document   : create_program_processing
    Created on : 11 15, 23, 12:20:24 AM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.*, java.sql.*, enrollmentmgt.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Program Processing</title>
    </head>
    <body>
        <form action = "index.html">
            <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>

            <%  
                String v_programName = request.getParameter("program_name");
                String v_startDate = request.getParameter("start_date");
                String v_endDate = request.getParameter("end_date");
                int v_cost =Integer.parseInt(request.getParameter("cost"));
                String v_venue = request.getParameter("venue");
                int v_class_limit = Integer.parseInt(request.getParameter("class_limit"));
                
                B.program_name = v_programName;
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date va_startDate = null;
                java.sql.Date va_endDate = null;
                
                try 
                {
                    va_startDate = new Date(dateFormat.parse(v_startDate).getTime());
                    va_endDate = new Date(dateFormat.parse(v_endDate).getTime());
                } 
                
                catch (ParseException e)
                {
                    e.printStackTrace(); 
                }
                
                B.start_date = va_startDate;
                B.end_date = va_endDate;
                B.cost = v_cost;
                B.venue = v_venue;
                B.class_limit = v_class_limit;

                int status = B.addTrainingProgram(B.program_name, B.start_date, B.end_date, B.cost, B.venue, B.class_limit);
                
                if (status == 1)
                {
            %>

            <h1>Program Created Successfully!</h1>
            <% } else {%>
            <h1>Failed to Create Program.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>

