<%-- 
    Document   : update_enrollment
    Created on : 11 13, 23, 2:01:01 PM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Enrollment Information</title>
    </head>
    <body>
        <form action ="update_enrollment_processing.jsp" method="post">
            <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session"/>
            Trainee ID:<select id="trainee_id" name ="trainee_id">
            <%
                A.listTrainees();
                for (int i = 0; i < A.trainee_idList.size(); i++) {
            %>
            <option value="<%=A.trainee_idList.get(i)%>"><%= A.trainee_idList.get(i) %></option>
            <% } %>
            </select>
            Last Name: <input type="text" id="last_name" name="last_name"><br>
            First Name: <input type="text" id="first_name" name="first_name"><br>
            Middle Initial Name: <input type="text" id="middle_initial_name" name="middle_initial_name"><br>
            Age: <input type="number" id="age" name="age" min="18" max="99"><br>
            Contact: <input type="tel" id="contact" name="contact" pattern="[0-9]{10}" placeholder="1234567890"><br>
            Payment Status:
            <select id="payment_status" name="payment_status" required>
                <option value="paid">Paid</option>
                <option value="unpaid">Unpaid</option>
            </select>
            <input type="Submit" value="Submit">
        </form>
    </body>
</html>
