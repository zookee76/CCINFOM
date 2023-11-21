<%-- 
    Document   : searchbyprogram_name
    Created on : 11 19, 23, 2:41:33 PM
    Author     : ccslearner
--%>

<%@page import="enrollmentmgt.trainingprogram"%>
<%@page import="enrollmentmgt.trainees"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Searched Trainees by Program</title>
    </head>
    <body>
        <form action = "index.html">
            <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>
            <%  
                String v_program_name = request.getParameter("program_name");
                
                B.program_name = v_program_name;
                
                int status = B.listTrainingPrograms(B.program_name); 

                if (status == 1)
                {
            %>

            <h2>Program Details:</h2>
            <%
                    for (int i = 0; i < B.training_programs.size(); i++) 
                    {   
                        trainingprogram training_program = B.training_programs.get(i);
            %>
                        <p>Program Name: <%= training_program.getProgramName() %></p>
                        <p>Start Date: <%= training_program.getStartDate() %></p>
                        <p>End Date: <%= training_program.getEndDate() %></p>
                        <p>Cost: <%= training_program.getCost() %></p>
                        <p>Venue: <%= training_program.getVenue() %></p>
                        <p>Class Limit: <%= training_program.getLimit() %></p>
                        <p>Schedule: <%= training_program.getSchedule() %></p>
            <%
                    }
            %>
            <% } else {%>
            <h1>Failed to Display Program Information.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>
