<%-- 
    Document   : update_enrollment_processing
    Created on : 11 13, 23, 2:04:33 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Updating Enrollment Information</title>
    </head>
    <body>
        <form action = "index.html">
            <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session"/>

            <%  
                int v_trainee_id = Integer.parseInt(request.getParameter("trainee_id"));
                String v_last_name = request.getParameter("last_name");
                String v_first_name = request.getParameter("first_name");
                String v_middle_initial_name = request.getParameter("middle_initial_name");
                int v_age = Integer.parseInt(request.getParameter("age"));
                String v_contact = request.getParameter("contact");
                long vv_contact = 0;

                try 
                {
                    vv_contact = Long.parseLong(v_contact);
                } 

                catch (NumberFormatException e) 
                {
                    out.println("Error: Please enter a valid contact number.");
                }
                
                A.trainee_id = v_trainee_id;
                A.last_name = v_last_name;
                A.last_name = v_last_name;
                A.first_name = v_first_name;
                A.middle_initial_name = v_middle_initial_name;
                A.age = v_age;
                A.contact = vv_contact;
                
                int status = A.mod_trainee(A.trainee_id, A.last_name, A.first_name, A.middle_initial_name, A.age, A.contact);

                if (status == 1)
                {
            %>

            <h1>Successfully Updated Trainee Information!</h1>
            <% } else {%>
            <h1>Failed to Update Trainee Information.</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>
