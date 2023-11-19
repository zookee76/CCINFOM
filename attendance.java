/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentmgt;

/**
 *
 * @author ccslearner
 */

import java.util.*;
import java.sql.*;

public class attendance 
{    
    public String URL = "jdbc:mysql://localhost:3306/culinary_db";
    public String USERNAME = "root";
    public String PASS = "12345678";
    public int attendance_report_id;
    public java.sql.Date attendance_date;
    public int trainee_id;
    public int mentor_id;
    public boolean present_mentor;
    public String training_program;
    public int section_id;
    public ArrayList<trainees> traineeList = new ArrayList<>();
    
    public attendance()
    {
        
    }
    
    public int logAttendance(java.sql.Date attendance_date, int mentor_id, boolean present_mentor, String training_program, int[] trainee_id)
    {
        try 
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstst = conn.prepareStatement("SELECT MAX(attendance_report_id) + 1 AS newID FROM attendance;");
            ResultSet rst = pstst.executeQuery();
            
            while (rst.next())
            {
                attendance_report_id = rst.getInt("newID");
            }
            
            pstst = conn.prepareStatement("INSERT INTO attendance VALUE (?, ?, ?, ?, ?, ?)");

            pstst.setInt(1, attendance_report_id);
            pstst.setDate(2, attendance_date);
            pstst.setInt(3, mentor_id);
            pstst.setBoolean(4, present_mentor);
            pstst.setString(5, training_program);
            pstst.setInt(6, section_id);
            
            pstst.executeUpdate();
            pstst.close();
            conn.close();
            System.out.println("success");
            
            return 1;
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int logTrainees(int attendance_report_id, int trainee_id)
    {
        try 
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstst = conn.prepareStatement("");
            ResultSet rst = pstst.executeQuery();
            
           
            
            
            pstst = conn.prepareStatement("INSERT INTO trainees_present VALUE (?, ?)");

            pstst.setInt(1, attendance_report_id);
            pstst.setInt(2, trainee_id);
            
            pstst.executeUpdate();
            pstst.close();
            
            
            conn.close();
            System.out.println("success");
            
            return 1;
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int modifyAttendance(int attendance_report_id)
    {
        try 
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");

            PreparedStatement pstst = conn.prepareStatement("UPDATE attendance SET attendance_date = ?, mentor_id = ?, present_mentor = ?, training_program = ? WHERE attendance_report_id = ?");
            pstst.setDate(1, attendance_date);
            pstst.setInt(2, mentor_id);
            pstst.setBoolean(3, present_mentor);
            pstst.setString(4, training_program);
            pstst.setInt(5, attendance_report_id);
            pstst.executeUpdate();
            
            pstst.close();
            conn.close();

            return 1;
        } 
        
        catch (SQLException e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int deleteAttendance(int attendance_report_id)
    {
        try 
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");

            PreparedStatement pstst = conn.prepareStatement("DELETE FROM attendance WHERE attendance_report_id = ?");
            pstst.setInt(1, attendance_report_id);
            pstst.executeUpdate();
            
            pstst.close();
            conn.close();

            return 1;
        } 
        
        catch (SQLException e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public static void main (String args[])
    {
       attendance R = new attendance();
       
       String startDate = "2023-11-23";
       java.sql.Date newStartDate = java.sql.Date.valueOf(startDate);

       
    }
}
