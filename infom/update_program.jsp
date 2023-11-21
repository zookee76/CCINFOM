<%-- 
    Document   : update_program
    Created on : 11 18, 23, 11:43:40 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Program</title>
    </head>
    <body>
        <form action ="update_program_prcessing.jsp" method="post">
            <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>
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
            Start Date: <input type="date" id="start_date" name="start_date" pattern="\d{4}-\d{2}-\d{2}" title="Enter a date in the format yyyy-mm-dd"><br>
            End Date: <input type="date" id="end_date" name="end_date" pattern="\d{4}-\d{2}-\d{2}" title="Enter a date in the format yyyy-mm-dd"><br>
            Cost: <input type="number" id="cost" name="cost"><br>
            Venue: <input type="text" id="venue" name="venue"><br>
            Class Limit: <input type="number" id="class_limit" name="class_limit"><br>
            Class Schedule:
            <select id="class_schedule" name="class_schedule">
                <option value="monday_thursday">Monday/Thursday</option>
                <option value="tuesday_friday">Tuesday/Friday</option>
                <option value="wednesday_saturday">Wednesday/Saturday</option>
            </select><br>
            <input type="Submit" value="Submit">
               </select><br>
        </form>
    </body>
</html>
