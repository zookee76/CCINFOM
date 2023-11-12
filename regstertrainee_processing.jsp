<%-- 
    Document   : registerstudent_processing
    Created on : 11 11, 23, 10:13:04 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, traineemanagement.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enrollment Trainee Processing</title>
    </head>
    <body>
        <form action = "index.html">
            <jsp:useBean id="A" class="traineemanagement.trainees" scope="session"/>

            <%  // Receive values from register_trainee.html
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
                    out.println("Error: Please enter a valid contact number."); // Display an error message
                }

                String v_training_program_name = request.getParameter("training_program_name");

                A.last_name = v_last_name;
                A.last_name = v_last_name;
                A.first_name = v_first_name;
                A.middle_initial_name = v_middle_initial_name;
                A.age = v_age;
                A.contact = vv_contact;
                A.training_program_name = v_training_program_name;

                int section_id = -1;

                if (A.training_program_name.equals("french_cuisine"))
                {
                    section_id = 0;
                }

                else if (A.training_program_name.equals("japanese_cuisine"))
                {
                    section_id = 10;
                }

                else if (A.training_program_name.equals("greek_cuisine"))
                {
                    section_id = 20;
                }

                else if (A.training_program_name.equals("filipino_cuisine"))
                {
                    section_id = 30;
                }

                else
                {
                    section_id = 40;
                }

                int status = A.register_trainee(A.last_name, A.first_name, A.middle_initial_name, A.age, A.contact, A.training_program_name, section_id);

                if (status == 1)
                {
            %>

            <h1>Enrollment of Trainee Successful!</h1>
            <% } else {%>
            <h1>Enrollment of Trainee Failed</h1>
            <% }%>
            
            <input type="Submit" value="Return to Menu">
        </form>
    </body>
</html>
