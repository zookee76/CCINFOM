<%-- 
    Document   : filtered_paymentStatus
    Created on : 11 16, 23, 2:51:32 AM
    Author     : ccslearner
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="clearancereportpkg.TraineeClearanceStatus" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quarterly Report - Payment Status</title>
</head>
<body>
    <jsp:useBean id="tcs" class="clearancereportpkg.TraineeClearanceStatus" scope="session" />
    <%
        // Get the selected department from the HTML form
        String filterType = request.getParameter("filterType");
        String filterValue = request.getParameter("filterValue");
        // Set the department in the Java Bean
        tcs.filterType = filterType;
        tcs.filterValue = filterValue;     
       // Call the method to fetch the data from the database
        tcs.generateQuarterlyReport();
    %>
    
    <h1>Quarterly Report - Filtered by: Payment Status</h1>

    <!-- Display the results -->
    <% for (String result : tcs.clearanceStatusResults) { %>    
        <div><%= result %></div>
    <% } %>
    
            <br><button onclick="goBack()">Back</button>

    <script>
        function goBack() {
            window.history.back();
        }
    </script>
</body>
</html>

