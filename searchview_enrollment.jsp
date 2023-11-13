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
        <title>Search and View Trainees</title>
    </head>
    <body>
        <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session"/>
        <form action="searchbyID_trainee.jsp" method="post">
            Search by Trainee ID: <input type="text" id="trainee_id" name="trainee_id" required>
            <input type="submit" value="Search">
        </form>

        <form action="trainees.jsp?action=searchByProgram" method="post">
            Search by Training Program: <input type="text" id="training_program_name" name="training_program_name" required>
            <input type="submit" value="Search">
        </form>

        <form action="trainees.jsp?action=searchBySection" method="post">
            Search by Section ID: <input type="text" id="section_id" name="section_id" required>
            <input type="submit" value="Search">
        </form>
    </body>
</html>
