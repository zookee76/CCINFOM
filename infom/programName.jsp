<%-- 
    Document   : programName
    Created on : 11 16, 23, 4:29:25 PM
    Author     : ccslearner
--%>
<%@ page import="clearancereportpkg.TraineeClearanceStatus" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Program Name Filter </title>
    </head>
    <body>
        <jsp:useBean id="cs" class="clearancereportpkg.TraineeClearanceStatus" scope="session" />
        <h1>Generate Quarterly Report - Program Name</h1>

    <form action="filtered_programName.jsp" method="post">
        <!-- Hidden input to specify the filter type -->
        <input type="hidden" name="filterType" value="programName">

        <label for="filterValue">Program Name:</label>
        <select id="filterValue" name="filterValue" required>
            <% cs.getProgramNames();
               for (String prog_name : cs.programNameList) { %>
               <option value=<%=prog_name%>><%=prog_name%></option>
            <% } %>
        </select><br>

        <br><button type="submit">Generate Report</button>
    </form>
    <br><button onclick="goBack()">Back</button>

    <script>
        function goBack() {
            window.history.back();
        }
    </script>
    </body>
</html>

