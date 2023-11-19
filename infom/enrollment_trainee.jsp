<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Enrollment Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <form action ="enrollment_processing.jsp" method="post">
            <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>
            Last Name: <input type="text" id="last_name" name="last_name"><br>
            First Name: <input type="text" id="first_name" name="first_name"><br>
            Middle Initial Name: <input type="text" id="middle_initial_name" name="middle_initial_name"><br>
            Age: <input type="number" id="age" name="age" min="10" max="99"><br>
            Contact: <input type="tel" id="contact" name="contact" pattern="[0-9]{10}" placeholder="1234567890">
            
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
               </select><br>
               
                <input type="Submit" value="Submit">
        </form>
    </body>
</html>
