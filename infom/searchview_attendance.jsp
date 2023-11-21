<%-- 
    Document   : searchview_attendance
    Created on : 11 21, 23, 1:15:39 AM
    Author     : ccslearner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, enrollmentmgt.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search and View Attendance</title>
    </head>
    <body>
        <jsp:useBean id="A" class="enrollmentmgt.trainees" scope="session"/>
        <jsp:useBean id="B" class="enrollmentmgt.trainingprogram" scope="session"/>
        <jsp:useBean id="C" class="enrollmentmgt.section" scope="session"/>
        <jsp:useBean id="E" class="enrollmentmgt.attendance" scope="session" />

        <form action="searchbyprogram_attendance.jsp" method="post">
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
        
        <form action="searchbyreportid_attendance.jsp" method="post">
            Search By Attendance Report ID:<select id="attendance_report_id" name ="attendance_report_id">
                    <%
                        E.listAttendance();
                        for (int i = 0; i < E.attendance_idList.size(); i++) {
                    %>
                    <option value="<%=E.attendance_idList.get(i)%>"><%= E.attendance_idList.get(i) %></option>
                    <% } %>
            </select><br>
            <input type="submit" value="Search">
        </form>

         <form action="searchtraineespresent_attendance.jsp" method="post">
            Search Trainees Present By Attendance Report ID:<select id="attendance_report_id" name ="attendance_report_id">
                    <%
                        E.listAttendance();
                        for (int i = 0; i < E.attendance_idList.size(); i++) {
                    %>
                    <option value="<%=E.attendance_idList.get(i)%>"><%= E.attendance_idList.get(i) %></option>
                    <% } %>
            </select><br>
            <input type="submit" value="Search">
        </form>   
    </body>
</html>
