<%-- 
    Document   : delete_enrollment_processing
    Created on : 11 13, 23, 5:39:26 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, enrollmentmgt.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Enrollment Information</title>
    </head>
    <body>
        <form action = "index.html">
            <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session"/>
            <%  
                int v_trainee_id = Integer.parseInt(request.getParameter("trainee_id"));
                
                A.trainee_id = v_trainee_id;
                
                int status = A.deleteTrainee(A.trainee_id);

                if (status == 1)
                {
            %>

            <h1>Successfully Deleted Trainee Information!</h1>
            <% } else {%>
            <h1>Failed to Delete Trainee Information.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>
