<%-- 
    Document   : searchbyID_trainee
    Created on : 11 13, 23, 11:30:12 PM
    Author     : ccslearner
--%>

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
            <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session"/>
            <%  
                int v_section_id = Integer.parseInt(request.getParameter("section_id"));
                
                A.section_id = v_section_id;
                
                int status = A.searchTraineeSection(A.section_id); 

                if (status == 1)
                {
            %>

            <h2>Trainee Details:</h2>
            <%
                    for (int i = 0; i < A.traineeList.size(); i++) 
                    {   trainees trainee = A.getTraineeList().get(i);
            %>
                        <p>Trainee ID: <%= trainee.getTrainee_id() %></p>
                        <p>Last Name: <%= trainee.getLast_name() %></p>
                        <p>First Name: <%= trainee.getFirst_name() %></p>
                        <p>Middle Initial Name: <%= trainee.getMiddle_initial_name() %></p>
                        <p>Age: <%= trainee.getAge() %></p>
                        <p>Contact: <%= trainee.getContact() %></p>
                        <p>Training Program: <%= trainee.getTraining_program_name() %></p>
                        <p>Section ID: <%= trainee.getSection_id() %></p><br>
            <%
                    }
            %>
            <% } else {%>
            <h1>Failed to Display Trainee Information.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>