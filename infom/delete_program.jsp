<%-- 
    Document   : delete_program
    Created on : 11 18, 23, 11:55:49 PM
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
        <form action ="delete_program_prcessing.jsp" method="post">
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
            <input type="Submit" value="Submit">
               </select><br>
        </form>
    </body>
</html>
