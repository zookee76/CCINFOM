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
        <form action="searchbyID_trainee.jsp" method="post">
            Search by Trainee ID: <input type="text" id="trainee_id" name="trainee_id" required>
            <input type="submit" value="Search">
        </form>

        <form action="searchbyprogram_trainee.jsp" method="post">
            Training Program:
               <select id="training_program_name" name="training_program_name">
                    <option value="japanese_cuisine">Japanese Cuisine</option>
                    <option value="greek_cuisine">Greek Cuisine</option>
                    <option value="italian_cuisine">Italian Cuisine</option>
                    <option value="filipino_cuisine">Filipino Cuisine</option>
                    <option value="french_cuisine">French Cuisine</option>
               </select><br>
            <input type="submit" value="Search">
        </form>

        <form action="searchbySection_trainee.jsp" method="post">
            Section ID:
            <select id="section_id" name="section_id">
                <option value="0">Section 0</option>
                <option value="10">Section 10</option>
                <option value="20">Section 20</option>
                <option value="30">Section 30</option>
                <option value="40">Section 40</option>
            </select>
            <input type="submit" value="Search">
        </form>
    </body>
</html>
