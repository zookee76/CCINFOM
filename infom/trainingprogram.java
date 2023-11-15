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

public class trainingprogram 
{
    public String program_name;
    public java.sql.Date start_date;
    public java.sql.Date end_date;
    public float cost;
    public String venue;
    public int class_limit;
    public String URL = "jdbc:mysql://localhost:3306/culinary_db";
    public String USERNAME = "root";
    public String PASS = "12345678";
    section Section = new section();
    
    public trainingprogram()
    {
        
    }
    
    public int addTrainingProgram(String program_name, java.sql.Date start_date, java.sql.Date end_date, float cost, String venue, int class_limit)
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstst = conn.prepareStatement("INSERT INTO training_program (program_name, start_date, end_date, cost, venue, class_limit) VALUE (?, ?, ?, ?, ?, ?)");

            pstst.setString(1, program_name);
            pstst.setDate(2, start_date);
            pstst.setDate(3, end_date);
            pstst.setFloat(4, cost);
            pstst.setString(5, venue);
            pstst.setInt(6, class_limit);

            pstst.executeUpdate();
            pstst.close();
            conn.close();
            
            Section.addSection(program_name);
            
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
    
    public int modifyTrainingProgram(String program_name, java.sql.Date start_date, java.sql.Date end_date, float cost, String venue, int class_limit) 
    {
        try 
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");

            PreparedStatement pstst = conn.prepareStatement("UPDATE enrollment SET finish_date = NULL WHERE training_program = ?");
            pstst.setString(1, program_name);
            pstst.executeUpdate();
            pstst.close();

            pstst = conn.prepareStatement("UPDATE training_program SET start_date = ?, end_date = ?, cost = ?, venue = ?, class_limit = ? WHERE program_name = ?");
            pstst.setDate(1, start_date);
            pstst.setDate(2, end_date);
            pstst.setFloat(3, cost);
            pstst.setString(4, venue);
            pstst.setInt(5, class_limit);
            pstst.setString(6, program_name);
            pstst.executeUpdate();
            pstst.close();

            pstst = conn.prepareStatement("UPDATE enrollment SET finish_date = ? WHERE training_program = ?");
            pstst.setDate(1, end_date);
            pstst.setString(2, program_name);
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
    
    public int deleteTrainingProgram(String program_name) 
    {
        int report_id = -1;
        try 
        {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection Successful!");
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM training_program WHERE program_name = ?");
            pstmt.setString(1, program_name);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) 
            {
                PreparedStatement pstst = conn.prepareStatement("SELECT ts.attendance_report_id AS report_id FROM trainees_present ts LEFT JOIN attendance a ON ts.attendance_report_id = a.attendance_report_id WHERE a.training_program = ?");
                pstst.setString(1, program_name);
                
                ResultSet rstst = pstst.executeQuery();
                
                while (rstst.next())
                {
                    report_id = rstst.getInt("report_id");
                }
                
                PreparedStatement delProg = conn.prepareStatement("DELETE FROM trainees_present WHERE attendance_report_id = ?");
                delProg.setInt(1, report_id);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("UPDATE trainee SET training_program = NULL, section_id = NULL WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("DELETE FROM sections WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("UPDATE mentor_trainingprograms SET program_name = NULL WHERE program_name = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("UPDATE clearance_report SET program_name = NULL WHERE program_name = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("UPDATE enrollment SET training_program = NULL, finish_date = NULL WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("UPDATE trainee_assessment SET training_program = NULL WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("DELETE FROM attendance WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                delProg = conn.prepareStatement("DELETE FROM program_schedule WHERE training_program = ?");
                delProg.setString(1, program_name);
                delProg.executeUpdate();
                
                PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM training_program WHERE program_name = ?");
                deleteStmt.setString(1, program_name);
                deleteStmt.executeUpdate();
                
                pstmt.close();
                delProg.close();
                deleteStmt.close();
                conn.close();
                return 1;  
            } 
            
            else 
            {
                pstmt.close();
                conn.close();
                return 0;  
            }
            
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
        trainingprogram D = new trainingprogram();
        
        String startDate = "2023-11-05";
        String endDate = "2023-12-31";

        java.sql.Date newStartDate = java.sql.Date.valueOf(startDate);
        java.sql.Date newEndDate = java.sql.Date.valueOf(endDate);
        
        D.modifyTrainingProgram("french_cuisine", newStartDate, newEndDate, 69000, "G403B", 69);
    }
}
