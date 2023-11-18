package clearancereportpkg;

import java.sql.*;
import java.util.*;

public class TraineeClearanceStatus {

    public String filterType; // Type of filter (paymentStatus, traineeName, programName, quarter)
    public String filterValue; // Value for the selected filter
    public String progName;
    public ArrayList<String> clearanceStatusResults = new ArrayList<>();
    public ArrayList<String> programNameList = new ArrayList<>();

    public void getProgramNames ()
    {
       try{
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/culinary_db?user=root&password=12345678");
        String sqlQuery = "SELECT DISTINCT(program_name) FROM clearance_report";
        PreparedStatement pstmt = conn.prepareStatement(sqlQuery);

        ResultSet rs = pstmt.executeQuery();
        programNameList.clear();

        while (rs.next()) {
 
            programNameList.add(rs.getString("program_name"));
        }
        rs.close();
        pstmt.close();
        conn.close();
        
        
       }
       catch (SQLException e) {
        e.printStackTrace();
       }
       
         
         
    }
    
        public void generateQuarterlyReport() {
            try {
                Connection conn;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/culinary_db?user=root&password=12345678");

                // Prepare SQL query based on selected filter
                String sqlQuery = "SELECT cr.clearance_status, cr.trainee_id, "
                                   + (filterType.equals("quarter") || filterType.equals("programName") ? "COUNT(cr.trainee_id) AS trainee_count, " : "")
                                   + "cr.program_name, cr.clearance_date, "
                                   + "t.last_name, t.first_name\n"
                                   + "FROM clearance_report cr\n"
                                   + "JOIN trainee t ON cr.trainee_id = t.trainee_id\n"
                                   + "WHERE ";

               switch (filterType) {
                   case "paymentStatus":
                       sqlQuery += "cr.clearance_status = ?";
                       break;
                   case "traineeName":
                       // Assume filterValue contains the trainee's name input
                       sqlQuery += "t.last_name LIKE ?";
                       break;
                   case "programName":
                       sqlQuery += "cr.program_name = ?";
                       sqlQuery += "\nGROUP BY cr.clearance_status, cr.trainee_id, cr.program_name, cr.clearance_date, t.last_name, t.first_name";
                       break;
                   case "quarter":
                       sqlQuery += "QUARTER(cr.clearance_date) = ?";
                       sqlQuery += "\nGROUP BY cr.clearance_status, cr.trainee_id, cr.program_name, cr.clearance_date, t.last_name, t.first_name";
                       break;
                   default:
                       // Handle invalid filter type
                       break;
               }

               PreparedStatement pstmt = conn.prepareStatement(sqlQuery);

               if(filterType.equals("traineeName"))
               {
                   pstmt.setString(1, "%" + filterValue + "%");

               }
               else
               {
                   pstmt.setString(1,filterValue);
               }

               ResultSet rs = pstmt.executeQuery();
               clearanceStatusResults.clear();

               boolean recordsFound = false;

               while (rs.next()) {
                   recordsFound = true;
                   String result = "";
                if (filterType.equals("programName") || filterType.equals("quarter")) {
                    result += "<br><b>Number of Trainees Enrolled ";
                    result += (filterType.equals("programName")) ? "in the Program:</b> " : "for the Quarter:</b> ";
                    result += rs.getInt("trainee_count");
                    result += "<br><br>";
                }
                   
                result += "<b>Trainee ID:</b> " + getStringOrNA(rs, "trainee_id") +
                         "<br><b>Trainee Name:</b> " + getStringOrNA(rs, "last_name") + ", " + getStringOrNA(rs, "first_name") +
                         "<br><b>Payment Status:</b> " + getStringOrNA(rs, "clearance_status") +
                         "<br><b>Program Name:</b> " + getStringOrNA(rs, "program_name") +
                         "<br><b>Clearance Date:</b> " + (rs.getDate("clearance_date") != null ? rs.getDate("clearance_date") : "N/A");

                result += "<br><br>";
                clearanceStatusResults.add(result);
            }

            rs.close();
            pstmt.close();
            conn.close();

            // Check if no records found
            if (!recordsFound) {
                clearanceStatusResults.add("No records found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        private String getStringOrNA(ResultSet rs, String columnName) throws SQLException {
            String value = rs.getString(columnName);
            return (value != null) ? value : "N/A";
        }
/*      FOR CHECKING:
        public static void main (String args[])
        {
            TraineeClearanceStatus tcs = new TraineeClearanceStatus();
            
            tcs.filterType = "paymentStatus";
            tcs.filterValue = "Paid";
            tcs.generateQuarterlyReport();
            
            for(String results : tcs.clearanceStatusResults)
            {
                System.out.println(results);
            }
               
            
        }
*/ 
}

