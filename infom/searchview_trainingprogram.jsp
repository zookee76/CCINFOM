<%-- 
    Document   : searchview_trainingprogram
    Created on : 11 19, 23, 2:25:29 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, enrollmentmgt.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search and View Training Program</title>
    </head>
    <body>
        <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session"/>
        <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>
        <jsp:useBean id="C" class="enrollmentmgt.section" scope="session"/>

        <form action="searchbyprogram_name.jsp" method="post">
            Search by Training Program:
               <select id="program_name" name="program_name">
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
    </body>
</html>

