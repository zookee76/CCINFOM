<%-- 
    Document   : delete_program_processing
    Created on : 11 18, 23, 11:56:00 PM
    Author     : ccslearner
--%>

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
                
                B.program_name = v_programName;
                
                int status = B.deleteTrainingProgram(B.program_name);

                if (status == 1)
                {
            %>

            <h1>Successfully Deleted Program Information!</h1>
            <% } else {%>
            <h1>Failed to Delete Program Information.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>


