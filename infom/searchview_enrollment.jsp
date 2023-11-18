<%-- 
    Document   : searchview_enrollment
    Created on : 11 13, 23, 11:03:08 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, enrollmentmgt.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search and View Trainees By ID Number</title>
    </head>
    <body>
        <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session"/>
        <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>
        <jsp:useBean id="C" class="enrollmentmgt.section" scope="session"/>
        <form action="searchbyID_trainee.jsp" method="post">
            Search by Trainee ID: <input type="text" id="trainee_id" name="trainee_id" required>
            <input type="submit" value="Search">
        </form>

        <form action="searchbyprogram_trainee.jsp" method="post">
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
            <input type="submit" value="Search">
        </form>

        <form action="searchbySection_trainee.jsp" method="post">
            Section ID:
           <select id="section_id" name="section_id">
                    <%
                        C.listSections();
                        for (int i = 0; i < C.section_idList.size(); i++)
                        {
                    %>
                        <option value="<%=C.section_idList.get(i)%>"><%= C.section_idList.get(i)%></option>
                    <%
                        }
                    %>
               </select><br>
            <input type="submit" value="Search">
        </form>
    </body>
</html>
